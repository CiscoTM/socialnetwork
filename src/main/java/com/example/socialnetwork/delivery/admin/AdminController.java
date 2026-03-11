package com.example.socialnetwork.delivery.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin/panel")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel() {
        return "Admin panel OK";
    }
}
