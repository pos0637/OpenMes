package ${package};

<#list imports as import, value>
import ${import};
</#list>

@RestController
@RequestMapping("${baseUrlPath}")
public class ${className}Controller {
    private final ${package}.${className} service;

    @Autowired
    public ${className}Controller(${package}.${className} service) {
        this.service = service;
    }

    @GetMapping("/${resourceName}")
    public PageResponse findAll(@RequestParam java.util.Map<String, Object> params) {
        return new PageResponse(HttpStatus.OK, service.findAll(params, ${entityName}.class));
    }

    @GetMapping("/${resourceName}/{id}")
    public RestResponse findById(@PathVariable ${pk} id) {
        return new RestResponse(HttpStatus.OK, null, service.findById(id).get());
    }

    @PostMapping("/${resourceName}")
    public RestResponse save(${entityName} entity) {
        return new RestResponse(HttpStatus.OK, null, service.save(entity));
    }

    @PutMapping("/${resourceName}")
    public RestResponse edit(${entityName} entity) {
        return new RestResponse(HttpStatus.OK, null, service.save(entity));
    }

    @DeleteMapping("/${resourceName}/{id}")
    public RestResponse deleteById(@PathVariable ${pk} id) {
        service.deleteById(id);
        return new RestResponse(HttpStatus.OK);
    }

    @PostMapping("/${resourceName}/batch")
    public RestResponse deleteBatch(@RequestParam String delete) {
        service.deleteInBatch(delete);
        return new RestResponse(HttpStatus.OK);
    }
}
