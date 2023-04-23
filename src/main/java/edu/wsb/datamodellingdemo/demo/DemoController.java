package edu.wsb.datamodellingdemo.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/time")
    public String time() {
        return new Date().toString();
    }

//    akcja, która zmieni wielkość liter argumentu

    @GetMapping("/capitalize")
    public String capitalize(@RequestParam String word) {
        return word.toUpperCase();
    }
}
