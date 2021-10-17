package com.github.hu553in.trello_clone.services.card;

import com.github.hu553in.trello_clone.entities.Card;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.card.CreateCardForm;
import com.github.hu553in.trello_clone.forms.card.UpdateCardForm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {
    UUID create(
            final UUID boardColumnId,
            final CreateCardForm createCardForm
    ) throws CustomMethodArgumentNotValidException;

    List<Card> readAll(final UUID boardColumnId);

    Optional<Card> read(final UUID boardColumnId, final UUID id);

    void update(
            final UUID boardColumnId,
            final UUID id,
            final UpdateCardForm updateCardForm
    ) throws CustomMethodArgumentNotValidException;

    void delete(final UUID boardColumnId, final UUID id);
}
