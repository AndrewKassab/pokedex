package andrewkassab.pokedex.services;

import andrewkassab.pokedex.entitites.Move;
import andrewkassab.pokedex.repositories.MoveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Primary
@Service
@RequiredArgsConstructor
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;

    @Override
    public List<Move> getAllMoves() {
        return moveRepository.findAll();
    }

    @Override
    public Optional<Move> getMoveById(Integer id) {
        return moveRepository.findById(id);
    }

    @Override
    public Optional<Move> getMoveByName(String name) {
        return moveRepository.findByName(name);
    }

    @Override
    public Move saveNewMove(Move move) {
        move.setCreatedDate(LocalDateTime.now());
        move.setUpdatedDate(LocalDateTime.now());
        return moveRepository.save(move);
    }

    @Override
    public Optional<Move> updateMoveById(Integer id, Move move) {
        AtomicReference<Optional<Move>> atomicReference = new AtomicReference<>();

        moveRepository.findById(id).ifPresentOrElse(foundMove -> {
            foundMove.setType(move.getType());
            foundMove.setName(move.getName());
            foundMove.setUpdatedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(moveRepository.save(foundMove)));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteMoveById(Integer id) {
        if (moveRepository.existsById(id)) {
            moveRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
