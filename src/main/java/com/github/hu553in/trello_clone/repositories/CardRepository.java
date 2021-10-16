package com.github.hu553in.trello_clone.repositories;

import com.github.hu553in.trello_clone.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}
