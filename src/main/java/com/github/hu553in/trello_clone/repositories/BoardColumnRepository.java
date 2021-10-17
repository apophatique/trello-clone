package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, UUID> {
    @Query("SELECT MAX(bc.position) " +
            "FROM BoardColumn bc")
    Optional<Short> findMaxPosition();

    boolean existsByPosition(final Short position);

    @Query("SELECT CASE WHEN COUNT(bc) > 0 THEN TRUE ELSE FALSE END " +
            "FROM BoardColumn bc " +
            "WHERE bc.id <> ?1 AND bc.position = ?2")
    boolean existsWithDifferentIdByPosition(final UUID id, final Short position);
}
