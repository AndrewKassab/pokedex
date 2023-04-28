package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.controller.exceptions.IdProvidedException;
import andrewkassab.pokedex.controller.exceptions.NotFoundException;
import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;
import andrewkassab.pokedex.services.MoveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MoveController {

    public static final String MOVE_PATH = "/api/move";
    public static final String MOVE_PATH_ID = MOVE_PATH + "/{moveId}";

    private final MoveService moveService;

    @PutMapping(MOVE_PATH_ID)
    public ResponseEntity updateMoveById(@PathVariable("moveId") Integer moveId, @RequestBody Move move) {

        if (moveService.updateMoveById(moveId, move).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(MOVE_PATH_ID)
    public ResponseEntity deleteMoveById(@PathVariable("moveId") Integer moveId) {

        if (!moveService.deleteMoveById(moveId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(MOVE_PATH)
    public ResponseEntity createMove(@Validated @RequestBody Move move) {

        if (move.getId() != null) {
            throw new IdProvidedException("id should not be provided");
        }
        if (moveService.getMoveByName(move.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Move name already exists");
        }

        var savedMove = moveService.saveNewMove(move);

        var headers = new HttpHeaders();
        headers.add("Location", MOVE_PATH + "/" + savedMove.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(MOVE_PATH)
    public Page<Move> getAllMoves(@RequestParam(required = false) Type type,
                                  @RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize) {
        return moveService.getAllMoves(type, pageNumber, pageSize);
    }

    @GetMapping(MOVE_PATH_ID)
    public Move getMoveById(@PathVariable("moveId") Integer moveId) {
        var move = moveService.getMoveById(moveId);
        return moveService.getMoveById(moveId).orElseThrow(NotFoundException::new);
    }

}
