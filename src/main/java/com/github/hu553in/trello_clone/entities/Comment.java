package com.github.hu553in.trello_clone.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Schema(required = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(required = true)
    private Card card;

    @Column(nullable = false)
    @NotBlank
    private String text;

    @CreationTimestamp
    @Schema(required = true)
    private Instant createdAt;

    @UpdateTimestamp
    @Schema(required = true)
    private Instant updatedAt;

    protected Comment() {
    }

    public Comment(final Card card, final String text) {
        this.card = card;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(final Card card) {
        this.card = card;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
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
        return "Comment{" +
                "id=" + id +
                ", card=" + card +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final Comment otherComment = (Comment) other;
        return Objects.equals(id, otherComment.id) &&
                Objects.equals(card, otherComment.card) &&
                Objects.equals(text, otherComment.text) &&
                Objects.equals(createdAt, otherComment.createdAt) &&
                Objects.equals(updatedAt, otherComment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, card, text, createdAt, updatedAt);
    }
}
