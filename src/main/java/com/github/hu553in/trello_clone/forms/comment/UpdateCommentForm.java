package com.github.hu553in.trello_clone.forms.comment;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class UpdateCommentForm {
    private String text;

    @JsonCreator
    public UpdateCommentForm(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "UpdateCommentForm{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final UpdateCommentForm otherUpdateCommentForm = (UpdateCommentForm) other;
        return Objects.equals(text, otherUpdateCommentForm.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
