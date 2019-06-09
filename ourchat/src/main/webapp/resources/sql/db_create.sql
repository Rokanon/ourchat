create table user (
    id bigint(20) not null auto_increment,
    password varchar(128) default null,
    mail varchar(30) default null,
    primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table profile (
    id bigint(20) NOT null auto_increment,
    user_id bigint(20) not null,
    name varchar(24) default null,
    primary key (id),
    foreign key(user_id) references user(id)
) engine=InnoDB default charset=utf8mb4;

create table message (
    id bigint(20) not null auto_increment,
    message text not null, -- Maximum size of 65,535 characters.
    seen tinyint (1) not null default 0,
    create_date datetime not null,
    update_date datetime not null,
    primary key(id)
) engine=InnoDB default charset=utf8mb4;
-- odavde 
create table `group` (
    id bigint(20) not null auto_increment,
    name varchar (20) not null,
    create_date datetime not null,
    primary key(id) 
) engine=InnoDB default charset=utf8mb4;

create table subscriber (
    id bigint(20) not null auto_increment,
    profile_id bigint(20) not null,
    group_id bigint(20) not null,
    primary key(id),
    foreign key(profile_id) references profile(id),
    foreign key(group_id) references `group`(id)
) engine=InnoDB default charset=utf8mb4;

create table chat_private (
    id bigint(20) not null auto_increment,
    sender_id bigint(20) not null,
    receiver_id bigint(20) not null,
    message_id bigint(20) not null,
    primary key(id),
    foreign key(sender_id) references profile(id),
    foreign key(receiver_id) references profile(id),
    foreign key(message_id) references message(id)
) engine=InnoDB default charset=utf8mb4;

create table chat_group (
    id bigint(20) not null auto_increment,
    sender_id bigint(20) not null,
    group_id bigint(20) not null,
    message_id bigint(20) not null,
    primary key(id),
    foreign key(sender_id) references profile(id),
    foreign key(group_id) references `group`(id),
    foreign key(message_id) references message(id)
) engine=InnoDB default charset=utf8mb4;

-- create trigger update_message_seen 
--     after update
--         on subscriber for each row
--         update message set seen = 1 where 
--         subscriber.group_id not in (select sub.group_id from subscriber sub where )
-- --         begin
-- --             
-- --         end;










