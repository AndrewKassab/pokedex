package andrewkassab.pokedex.entitites;


import andrewkassab.pokedex.models.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Column(length=50)
    private String name;

    @ManyToMany
    private Set<Move> moves;

    @NotNull
    private Type type;

    @NotNull
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
