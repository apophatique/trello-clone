package com.github.hu553in.trello_clone.services.board_column;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.board_column.CreateBoardColumnForm;
import com.github.hu553in.trello_clone.forms.board_column.UpdateBoardColumnForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardColumnService {
    UUID create(final CreateBoardColumnForm createBoardColumnForm) throws CustomMethodArgumentNotValidException;

    List<BoardColumn> readAll();

    Optional<BoardColumn> read(final UUID id);

    void update(
            final UUID id,
            final UpdateBoardColumnForm updateBoardColumnForm
    ) throws CustomMethodArgumentNotValidException;

    void delete(final UUID id);
}
