create table user (
    id bigint(20) NOT null auto_increment,
    username varchar(24) default null,
    password varchar(128) default null,
    mail varchar(30) default null,
    PRIMARY KEY (id)
) engine=InnoDB default charset=utf8mb4;

-- create table name (
--     id bigint(20) NOT null auto_increment,
-- ) engine=InnoDB default charset=utf8mb4;