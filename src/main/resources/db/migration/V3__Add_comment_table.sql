CREATE TABLE comment (
    id uuid PRIMARY KEY,
    card_id uuid NOT NULL,
    text varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card (id)
);
