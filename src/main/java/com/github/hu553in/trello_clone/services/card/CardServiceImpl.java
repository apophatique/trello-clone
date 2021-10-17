package com.github.hu553in.trello_clone.services.card;

import com.github.hu553in.trello_clone.entities.Card;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.card.CreateCardForm;
import com.github.hu553in.trello_clone.forms.card.UpdateCardForm;
import com.github.hu553in.trello_clone.repositories.BoardColumnRepository;
import com.github.hu553in.trello_clone.repositories.CardRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {
    private final BoardColumnRepository boardColumnRepository;
    private final CardRepository cardRepository;

    public CardServiceImpl(final BoardColumnRepository boardColumnRepository, final CardRepository cardRepository) {
        this.boardColumnRepository = boardColumnRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public UUID create(
            final UUID boardColumnId,
            final CreateCardForm createCardForm
    ) throws CustomMethodArgumentNotValidException {
        var boardColumn = boardColumnRepository
                .findById(boardColumnId)
                .orElseThrow(EntityNotFoundException::new);
        var newPosition = createCardForm.getPosition();
        if (cardRepository.existsByBoardColumnIdAndPosition(boardColumnId, newPosition)) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should be unique"
            );
        }
        var maxPosition = cardRepository.findMaxPosition(boardColumnId);
        if (maxPosition.isPresent() && newPosition - maxPosition.get() > 1) {
            throw new CustomMethodArgumentNotValidException(
                    "position",
                    "should not exceed max position by more than 1"
            );
        }
        return cardRepository
                .save(new Card(boardColumn, createCardForm.getText(), newPosition))
                .getId();
    }

    @Override
    public List<Card> readAll(final UUID boardColumnId) {
        return cardRepository.findAllByBoardColumnId(boardColumnId);
    }

    @Override
    public Optional<Card> read(final UUID boardColumnId, final UUID id) {
        return cardRepository.findByIdAndBoardColumnId(boardColumnId, id);
    }

    @Override
    public void update(
            final UUID boardColumnId,
            final UUID id,
            final UpdateCardForm updateCardForm
    ) throws CustomMethodArgumentNotValidException {
        if (!boardColumnRepository.existsById(boardColumnId)) throw new EntityNotFoundException();
        var card = cardRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        var newPosition = updateCardForm.getPosition();
        if (newPosition != null) {
            if (cardRepository.existsWithDifferentIdByBoardColumnIdAndPosition(id, boardColumnId, newPosition)) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should be unique"
                );
            }
            var maxPosition = cardRepository.findMaxPosition(boardColumnId);
            if (maxPosition.isPresent() && newPosition - maxPosition.get() > 1) {
                throw new CustomMethodArgumentNotValidException(
                        "position",
                        "should not exceed max position by more than 1"
                );
            }
            card.setPosition(newPosition);
        }
        var newText = updateCardForm.getText();
        if (newText != null) card.setText(newText);
        cardRepository.save(card);
    }

    @Override
    public void delete(final UUID boardColumnId, final UUID id) {
        cardRepository.deleteByIdAndBoardColumnId(boardColumnId, id);
    }
}
