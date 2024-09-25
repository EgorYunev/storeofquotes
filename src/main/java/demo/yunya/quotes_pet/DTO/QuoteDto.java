package demo.yunya.quotes_pet.DTO;

import demo.yunya.quotes_pet.models.AppUser;
import lombok.Value;

/**
 * DTO for {@link demo.yunya.quotes_pet.models.Quote}
 */
@Value
public class QuoteDto {
    String text;
    AppUser author;
}