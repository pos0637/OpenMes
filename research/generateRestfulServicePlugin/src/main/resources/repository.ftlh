package ${package};

<#list imports as import, value>
import ${import};
</#list>

public interface ${className}Repository extends BaseRepository<${className}, Long> {
}
