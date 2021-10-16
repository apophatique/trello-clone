package com.github.hu553in.trello_clone.services.board_column;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.board_column.CreateBoardColumnForm;
import com.github.hu553in.trello_clone.forms.board_column.UpdateBoardColumnForm;
import com.github.hu553in.trello_clone.repositories.BoardColumnRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;

    public BoardColumnServiceImpl(final BoardColumnRepository boardColumnRepository) {
        this.boardColumnRepository = boardColumnRepository;
    }

    @Override
    public UUID create(final CreateBoardColumnForm createBoardColumnForm) throws CustomMethodArgumentNotValidException {
        var newPosition = createBoardColumnForm.getPosition();
        var foundByPosition = boardColumnRepository.findByPosition(newPosition);
        if (foundByPosition.isPresent()) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should be unique"
            );
        }
        var maxPosition = boardColumnRepository.findMaxPosition();
        if (maxPosition.isPresent() && newPosition - maxPosition.get() > 1) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should not exceed max position by more than 1"
            );
        }
        return boardColumnRepository.save(new BoardColumn(
                createBoardColumnForm.getTitle(),
                createBoardColumnForm.getPosition()
        )).getId();
    }

    @Override
    public List<BoardColumn> readAll() {
        return boardColumnRepository.findAll();
    }

    @Override
    public Optional<BoardColumn> read(final UUID id) {
        return boardColumnRepository.findById(id);
    }

    @Override
    public void update(
            final UUID id,
            final UpdateBoardColumnForm updateBoardColumnForm
    ) throws CustomMethodArgumentNotValidException {
        var optionalBoardColumn = boardColumnRepository.findById(id);
        if (optionalBoardColumn.isEmpty()) throw new EntityNotFoundException();
        var boardColumn = optionalBoardColumn.get();
        var newPosition = updateBoardColumnForm.getPosition();
        if (newPosition != null) {
            var foundByPosition = boardColumnRepository.findByPosition(newPosition);
            if (foundByPosition.isPresent() && foundByPosition.get().getId() != id) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should be unique"
                );
            }
            var maxPosition = boardColumnRepository.findMaxPosition();
            if (maxPosition.isPresent() && newPosition - maxPosition.get() > 1) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should not exceed max position by more than 1"
                );
            }
            boardColumn.setPosition(updateBoardColumnForm.getPosition());
        }
        var newTitle = updateBoardColumnForm.getTitle();
        if (newTitle != null) boardColumn.setTitle(updateBoardColumnForm.getTitle());
        boardColumnRepository.save(boardColumn);
    }

    @Override
    public void delete(final UUID id) {
        boardColumnRepository.deleteById(id);
    }
}
