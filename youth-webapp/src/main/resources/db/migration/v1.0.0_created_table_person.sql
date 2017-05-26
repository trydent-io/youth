create table if not exists person (
  id int(11) not null auto_increment,
  uuid varchar(35) not null,
  firstName varchar(127) not null,
  secondName varchar(127),
  lastName varchar(255) not null
)
