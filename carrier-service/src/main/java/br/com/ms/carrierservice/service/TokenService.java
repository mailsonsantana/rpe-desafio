package br.com.ms.carrierservice.service;

import br.com.ms.carrierservice.security.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;


    public TokenService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String getToken(String cpf) {
        return gerarToken(cpf);
    }

    private String gerarToken(String cpf) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("cpf", cpf);
        return jwtTokenUtil.generateToken(claims);
    }
}
