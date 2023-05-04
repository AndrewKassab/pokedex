package andrewkassab.pokedex.entitites;


import andrewkassab.pokedex.models.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @NotNull
    @Column(unique = true)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(max=50)
    @Column(length=50)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type primaryType;

    @Enumerated(EnumType.STRING)
    private Type secondaryType;

    @OneToMany(mappedBy = "pokemon")
    @Builder.Default
    private Set<TypeWeakness> typeWeaknesses = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedDate;

    public boolean isWeakToType(Type type) {
        var weaknesses = typeWeaknesses.stream().map(TypeWeakness::getType)
                .toList();
        if (weaknesses.contains(type)) {
            return true;
        }
        return false;
    }

}
