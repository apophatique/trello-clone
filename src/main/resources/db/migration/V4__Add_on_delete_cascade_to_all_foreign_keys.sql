ALTER TABLE card
    DROP CONSTRAINT fk_board_column;
ALTER TABLE card
    ADD CONSTRAINT fk_board_column FOREIGN KEY (board_column_id) REFERENCES board_column (id) ON DELETE CASCADE;
ALTER TABLE comment
    DROP CONSTRAINT fk_card;
ALTER TABLE comment
    ADD CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES card (id) ON DELETE CASCADE;
