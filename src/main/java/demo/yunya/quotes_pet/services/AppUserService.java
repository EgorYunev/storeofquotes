package demo.yunya.quotes_pet.services;

import demo.yunya.quotes_pet.models.AppUser;
import demo.yunya.quotes_pet.models.Quote;
import demo.yunya.quotes_pet.repositories.AppUserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private AppUserRepo repo;

    private PasswordEncoder encoder;

    public void addUser(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }

    @Transactional
    public void addQuoteToUserList(Quote quote, int userid) {
        List<Quote> userList = repo.findAppUserById(userid).getQuotes();
        userList.add(quote);

    }

    public void changeUser(AppUser user) {
        repo.save(user);
    }

    public void deleteUserById(int id) {
        repo.deleteById(id);
    }

    public AppUser getUserByUsername(String username) {
        Optional<AppUser> user = repo.findAppUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public List<AppUser> getAllUsers() {
        return repo.findAll();
    }

    public AppUser getUserById(int id) {
        return repo.findAppUserById(id);
    }
}