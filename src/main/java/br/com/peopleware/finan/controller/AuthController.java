package br.com.peopleware.finan.controller;

import br.com.peopleware.finan.dto.AuthenticationRequestDTO;
import br.com.peopleware.finan.dto.AuthenticationResponseDTO;
import br.com.peopleware.finan.security.JwtUtil;
import br.com.peopleware.finan.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO request) {
        System.out.println("Tentativa de login: " + request.getUsername() + " / " + request.getPassword());

        if (userDetailsService.authenticate(request.getUsername(), request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new AuthenticationResponseDTO(token));
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}
