package com.setecores.Controller; // Use o nome do seu pacote correto

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // Redireciona diretamente para o recurso estático "index.html"
        return "redirect:/index.html"; 
    }
}