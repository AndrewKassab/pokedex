package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Move;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class MoveServiceImpl implements MoveService {

    @Override
    public List<Move> getAllMove() {
        return null;
    }

    @Override
    public Optional<Move> getMoveById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Move saveNewMove(Move pokemon) {
        return null;
    }

    @Override
    public Optional<Move> updateMoveById(Integer id, Move pokemon) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteMoveById(Integer id) {
        return null;
    }

}
