package com.alpha.solutions.calcmaster2000.service;

import com.alpha.solutions.calcmaster2000.model.Admin;
import com.alpha.solutions.calcmaster2000.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

        private final AdminRepository adminRepository;

        @Autowired // Constructor injection
        public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        }

        //validateLogin er forretningslogik, fordi den kombinerer dataadgang (repository-kaldet) med en loginvalidering.
        public Admin validateLogin(String username, String password) {
            Admin admin = adminRepository.getAdminByUsername(username); //kalder repositpry for at hente en admin-instans baseret ud fra username
            if (admin != null && admin.getPassword().equals(password)) { //tjekker, om der findes en bruger, og om den gemte adgangskode matcher den, som der er skrevet
                return admin; // Login succesfuldt
            }
            return null; // Forkert login
        }
    }


