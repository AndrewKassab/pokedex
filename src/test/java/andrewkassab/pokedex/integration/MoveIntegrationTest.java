package andrewkassab.pokedex.integration;

import andrewkassab.pokedex.controller.MoveRestController;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.repositories.MoveRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MoveIntegrationTest {

    @Autowired
    MoveRestController moveController;

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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

    @Test
    void testGetMoveById() {
        var moveReturned = moveController.getMoveById(1);
        assertNotNull(moveReturned);
    }

    @Test
    void testGetAllMoves() {
        var moveList = moveController.getAllMoves(null, null, null);

        assertEquals(15, moveList.getContent().size());
    }

    @Test
    void testGetMovesByType() throws Exception {
        var typeToFilter = Type.WATER;

        var result = mockMvc.perform(get(MoveRestController.MOVE_PATH)
                        .queryParam("type", typeToFilter.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(5)))
                .andReturn();

        // We remove "content" by using substring because of the returned page.
        var responseList = objectMapper.readValue(result.getResponse().getContentAsString().substring(11), new TypeReference<List<Move>>() {});
        assertEquals(5, responseList.size());
        responseList.forEach(move -> assertEquals(typeToFilter, move.getType()));
    }

}