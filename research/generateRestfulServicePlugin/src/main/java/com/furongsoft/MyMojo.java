package com.furongsoft;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class MyMojo extends AbstractMojo {
    private final String ServiceAnnotation = "RestfulService";

    @Parameter(defaultValue = "${project.build.sourceDirectory}", required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", required = true)
    private File outputDirectory;

    /**
     * 扫描文件夹下所有文件
     *
     * @param path 路径
     * @return 所有文件
     */
    private static List<File> scan(final File path) {
        List<File> list = new LinkedList<>();
        File[] files = path.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.isFile()) {
                list.add(file);
            } else {
                List<File> ret = scan(file);
                if (ret != null) {
                    list.addAll(ret);
                }
            }
        }

        return list;
    }

    public void execute() throws MojoExecutionException {
        getLog().info("MyMojo run here");
        generateRestfulController();

        final File f = outputDirectory;
        if (!f.exists()) {
            f.mkdirs();
        }

        File touch = new File(f, "touch.txt");
        FileWriter w = null;

        try {
            w = new FileWriter(touch);
            w.write("touch.txt");
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file " + touch, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * 生成Restful控制器代码
     */
    private void generateRestfulController() {
        List<File> files = scan(sourceDirectory);
        if (files == null) {
            return;
        }

        files.forEach(file -> {
            try {
                CompilationUnit compilationUnit = JavaParser.parse(file);
                compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream()
                        .filter(c -> c.isAnnotationPresent(ServiceAnnotation))
                        .forEach(c -> {
                            getLog().info(file.getAbsolutePath());
                            generateRestfulControllerCode(file);
                        });
            } catch (FileNotFoundException e) {
                getLog().error(e);
            }
        });
    }

    /**
     * 生成Restful控制器代码
     */
    private void generateRestfulControllerCode(File file) {
        Writer writer = null;

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setDirectoryForTemplateLoading(new File(getClass().getResource("/").toString()));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);

            Template template = cfg.getTemplate("restfulController.ftlh");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("package", "com.furongsoft.xxx");
            params.put("className", "XXXService");

            File newFile = new File(outputDirectory, "genertaed-sources/XXXController.java");
            writer = new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8");
            template.process(params, writer);
        } catch (IOException | TemplateException | NullPointerException e) {
            getLog().error(e);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
