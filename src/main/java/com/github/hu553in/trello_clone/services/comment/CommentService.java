package com.github.hu553in.trello_clone.services.comment;

import com.github.hu553in.trello_clone.entities.Comment;
import com.github.hu553in.trello_clone.forms.comment.CreateCommentForm;
import com.github.hu553in.trello_clone.forms.comment.UpdateCommentForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    UUID create(
            final UUID boardColumnId,
            final UUID cardId,
            final CreateCommentForm createCommentForm
    );

    List<Comment> readAll(final UUID cardId);

    Optional<Comment> read(final UUID cardId, final UUID id);

    void update(
            final UUID boardColumnId,
            final UUID cardId,
            final UUID id,
            final UpdateCommentForm updateCommentForm
    );

    void delete(final UUID cardId, final UUID id);
}
