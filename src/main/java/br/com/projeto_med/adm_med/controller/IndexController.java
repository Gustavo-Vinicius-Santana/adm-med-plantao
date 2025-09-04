package br.com.projeto_med.adm_med.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> index() {
        Map<String, String> info = new HashMap<>();
        info.put("criadoPor", "gustavo vinicius");
        info.put("sobreAPI", "Sistema de gerenciamento de plantões para instituições educacionais. Permite o cadastro de usuários, locais e controle de plantões com horas trabalhadas.");
        info.put("linkedin", "https://www.linkedin.com/in/gustavo-vinicius-596005276/");
        info.put("github", "https://github.com/Gustavo-Vinicius-Santana");

        return ResponseEntity.ok(info);
    }
}