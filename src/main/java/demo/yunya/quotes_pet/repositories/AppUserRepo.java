package demo.yunya.quotes_pet.repositories;

import demo.yunya.quotes_pet.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findAppUserByUsername(String username);


    AppUser findAppUserById(int id);
}
