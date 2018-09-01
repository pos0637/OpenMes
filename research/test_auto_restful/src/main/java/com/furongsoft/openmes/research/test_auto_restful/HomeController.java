package com.furongsoft.openmes.research.test_auto_restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
public class HomeController {
    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "Hello, world!";
    }
}
