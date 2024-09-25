package demo.yunya.quotes_pet.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Quote {

    @Id
    @GeneratedValue
    private int id;

    private String text;

    @ManyToOne
    private AppUser author;

}
