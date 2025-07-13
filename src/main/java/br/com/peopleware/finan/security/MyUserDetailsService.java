package br.com.peopleware.finan.security;

import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService {


    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }
}
