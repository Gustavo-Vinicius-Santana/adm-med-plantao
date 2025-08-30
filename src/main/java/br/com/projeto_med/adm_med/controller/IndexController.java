package br.com.projeto_med.adm_med.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "Página Index Default do Spring Boot";
    }

    @GetMapping("/teste")
    public String teste() {
        return "Teste OK";
    }

    @GetMapping("/objeto")
    public Map<String, String> objeto() {
        Map<String, String> resp = new HashMap<>();
        resp.put("nome", "Gustavo");
        return resp;
    }

}
