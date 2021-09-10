CREATE TABLE public.player
(
    id bigint NOT NULL,
    opponent_id bigint,
    name character varying(256)
);

alter table public.player
    add primary key (id);
alter table public.player
    ADD FOREIGN KEY (opponent_id)
        REFERENCES player(id) ON DELETE CASCADE;

CREATE TABLE public.stone
(
    id bigint NOT NULL,
    player_id bigint NOT NULL,
    row_position int,
    column_position  int,
    is_white boolean NOT NULL,
    is_in_game boolean NOT NULL
);

alter table public.stone
    add primary key (id);
alter table public.stone
    ADD FOREIGN KEY (player_id)
        REFERENCES player(id) ON DELETE CASCADE;




