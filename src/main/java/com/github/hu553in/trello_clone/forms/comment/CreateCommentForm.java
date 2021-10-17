package com.github.hu553in.trello_clone.forms.comment;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CreateCommentForm {
    @NotBlank(message = "should not be blank")
    private String text;

    @JsonCreator
    public CreateCommentForm(final String text) {
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
        return "CreateCommentForm{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final CreateCommentForm otherCreateCommentForm = (CreateCommentForm) other;
        return Objects.equals(text, otherCreateCommentForm.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
