package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.controller.MoveController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.MoveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoveIntegrationTest extends PokedexTest {

    @Autowired
    MoveController moveController;

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        var move = getFiveMoves();
        moveRepository.saveAll(move);
    }

    @Test
    void testDeleteMoveDoesntExist() {
        assertThrows(NotFoundException.class, () -> {
            moveController.deleteMoveById(20);
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteMove() {
        var move = moveRepository.findAll().get(0);

        var response = moveController.deleteMoveById(move.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertTrue(moveRepository.findById(move.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            moveController.updateMoveById(20, Move.builder().build());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateMove() {
        var move = moveRepository.findAll().get(0);
        var moveId = move.getId();
        move.setId(null);
        move.setType(null);

        var newName = "New Move";
        move.setName(newName);

        var response = moveController.updateMoveById(moveId, move);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        var returnedMove = moveRepository.findById(moveId).orElse(null);

        assertEquals(newName, returnedMove.getName());
    }

    @Transactional
    @Rollback
    @Test
    void testCreateMove() {
        Move newMove = Move.builder()
                .id(2)
                .name("Life Drain")
                .type(Type.GRASS)
                .build();

        var response = moveController.createMove(newMove);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        String[] locationId = response.getHeaders().getLocation().getPath().split("/");
        var savedId = Integer.parseInt(locationId[4]);

        Move returnedMove = moveRepository.findById(savedId).orElse(null);

        assertNotNull(returnedMove);
    }


    @Test
    void testGetMoveById() {
        var move = moveRepository.findAll().get(0);
        var moveReturned = moveController.getMoveById(move.getId());

        assertNotNull(moveReturned);
    }

    @Test
    void testGetMoveEmpty() {
        moveRepository.deleteAll();
        var moveList = moveController.getAllMoves();

        assertEquals(moveList.size(), 0);
    }

    @Test
    void testGetAllMove() {
        var moveList = moveController.getAllMoves();

        assertEquals(moveList.size(), 3);
    }


}