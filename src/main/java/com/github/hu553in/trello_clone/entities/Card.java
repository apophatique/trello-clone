package com.github.hu553in.trello_clone.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Card {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "board_column_id")
    private BoardColumn boardColumn;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    @Min(0)
    private Short position;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    protected Card() {
    }

    public Card(final String text, final Short position) {
        this.text = text;
        this.position = position;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public BoardColumn getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(final BoardColumn boardColumn) {
        this.boardColumn = boardColumn;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(final Short position) {
        this.position = position;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", boardColumn=" + boardColumn +
                ", text='" + text + '\'' +
                ", position=" + position +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final Card otherCard = (Card) other;
        return position == otherCard.position &&
                Objects.equals(id, otherCard.id) &&
                Objects.equals(boardColumn, otherCard.boardColumn) &&
                Objects.equals(text, otherCard.text) &&
                Objects.equals(createdAt, otherCard.createdAt) &&
                Objects.equals(updatedAt, otherCard.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boardColumn, text, position, createdAt, updatedAt);
    }
}
