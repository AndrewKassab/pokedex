package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Move;

import java.util.List;
import java.util.Optional;

public interface MoveService {

    List<Move> getAllMoves();

    Optional<Move> getMoveById(Integer id);

    Optional<Move> getMoveByName(String name);

    Move saveNewMove(Move pokemon);

    Optional<Move> updateMoveById(Integer id, Move pokemon);

    Boolean deleteMoveById(Integer id);

}
