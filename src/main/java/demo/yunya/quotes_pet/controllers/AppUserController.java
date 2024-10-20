package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.DTO.AppUserDto;
import demo.yunya.quotes_pet.exceptions.*;
import demo.yunya.quotes_pet.log.Log;
import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Log
public class AppUserController {

    private AppUserService service;

    @PostMapping("/save")
    public String addUser(@ModelAttribute AppUserDto dto) {
        if (service.getUserByUsername(dto.getUsername()) == null) {
            AppUser user = AppUser.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .roles("USER")
                    .build();
            service.addUser(user);
            return "success";
        } else {
            throw new UsernameIsBusyException();
        }
    }

    //TODO доделать изменение пользователя

    @PostMapping("/change")
    public String changeUser(@ModelAttribute AppUserDto dto, Principal principal) {
        AppUser user = service.getUserByUsername(principal.getName());
        if (dto.getUsername() != null || !dto.getUsername().isEmpty()) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null || !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }
        service.changeUser(user);
        return "success";
    }

    @GetMapping("/get-user-by-username")
    public AppUser getUserByUsername(@RequestBody String username) {
        AppUser user = service.getUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserCantBeFindException();
        }
    }

    @DeleteMapping("/delete")
    public void deleteUserByUsername(@RequestBody String username) {
        AppUser user = service.getUserByUsername(username);
        if (user != null) {
            service.deleteUserById(user.getId());
        } else {
            throw new UserCantBeFindException();
        }
    }

    @GetMapping("/account")
    public String getUserById(Model model, Principal principal) {
        AppUser user = service.getUserByUsername(principal.getName());
        if (user != null) {
            model.addAttribute("user", user);
            return "account";
        } else {
            throw new UserCantBeFindException();
        }
    }
}