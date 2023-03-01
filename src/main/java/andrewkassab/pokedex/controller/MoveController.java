package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.services.MoveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MoveController {

    public static final String MOVE_PATH = "/api/move";
    public static final String MOVE_PATH_ID = MOVE_PATH + "/{moveId}";

    private final MoveService moveService;

    @PutMapping(MOVE_PATH_ID)
    public ResponseEntity updateMoveById(@PathVariable("moveId") Integer moveId, @RequestBody Move move) {
        return null;
    }

    @DeleteMapping(MOVE_PATH_ID)
    public ResponseEntity deleteMoveById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

    @PostMapping(MOVE_PATH)
    public ResponseEntity createMove(@Validated @RequestBody Move move) {
        return null;
    }

    @GetMapping(MOVE_PATH)
    public ResponseEntity getAllMoves() {
        return null;
    }

    @GetMapping(MOVE_PATH_ID)
    public ResponseEntity getMoveById(@PathVariable("moveId") Integer moveId) {
        return null;
    }

}
