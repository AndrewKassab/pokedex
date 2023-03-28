package andrewkassab.pokedex.entitites;


import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.models.TypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    @NotNull
    private Integer id;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Column(length=50)
    private String name;

    @Size(max=4)
    @ManyToMany
    @Builder.Default
    private Set<Move> moves = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = TypeDeserializer.class)
    @NotNull
    private Type type;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
