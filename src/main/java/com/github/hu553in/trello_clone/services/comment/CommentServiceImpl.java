package com.github.hu553in.trello_clone.services.comment;

import com.github.hu553in.trello_clone.entities.Comment;
import com.github.hu553in.trello_clone.forms.comment.CreateCommentForm;
import com.github.hu553in.trello_clone.forms.comment.UpdateCommentForm;
import com.github.hu553in.trello_clone.repositories.BoardColumnRepository;
import com.github.hu553in.trello_clone.repositories.CardRepository;
import com.github.hu553in.trello_clone.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final BoardColumnRepository boardColumnRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(
            final BoardColumnRepository boardColumnRepository,
            final CardRepository cardRepository,
            final CommentRepository commentRepository
    ) {
        this.boardColumnRepository = boardColumnRepository;
        this.cardRepository = cardRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public UUID create(
            final UUID boardColumnId,
            final UUID cardId,
            final CreateCommentForm createCommentForm
    ) {
        if (!boardColumnRepository.existsById(boardColumnId)) throw new EntityNotFoundException();
        var card = cardRepository
                .findByIdAndBoardColumnId(cardId, boardColumnId)
                .orElseThrow(EntityNotFoundException::new);
        return commentRepository
                .save(new Comment(card, createCommentForm.getText()))
                .getId();
    }

    @Override
    public List<Comment> readAll(final UUID cardId) {
        return commentRepository.findAllByCardId(cardId);
    }

    @Override
    public Optional<Comment> read(final UUID cardId, final UUID id) {
        return commentRepository.findByIdAndCardId(id, cardId);
    }

    @Override
    public void update(
            final UUID boardColumnId,
            final UUID cardId,
            final UUID id,
            final UpdateCommentForm updateCommentForm
    ) {
        if (!boardColumnRepository.existsById(boardColumnId) || !cardRepository.existsById(cardId))
            throw new EntityNotFoundException();
        var comment = commentRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        var newText = updateCommentForm.getText();
        if (newText != null && !newText.equals(comment.getText())) comment.setText(newText);
        commentRepository.save(comment);
    }

    @Override
    public void delete(final UUID cardId, final UUID id) {
        commentRepository.deleteByIdAndCardId(id, cardId);
    }
}
