package com.github.hu553in.trello_clone.forms.board_column;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class CreateBoardColumnForm {
    @NotBlank(message = "should not be blank")
    private String title;

    @NotNull(message = "is required")
    @Min(value = 0, message = "should be greater than or equal to 0")
    @Max(value = 32767, message = "should be less than or equal to 32767")
    private Short position;

    @JsonCreator
    public CreateBoardColumnForm(final String title, final Short position) {
        this.title = title;
        this.position = position;
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

    @Override
    public String toString() {
        return "CreateBoardColumnForm{" +
                "title='" + title + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final CreateBoardColumnForm otherCreateBoardColumnForm = (CreateBoardColumnForm) other;
        return Objects.equals(title, otherCreateBoardColumnForm.title) &&
                Objects.equals(position, otherCreateBoardColumnForm.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, position);
    }
}
