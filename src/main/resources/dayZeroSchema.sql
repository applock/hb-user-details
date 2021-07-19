create table users(
	id varchar(50) not null,
    username varchar(50) not null,
    password varchar(250) not null,
    email varchar(50),
    mobile varchar(50) not null,
    enabled boolean not null,
    primary key(id, username),
    unique(username));
    
create table authorities (
	id varchar(50) not null primary key,
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority);
     
create table persistent_logins (
  username varchar(64) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null);     

create index ix_users_username_lower on users (lower(username) varchar_pattern_ops);

create table acl_sid(
  id bigserial not null primary key,
  principal boolean not null,
  sid varchar(100) not null,
  constraint unique_uk_1 unique(sid,principal));

create table acl_class(
  id bigserial not null primary key,
  class varchar(100) not null,
  constraint unique_uk_2 unique(class));

create table acl_object_identity(
  id bigserial primary key,
  object_id_class bigint not null,
  object_id_identity bigint not null,
  parent_object bigint,
  owner_sid bigint,
  entries_inheriting boolean not null,
  constraint unique_uk_3 unique(object_id_class,object_id_identity),
  constraint foreign_fk_1 foreign key(parent_object) references acl_object_identity(id),
  constraint foreign_fk_2 foreign key(object_id_class) references acl_class(id),
  constraint foreign_fk_3 foreign key(owner_sid) references acl_sid(id));

create table acl_entry(
  id bigserial primary key,
  acl_object_identity bigint not null,
  ace_order int not null,
  sid bigint not null,
  mask integer not null,
  granting boolean not null,
  audit_success boolean not null,
  audit_failure boolean not null,
  constraint unique_uk_4 unique(acl_object_identity,ace_order),
  constraint foreign_fk_4 foreign key(acl_object_identity)
      references acl_object_identity(id),
  constraint foreign_fk_5 foreign key(sid) references acl_sid(id));
  
!-- select username from users where username = lower('just3ws');
 
insert into users values ('admin', '$2a$10$ShWgdCBTfkMEihcVa4ENbO8zw8kpg.Cc2EDQtZcx1RTlBpBgNtKc2', 'ibsanchar@gmail.com', '9741972215', true);
insert into users values ('iman', '$2a$10$tiuzO1AMagKCpnqun5.7Y.VLMytPkMmncPi7wlpaYr1C6jqHIWedm' , 'ibsanchar@gmail.com', '9741972215', true);
insert into users values ('prakash', '$2a$10$0TeKf7RaE0sgiLDGVdmhdO5MSVhBG8Lo8jjPA/NsL8pcH63Rk9Ux6', 'ibsanchar@gmail.com', '9741972215', true);