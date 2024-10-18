package demo.yunya.quotes_pet.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * DTO for {@link demo.yunya.quotes_pet.models.AppUser}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserDto {
    String username;
    String password;
}