package andrewkassab.pokedex.entitites;

import andrewkassab.pokedex.models.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    @NotBlank
    @NotNull
    @Column(length = 50, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

}
