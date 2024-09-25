package demo.yunya.quotes_pet.services;

import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.repositories.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {

    private AppUserRepo repo;

    public void addUser(AppUser user) {
        repo.save(user);
    }

    public void changeUser(AppUser user) {
        repo.save(user);
    }

    public void deleteUserByUsername(String username) {
        repo.deleteByUsername(username);
    }

    public AppUser getUserByUsername(String username) {
        return repo.findAppUserByUsername(username);
    }

    public List<AppUser> getAllUsers() {
        return repo.findAll();
    }

    public AppUser getUserById(int id) {
        return repo.findAppUserById(id);
    }

}
