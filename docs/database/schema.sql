CREATE TABLE posts (
    created_date datetime(6),
    id bigint not null auto_increment,
    modified_date datetime(6),
    title varchar(500) not null,
    author varchar(255),
    content text not null,
    primary key (id)
) engine=InnoDB;

CREATE TABLE users (
    created_date datetime(6),
    id bigint not null auto_increment,
    modified_date datetime(6),
    email varchar(255) not null,
    name varchar(255) not null,
    picture varchar(255),
    role enum ('GUEST','USER') not null,
    primary key (id)
) engine=InnoDB;

CREATE TABLE SPRING_SESSION (
    PRIMARY_ID char(36) not null,
    SESSION_ID char(36) not null,
    CREATION_TIME bigint not null,
    LAST_ACCESS_TIME bigint not null,
    MAX_INACTIVE_INTERVAL int not null,
    EXPIRY_TIME bigint not null,
    PRINCIPAL_NAME varchar(100),
    constraint SPRING_SESSION_PK primary key (PRIMARY_ID)
) engine=InnoDB row_format=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1
ON SPRING_SESSION (SESSION_ID);

CREATE INDEX SPRING_SESSION_IX2
ON SPRING_SESSION (EXPIRY_TIME);

CREATE INDEX SPRING_SESSION_IX3
ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID char(36) not null,
    ATTRIBUTE_NAME varchar(200) not null,
    ATTRIBUTE_BYTES blob not null,
    constraint SPRING_SESSION_ATTRIBUTES_PK
       primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
       foreign key (SESSION_PRIMARY_ID)
           references SPRING_SESSION(PRIMARY_ID)
           on delete cascade
) engine=InnoDB row_format=DYNAMIC;