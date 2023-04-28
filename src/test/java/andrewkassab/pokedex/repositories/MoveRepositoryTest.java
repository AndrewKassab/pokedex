package andrewkassab.pokedex.repositories;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class MoveRepositoryTest {

    @Autowired
    MoveRepository moveRepository;

    @Test
    void testSaveMove() {
        Move savedMove = moveRepository.save(Move.builder()
                .name("Lifedrain")
                .type(Type.GRASS)
                .build());

        moveRepository.flush();
        assertThat(savedMove).isNotNull();
    }

    @Test
    void testSaveMoveBlankName() {
        assertThrows(ConstraintViolationException.class, () -> {
            Move savedMove = moveRepository.save(Move.builder()
                    .id(1)
                    .name("")
                    .type(Type.GRASS)
                    .build());
            moveRepository.flush();
        });
    }

    @Test
    void testSaveMoveMissingType() {
        assertThrows(ConstraintViolationException.class, () -> {
            Move savedMove = moveRepository.save(Move.builder()
                    .name("Razor Leaf")
                    .build());
            moveRepository.flush();
        });
    }

    @Test
    void testSaveMoveDuplicateName() {
        Move newMoveOne = Move.builder()
                .name("New Move")
                .type(Type.GRASS)
                .build();
        Move newMoveTwo = Move.builder()
                .name("New Move")
                .type(Type.FIRE)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            moveRepository.save(newMoveOne);
            moveRepository.save(newMoveTwo);
            moveRepository.flush();
            var moves = moveRepository.findAll();
        });
    }
}