package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.controller.MoveController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.MoveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
class MoveIntegrationTest {

    @Autowired
    MoveController moveController;

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
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
        var response = moveController.deleteMoveById(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertTrue(moveRepository.findById(1).isEmpty());
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
        var move = moveRepository.getReferenceById(1);

        var newName = "New Move";
        move.setName(newName);

        var response = moveController.updateMoveById(1, move);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        var returnedMove = moveRepository.findById(1).orElse(null);

        assertEquals(newName, returnedMove.getName());
    }

    @Transactional
    @Rollback
    @Test
    void testCreateMove() {
        Move newMove = Move.builder().name("Move 16").type(Type.GRASS).build();
        var response = moveController.createMove(newMove);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        String[] locationId = response.getHeaders().getLocation().getPath().split("/");
        var savedId = Integer.parseInt(locationId[3]);

        Move returnedMove = moveRepository.findById(savedId).orElse(null);

        assertNotNull(returnedMove);
    }

    @Transactional
    @Rollback
    @Test
    void testGetMoveById() {
        var moveReturned = moveController.getMoveById(1);
        assertNotNull(moveReturned);
    }

    @Transactional
    @Rollback
    @Test
    void testGetAllMove() {
        var moveList = moveController.getAllMoves();

        assertEquals(15, moveList.size());
    }

}