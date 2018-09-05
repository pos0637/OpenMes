package ${package};

<#list imports as import, value>
import ${import};
</#list>

@Repository("${package}.${className}Repository")
public interface ${className}Repository extends BaseRepository<${className}, Long> {
}
