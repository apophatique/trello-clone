package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, UUID> {
    @Query("SELECT MAX(bc.position) FROM BoardColumn bc")
    Optional<Short> findMaxPosition();

    @Query("SELECT bc FROM BoardColumn bc WHERE bc.position = ?1")
    Optional<BoardColumn> findByPosition(final Short position);
}
