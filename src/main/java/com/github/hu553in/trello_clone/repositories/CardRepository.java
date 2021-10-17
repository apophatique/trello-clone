package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findAllByBoardColumnId(final UUID boardColumnId);

    Optional<Card> findByIdAndBoardColumnId(final UUID id, final UUID boardColumnId);

    void deleteByIdAndBoardColumnId(final UUID id, final UUID boardColumnId);

    @Query("SELECT MAX(c.position) " +
            "FROM Card c, BoardColumn bc " +
            "WHERE bc.id = ?1")
    Optional<Short> findMaxPositionByBoardColumnId(final UUID boardColumnId);

    boolean existsByBoardColumnIdAndPosition(final UUID boardColumnId, final Short position);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Card c, BoardColumn bc " +
            "WHERE c.id <> ?1 AND bc.id = ?2 AND c.position = ?3")
    boolean existsWithDifferentIdByBoardColumnIdAndPosition(
            final UUID id,
            final UUID boardColumnId,
            final Short position
    );
}
