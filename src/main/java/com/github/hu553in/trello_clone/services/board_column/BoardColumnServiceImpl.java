package com.github.hu553in.trello_clone.services.board_column;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.board_column.CreateBoardColumnForm;
import com.github.hu553in.trello_clone.forms.board_column.UpdateBoardColumnForm;
import com.github.hu553in.trello_clone.repositories.BoardColumnRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;

    public BoardColumnServiceImpl(final BoardColumnRepository boardColumnRepository) {
        this.boardColumnRepository = boardColumnRepository;
    }

    @Override
    public UUID create(
            final CreateBoardColumnForm createBoardColumnForm
    ) throws CustomMethodArgumentNotValidException {
        var newPosition = createBoardColumnForm.getPosition();
        if (boardColumnRepository.existsByPosition(newPosition)) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should be unique"
            );
        }
        var maxPosition = boardColumnRepository.findMaxPosition();
        if (maxPosition.isPresent()) {
            if (newPosition - maxPosition.get() > 1) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should not exceed max position by more than 1"
                );
            }
        } else if (newPosition > 0) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should be 0 if there are no elements"
            );
        }
        return boardColumnRepository
                .save(new BoardColumn(createBoardColumnForm.getTitle(), newPosition))
                .getId();
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
        var boardColumn = boardColumnRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        var oldPosition = boardColumn.getPosition();
        var newPosition = updateBoardColumnForm.getPosition();
        if (newPosition != null && !newPosition.equals(oldPosition)) {
            if (boardColumnRepository.existsWithDifferentIdByPosition(id, newPosition)) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should be unique"
                );
            }
            var optionalMaxPosition = boardColumnRepository.findMaxPosition();
            if (optionalMaxPosition.isPresent()) {
                var maxPosition = optionalMaxPosition.get();
                if (newPosition - maxPosition > 0 && Objects.equals(oldPosition, maxPosition)) {
                    throw new CustomMethodArgumentNotValidException(
                            "position",
                            "is trying to increase position of element with max position"
                    );
                }
                if (newPosition - maxPosition > 1) {
                    throw new CustomMethodArgumentNotValidException(
                            "position",
                            "should not exceed max position by more than 1"
                    );
                }
            }
            boardColumn.setPosition(newPosition);
        }
        var newTitle = updateBoardColumnForm.getTitle();
        if (newTitle != null && !newTitle.equals(boardColumn.getTitle())) boardColumn.setTitle(newTitle);
        boardColumnRepository.save(boardColumn);
    }

    @Override
    public void delete(final UUID id) {
        boardColumnRepository.deleteById(id);
    }
}
