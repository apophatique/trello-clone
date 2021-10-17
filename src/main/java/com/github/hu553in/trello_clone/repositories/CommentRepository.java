package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByCardId(final UUID cardId);

    Optional<Comment> findByIdAndCardId(final UUID id, final UUID cardId);

    void deleteByIdAndCardId(final UUID id, final UUID cardId);
}
