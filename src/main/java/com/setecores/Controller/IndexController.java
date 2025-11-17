package com.setecores.Controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // Retorna "index" como o nome de uma view (eliminando o 'redirect')
        return "index"; 
    }
}