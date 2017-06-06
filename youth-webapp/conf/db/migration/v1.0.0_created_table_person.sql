create table if not exists person (
  id         int(11)      not null auto_increment,
  uuid       varchar(36)  not null,
  firstName  varchar(127) not null,
  secondName varchar(127),
  lastName   varchar(255) not null,
  fiscalCode varchar(16)  not null,
  birthDate  date,
  gender     varchar(6)   not null default 'male',
  primary key (id),
  constraint person_fiscalCode unique key (fiscalCode),
  constraint person_uuid unique key (uuid)
);

create table if not exists profile (
  id          int(11)      not null auto_increment,
  picture     binary       null,
  email       varchar(255) not null,
  mobilePhone varchar(12),
  homePhone   varchar(12),
  person_id   int(11)      not null,
  primary key (id),
  constraint profile_person foreign key (person_id) references person (id)
    on delete no action
    on update cascade,
  constraint profile_mobilephone unique key (email, mobilePhone)
);

create table if not exists person_audit (
  id         int(11)      not null auto_increment,
  created    datetime     not null,
  modified   datetime     not null,
  deleted    datetime,
  createdBy  varchar(255) not null,
  modifiedBy varchar(255) not null,
  deletedBy  varchar(255),
  person_id  int(11)      not null,
  primary key (id),
  constraint person_audit foreign key (person_id) references person (id)
    on update no action
    on delete no action
);

create view profile_person as
  select
    pe.uuid,
    pe.firstName || ' ' || coalesce(pe.secondName, '') || ' ' || pe.lastName as fullName,
    pr.picture,
    pr.email,
    pr.mobilePhone
  from person pe
    left join profile pr on pe.id = pr.person_id
    left join person_audit pa on pe.id = pa.person_id
  where pa.deleted is null
