package com.github.hu553in.trello_clone.forms.card;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CreateCardForm {
    @NotBlank(message = "should not be blank")
    private String text;

    @NotNull(message = "is required")
    @Min(value = 0, message = "should be greater than or equal to 0")
    private Short position;

    public CreateCardForm(final String text, final Short position) {
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
        return "CreateCardForm{" +
                "text='" + text + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final CreateCardForm otherCreateCardForm = (CreateCardForm) other;
        return Objects.equals(text, otherCreateCardForm.text) &&
                Objects.equals(position, otherCreateCardForm.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, position);
    }
}
