drop table if exists auth_user;
create table auth_user
(
    id                  int not null primary key auto_increment,
    created_at          datetime     default '',
    updated_at          datetime     default '',
    username            varchar(255) default '',
    nickname            varchar(255) default '',
    email               varchar(255) default '',
    phone               varchar(255) default '',
    account_non_expired boolean      default false,
    account_non_locked  boolean      default false,
    enabled             boolean      default false
)
