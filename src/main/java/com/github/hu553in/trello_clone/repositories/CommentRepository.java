package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
