package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.models.Type;

import java.util.List;
import java.util.Optional;

public interface MoveService {

    List<Move> getAllMoves(Type type, Integer pageNumber, Integer pageSize);

    Optional<Move> getMoveById(Integer id);

    Optional<Move> getMoveByName(String name);

    Move saveNewMove(Move pokemon);

    Optional<Move> updateMoveById(Integer id, Move pokemon);

    Boolean deleteMoveById(Integer id);

}
