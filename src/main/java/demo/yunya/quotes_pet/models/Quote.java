package demo.yunya.quotes_pet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue
    private int id;

    private String text;

    private int likes = 0;

    @ManyToOne(targetEntity = AppUser.class)
    private AppUser author;

    @ManyToMany(mappedBy = "likesQuotes", fetch = FetchType.LAZY)
    private List<AppUser> usersLikes = new ArrayList<>();

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", author=" + author +
                ", usersLikes=" + usersLikes +
                '}';
    }
}
