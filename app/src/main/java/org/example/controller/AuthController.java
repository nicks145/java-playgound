package org.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
                model.addAttribute("userType", "OAuth2");
                model.addAttribute("attributes", oauth2User.getAttributes());
            } else {
                model.addAttribute("userType", "Form Login");
            }
        }
        return "dashboard";
    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> userInfo = new HashMap<>();
        
        if (principal != null) {
            userInfo.put("name", principal.getName());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            userInfo.put("authorities", auth.getAuthorities());
            
            if (auth.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
                userInfo.put("type", "oauth2");
                userInfo.put("attributes", oauth2User.getAttributes());
            } else {
                userInfo.put("type", "form");
            }
        } else {
            userInfo.put("error", "No authenticated user");
        }
        
        return userInfo;
    }

    @GetMapping("/auth-status")
    @ResponseBody
    public Map<String, Object> authStatus(Principal principal) {
        Map<String, Object> status = new HashMap<>();
        
        if (principal != null) {
            status.put("authenticated", true);
            status.put("username", principal.getName());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            status.put("authorities", auth.getAuthorities());
        } else {
            status.put("authenticated", false);
        }
        
        return status;
    }
}