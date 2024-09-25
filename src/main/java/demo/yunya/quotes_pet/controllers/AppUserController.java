package demo.yunya.quotes_pet.controllers;

import demo.yunya.quotes_pet.DTO.AppUserDto;
import demo.yunya.quotes_pet.exceptions.UserCantBeFind;
import demo.yunya.quotes_pet.exceptions.UsernameIsBusy;
import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.services.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private AppUserService service;

    @PostMapping("/save")
    public String addUser(@RequestBody AppUserDto dto) {
        if (service.getUserByUsername(dto.getUsername()) == null) {
            AppUser user = AppUser.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .build();
            service.addUser(user);
            return "Пользователь успешно создан!";
        } else {
            throw new UsernameIsBusy();
        }
    }

    @PutMapping("/{id}/change-user")
    public String changeUser(@PathVariable int id, @RequestBody AppUserDto dto) {
        if (service.getUserById(id) != null) {
            AppUser user = AppUser.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .build();
            service.changeUser(user);
            return "Пользовательские данные успешно изменены!";
        } else {
            throw new UserCantBeFind();
        }
    }

    @GetMapping("/get-user-by-username")
    public AppUser getUserByUsername(@RequestBody String username) {
        AppUser user = service.getUserByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UserCantBeFind();
        }
    }

    @GetMapping("/get-all/users")
    public List<AppUser> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/delete-user-by-username")
    public void deleteUserByUsername(@RequestBody String username) {
        AppUser user = service.getUserByUsername(username);
        if (user != null) {
            service.deleteUserByUsername(username);
        } else {
            throw new UserCantBeFind();
        }
    }

    @GetMapping("/get-user-by-id")
    public AppUser getUserById(@RequestBody int id) {
        AppUser user = service.getUserById(id);
        if (user != null) {
            return user;
        } else {
            throw new UserCantBeFind();
        }
    }

}
