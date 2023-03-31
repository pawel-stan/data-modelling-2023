package edu.wsb.datamodellingdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
