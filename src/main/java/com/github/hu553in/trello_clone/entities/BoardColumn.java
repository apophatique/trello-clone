package com.github.hu553in.trello_clone.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BoardColumn {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Short position;

    @OneToMany(mappedBy = "boardColumn")
    @NotNull
    private List<Card> cards;

    @CreationTimestamp
    @NotNull
    private Instant createdAt;

    @UpdateTimestamp
    @NotNull
    private Instant updatedAt;

    protected BoardColumn() {
    }

    public BoardColumn(final String title, final Short position) {
        this.title = title;
        this.position = position;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(final Short position) {
        this.position = position;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(final List<Card> cards) {
        this.cards = cards;
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
        return "BoardColumn{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", position=" + position +
                ", cards=" + cards +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final BoardColumn otherBoardColumn = (BoardColumn) other;
        return Objects.equals(id, otherBoardColumn.id) &&
                Objects.equals(title, otherBoardColumn.title) &&
                Objects.equals(position, otherBoardColumn.position) &&
                Objects.equals(cards, otherBoardColumn.cards) &&
                Objects.equals(createdAt, otherBoardColumn.createdAt) &&
                Objects.equals(updatedAt, otherBoardColumn.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, position, cards, createdAt, updatedAt);
    }
}
