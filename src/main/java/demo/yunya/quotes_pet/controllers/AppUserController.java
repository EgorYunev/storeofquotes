package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.DTO.AppUserDto;
import demo.yunya.quotes_pet.exceptions.*;
import demo.yunya.quotes_pet.log.Log;
import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Log
public class AppUserController {

    private AppUserService service;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public String addUser(@ModelAttribute AppUserDto dto, Model model) {
        if (service.getUserByUsername(dto.getUsername()) == null) {
            AppUser user = AppUser.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .roles("USER")
                    .build();
            service.addUser(user);
            model.addAttribute("message", "Регистрация успешна!");
            return "success";
        } else {
            model.addAttribute("message", "Такое имя пользователя уже занято");
            return "error";
        }
    }

    @PostMapping("/change")
    public String changeUser(@ModelAttribute AppUserDto dto, Principal principal, Model model) {
        AppUser user = service.getUserByUsername(principal.getName());
        if (!dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        if (!dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        service.changeUser(user);
        model.addAttribute("message", "Пользовательские данные изменены");
        return "success";
    }

    @PostMapping("/delete")
    public String deleteUserByUsername(Model model, Principal principal) {
        AppUser user = service.getUserByUsername(principal.getName());
        if (user != null) {
            service.deleteUserById(user.getId());
            model.addAttribute("message", "Пользователь удален");
            return "success";
        } else {
            model.addAttribute("message", "Пользователь не найден");
            return "error";
        }
    }

    @GetMapping("/account")
    public String getUserById(Model model, Principal principal) {
        AppUser user = service.getUserByUsername(principal.getName());
        if (user != null) {
            model.addAttribute("user", user);
            return "account";
        } else {
            model.addAttribute("message", "Пользователь не найден");
            return "error";
        }
    }
}