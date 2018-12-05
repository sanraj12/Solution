DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

insert into users(username, password, enabled)values('target','password',true);
insert into authorities(username,authority)values('target','ROLE_ADMIN');
insert into authorities(username,authority)values('target','ROLE_USER');
 
insert into users(username, password, enabled)values('user','password',true);
insert into authorities(username,authority)values('user','ROLE_USER');