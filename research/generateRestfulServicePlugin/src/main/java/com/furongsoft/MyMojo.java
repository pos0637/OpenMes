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
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
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
import java.util.*;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class MyMojo extends AbstractMojo {
    private final String ServiceAnnotation = "RestfulService";
    private final String BaseService = "BaseService";
    private final String EntityAnnotation = "RestfulEntity";

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
            if (file.isFile() && file.getName().endsWith(".java")) {
                list.add(file);
            } else if (file.isDirectory()) {
                List<File> ret = scan(file);
                if (ret != null) {
                    list.addAll(ret);
                }
            }
        }

        return list;
    }

    /**
     * 获取注解参数值
     *
     * @param cd             类与接口定义
     * @param annotationName 注解名称
     * @param parameterName  参数名称
     * @return 参数值
     */
    private static String getAnnotationParameter(ClassOrInterfaceDeclaration cd, String annotationName, String parameterName) {
        Optional<AnnotationExpr> expr = cd.getAnnotationByName(annotationName);
        if (!expr.isPresent()) {
            return "";
        }

        List<MemberValuePair> children = expr.get().findAll(MemberValuePair.class);
        for (MemberValuePair memberValuePair : children) {
            if (parameterName.equals(memberValuePair.getNameAsString())) {
                return memberValuePair.getValue().toString();
            }
        }

        return "";
    }

    /**
     * 获取父类泛型
     *
     * @param cd        类与接口定义
     * @param className 类名
     * @return 泛型
     */
    private static String[] getGenericTypesOfExtendedClass(final ClassOrInterfaceDeclaration cd, final String className) {
        Optional<ClassOrInterfaceType> type = cd.getExtendedTypes().stream()
                .filter(classOrInterfaceType -> classOrInterfaceType.asString().startsWith(className))
                .findFirst();
        if (type.isPresent()) {
            return getGenericTypes(cd, type.get().asString());
        } else {
            return null;
        }
    }

    /**
     * 获取泛型
     *
     * @param cd        类与接口定义
     * @param className 类名
     * @return 泛型
     */
    private static String[] getGenericTypes(final ClassOrInterfaceDeclaration cd, final String className) {
        if (!className.endsWith(">")) {
            return null;
        }

        int pos = className.indexOf('<');
        if (pos < 0) {
            return null;
        }

        return className.substring(pos + 1, className.length() - 1).split(",");
    }

    public void execute() throws MojoExecutionException {
        getLog().info("generate restful services");
        generateRepository();
        generateRestfulController();
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
                            getLog().debug("generate restful controller: " + file.getAbsolutePath());
                            generateRestfulControllerCode(compilationUnit, c);
                        });
            } catch (FileNotFoundException e) {
                getLog().error(e);
            }
        });
    }

    /**
     * 生成Restful控制器代码
     *
     * @param cu 编译单元
     * @param cd 类与接口定义
     */
    private void generateRestfulControllerCode(CompilationUnit cu, ClassOrInterfaceDeclaration cd) {
        Writer writer = null;

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassForTemplateLoading(getClass(), "/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);

            String packageName = cu.getPackageDeclaration().isPresent() ? cu.getPackageDeclaration().get().getName().asString() : "";
            String packagePath = packageName.replace('.', '/');
            String className = cd.getNameAsString();
            String baseUrlPath = getAnnotationParameter(cd, ServiceAnnotation, "path");
            String[] genericTypes = getGenericTypesOfExtendedClass(cd, BaseService);
            if ((genericTypes == null) || (genericTypes.length < 2)) {
                return;
            }

            HashMap<String, String> imports = new HashMap<>();
            imports.put("com.furongsoft.base.entities.PageRequest", null);
            imports.put("com.furongsoft.base.entities.RestResponse", null);
            imports.put("com.furongsoft.base.entities.PageResponse", null);
            imports.put("org.springframework.http.HttpStatus", null);
            imports.put("org.springframework.web.bind.annotation.*", null);
            imports.put("org.springframework.beans.factory.annotation.Autowired", null);
            cu.getImports().forEach(importDeclaration -> {
                imports.put(importDeclaration.getNameAsString(), importDeclaration.getNameAsString());
            });

            Template template = cfg.getTemplate("restfulController.ftlh");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("package", packageName);
            params.put("className", className);
            params.put("baseUrlPath", baseUrlPath.replace("\"", ""));
            params.put("resourceName", genericTypes[0].toLowerCase() + "s");
            params.put("entityName", genericTypes[0]);
            params.put("pk", genericTypes[1]);
            params.put("imports", imports);

            File newPath = new File(outputDirectory, packagePath);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }

            File newFile = new File(newPath, className + "Controller.java");
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

    /**
     * 生成数据仓库代码
     */
    private void generateRepository() {
        List<File> files = scan(sourceDirectory);
        if (files == null) {
            return;
        }

        files.forEach(file -> {
            try {
                CompilationUnit compilationUnit = JavaParser.parse(file);
                compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream()
                        .filter(c -> c.isAnnotationPresent(EntityAnnotation))
                        .forEach(c -> {
                            getLog().debug("generate repository" + file.getAbsolutePath());
                            generateRepositoryCode(compilationUnit, c);
                        });
            } catch (FileNotFoundException e) {
                getLog().error(e);
            }
        });
    }

    /**
     * 生成数据仓库代码
     *
     * @param cu 编译单元
     * @param cd 类与接口定义
     */
    private void generateRepositoryCode(CompilationUnit cu, ClassOrInterfaceDeclaration cd) {
        Writer writer = null;

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassForTemplateLoading(getClass(), "/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);

            String packageName = cu.getPackageDeclaration().isPresent() ? cu.getPackageDeclaration().get().getName().asString() : "";
            String packagePath = packageName.replace('.', '/');
            String className = cd.getNameAsString();

            HashMap<String, String> imports = new HashMap<>();
            imports.put("com.furongsoft.base.repositories.BaseRepository", null);
            imports.put(packageName + "." + className, null);

            Template template = cfg.getTemplate("repository.ftlh");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("package", packageName);
            params.put("className", className);
            params.put("imports", imports);

            File newPath = new File(outputDirectory, packagePath);
            if (!newPath.exists()) {
                newPath.mkdirs();
            }

            File newFile = new File(newPath, className + "Repository.java");
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
