package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.services.MoveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MoveController.class)
class MoveControllerTest extends PokedexTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MoveService moveService;

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<Move> moveArgumentCaptor;

    ObjectMapper objectMapper;

    List<Move> moveList = getFiveMoves();

    @Test
    void testDeleteMove() throws Exception {
        var testMove = moveList.get(0);
        given(moveService.deleteMoveById(any())).willReturn(true);

        mockMvc.perform(delete(MoveController.MOVE_PATH_ID, testMove.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(moveService).deleteMoveById(idArgumentCaptor.capture());

        assertThat(testMove.getId()).isEqualTo(idArgumentCaptor.getValue());
    }

    @Test
    void testUpdateMove() throws Exception {
        var testMove = moveList.get(0);
        testMove.setName("New Name");
        given(moveService.updateMoveById(any(), any())).willReturn(Optional.of(testMove));

        mockMvc.perform(put(MoveController.MOVE_PATH_ID, testMove.getId())
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMove)))
                .andExpect(status().isNoContent());

        verify(moveService).updateMoveById(idArgumentCaptor.capture(), moveArgumentCaptor.capture());

        assertThat(testMove.getId()).isEqualTo(idArgumentCaptor.getValue());
        assertThat(testMove.getName()).isEqualTo(moveArgumentCaptor.getValue().getName());
    }

    @Test
    void testCreateMove() throws Exception {
        var testMove = moveList.get(0);
        given(moveService.saveNewMove(any(Move.class))).willReturn(moveList.get(0));

        mockMvc.perform(post(MoveController.MOVE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(testMove)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name", is(testMove.getName())));
    }

    @Test
    void testCreateMoveNullName() throws Exception {
        var testMove = moveList.get(0);
        testMove.setName(null);
        testMove.setId(2);
        given(moveService.saveNewMove(any(Move.class))).willReturn(moveList.get(0));

        mockMvc.perform(post(MoveController.MOVE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(testMove)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllMove() throws Exception {
        given(moveService.getAllMove()).willReturn(moveList);

        mockMvc.perform(get(MoveController.MOVE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)));
    }

    @Test
    void testGetMoveById() throws Exception {
        var testMove = moveList.get(0);
        given(moveService.getMoveById(testMove.getId())).willReturn(Optional.of(testMove));

        mockMvc.perform(get(MoveController.MOVE_PATH_ID, testMove.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testMove.getId())))
                .andExpect(jsonPath("$.name", is(testMove.getName())));
    }

    @Test
    void testGetMoveByIdNotFound() throws Exception {
        given(moveService.getMoveById(any(Integer.class))).willReturn(Optional.empty());

        mockMvc.perform(get(MoveController.MOVE_PATH_ID, 1))
                .andExpect(status().isNotFound());
    }


}