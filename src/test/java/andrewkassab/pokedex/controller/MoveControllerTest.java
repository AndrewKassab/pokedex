package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.PokedexTest;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.services.MoveService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
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

    @MockBean
    MoveService moveService;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    @Captor
    ArgumentCaptor<Move> moveArgumentCaptor;

    @Autowired
    ObjectMapper objectMapper;

    List<Move> moveList = getFiveMoves();

    @Test
    void testDeleteMove() throws Exception {
        var testMove = moveList.get(0);
        testMove.setId(1);
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
        testMove.setId(1);
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
        testMove.setId(1);
        given(moveService.saveNewMove(any(Move.class))).willReturn(testMove);
        Map<String, Object> valueAsMap = objectMapper.convertValue(testMove, new TypeReference<Map<String, Object>>() {});
        valueAsMap.put("id", null);

        mockMvc.perform(post(MoveController.MOVE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(valueAsMap)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
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
                        .content(objectMapper.writeValueAsString(testMove)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateMoveInvalidType() throws Exception {
        var testMove = moveList.get(0);
        Map<String, Object> valueAsMap = objectMapper.convertValue(testMove, new TypeReference<Map<String, Object>>() {});
        valueAsMap.put("type", "faketype");

        var result = mockMvc.perform(post(MoveController.MOVE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(valueAsMap)))
                .andExpect(status().isBadRequest())
                .andReturn();

        var content = result.getResponse().getContentAsString();

        assertThat(content).isEqualTo("Invalid type value: faketype");
    }

    @Test
    void testGetAllMove() throws Exception {
        given(moveService.getAllMoves(null, null, null)).willReturn(new PageImpl<>(moveList));

        mockMvc.perform(get(MoveController.MOVE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(5)));
    }

    @Test
    void testGetMoveById() throws Exception {
        var testMove = moveList.get(0);
        testMove.setId(1);
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