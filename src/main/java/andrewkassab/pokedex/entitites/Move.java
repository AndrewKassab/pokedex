package andrewkassab.pokedex.entitites;

import andrewkassab.pokedex.models.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    @Column(length = 50, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedDate;

}
