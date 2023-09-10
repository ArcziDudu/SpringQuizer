CREATE TABLE quizer_role
(
    role_id SERIAL      NOT NULL,
    role    VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE quizer_user
(
    user_id         SERIAL       NOT NULL,
    user_name           VARCHAR(32)  NOT NULL,
   email           VARCHAR(52)  NOT NULL,
    password        VARCHAR(132) NOT NULL,
    active          BOOLEAN      NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE quizer_user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT fk_quizer_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES quizer_user (user_id),
    CONSTRAINT fk_quizer_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES quizer_role (role_id)
);

insert into quizer_role (role_id, role) values (1, 'PLAYER');


insert into quizer_user_role (user_id, role_id) values (1, 1);