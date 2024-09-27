package demo.yunya.quotes_pet.DTO;

import lombok.Value;

/**
 * DTO for {@link demo.yunya.quotes_pet.models.AppUser}
 */
@Value
public class AppUserDto {
    String username;
    String password;
    String roles;
}