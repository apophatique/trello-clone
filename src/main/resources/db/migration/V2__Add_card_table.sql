CREATE TABLE card (
    id uuid PRIMARY KEY,
    board_column_id uuid NOT NULL,
    text varchar(255) NOT NULL,
    position smallint NOT NULL UNIQUE CHECK (position >= 0),
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT fk_board_column FOREIGN KEY (board_column_id) REFERENCES board_column (id)
);
