package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Move;

import java.util.List;
import java.util.Optional;

public interface MoveService {

    List<Move> getAllMove();

    Optional<Move> getMoveById(Integer id);

    Move saveNewMove(Move pokemon);

    Optional<Move> updateMoveById(Integer id, Move pokemon);

    Boolean deleteMoveById(Integer id);

}
