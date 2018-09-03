package ${package};

<#list imports as import, value>
import ${import};
</#list>

@Controller
@RequestMapping("${baseUrlPath}")
public class ${className}Controller {
    private final ${package}.${className} service;

    @Autowired
    public ${className}Controller(${package}.${className} service) {
        this.service = service;
    }

    @GetMapping("/${resourceName}")
    public PageResponse findAll(PageRequest pageRequest) {
        return new PageResponse(HttpStatus.OK, service.findAll(pageRequest));
    }

    @GetMapping("/${resourceName}/{id}")
    public RestResponse findOne(@PathVariable Long id) {
        return new RestResponse(HttpStatus.OK, service.findOne(id));
    }

    @PostMapping("/${resourceName}")
    public RestResponse save(${entityName} entity) {
        return new RestResponse(HttpStatus.OK, service.save(entity));
    }

    @PutMapping("/${resourceName}/{id}")
    public RestResponse edit(@PathVariable Long id, ${entityName} entity) {
        return new RestResponse(HttpStatus.OK);
    }

    @DeleteMapping("/${resourceName}/{id}")
    public RestResponse delete(@PathVariable Long id) {
        return new RestResponse(HttpStatus.OK, service.delete(id));
    }

    @PostMapping("/${resourceName}/batch")
    public RestResponse deleteBatch(@RequestParam String delete) {
        return new RestResponse(HttpStatus.OK, service.deleteBatch(delete));
    }
}
