CREATE TABLE board_column (
    id uuid PRIMARY KEY,
    title varchar(255) NOT NULL,
    position smallint NOT NULL UNIQUE CHECK (position >= 0),
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);
