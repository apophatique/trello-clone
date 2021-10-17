package com.github.hu553in.trello_clone.forms.card;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

public class UpdateCardForm {
    private String text;

    @Min(value = 0, message = "should be greater than or equal to 0")
    @Max(value = 32767, message = "should be less than or equal to 32767")
    private Short position;

    @JsonCreator
    public UpdateCardForm(final String text, final Short position) {
        this.text = text;
        this.position = position;
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

    @Override
    public String toString() {
        return "UpdateCardForm{" +
                "text='" + text + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final UpdateCardForm otherUpdateCardForm = (UpdateCardForm) other;
        return Objects.equals(text, otherUpdateCardForm.text) &&
                Objects.equals(position, otherUpdateCardForm.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, position);
    }
}
