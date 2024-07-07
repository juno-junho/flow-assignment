drop database if exists file_upload;
create database file_upload;
use file_upload;

drop table if exists custom_file_extension cascade;
drop table if exists file cascade;
drop table if exists fixed_file_extension cascade;
drop table if exists upload_history cascade;
drop table if exists users cascade;

create table custom_file_extension
(
    id               bigint unsigned auto_increment primary key,
    file_extension   varchar(20)     not null,
    user_id          bigint unsigned not null,
    created_at       datetime(6),
    last_modified_at timestamp(6)
) engine = InnoDB
  default charset = utf8mb4;


create table file
(
    id                  bigint unsigned auto_increment primary key,
    upload_file_name    varchar(255)    not null,
    generated_file_name varchar(255)    not null,
    file_extension      varchar(255)    not null,
    user_id             bigint unsigned not null,
    created_at          timestamp(6),
    last_modified_at    timestamp(6)
) engine = InnoDB
  default charset = utf8mb4;

create table fixed_file_extension
(
    id               bigint unsigned auto_increment primary key,
    file_extension   varchar(255)    not null,
    restricted       boolean         not null,
    user_id          bigint unsigned not null,
    created_at       timestamp(6),
    last_modified_at timestamp(6)
) engine = InnoDB
  default charset = utf8mb4;

create table upload_history
(
    id               bigint unsigned auto_increment primary key,
    ip_address       varchar(45)     not null,
    user_id          bigint unsigned not null,
    created_at       timestamp(6),
    last_modified_at timestamp(6)
) engine = InnoDB
  default charset = utf8mb4;

create table users
(
    id               bigint unsigned auto_increment primary key,
    role enum ('ADMIN','GUEST') not null
) engine = InnoDB
  default charset = utf8mb4;
