package com.setecores.Controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // Tenta retornar "index" como o nome de uma view/template
        return "index"; 
    }
}