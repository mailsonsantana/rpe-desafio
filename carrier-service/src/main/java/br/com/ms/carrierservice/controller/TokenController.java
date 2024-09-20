package br.com.ms.carrierservice.controller;

import br.com.ms.carrierservice.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/param")
    public ResponseEntity<String> getToken(
            @RequestParam(value = "cpf") String cpf) {
        ResponseEntity<String> response = ResponseEntity.ok(tokenService.getToken(cpf));
        return response;
    }
}
