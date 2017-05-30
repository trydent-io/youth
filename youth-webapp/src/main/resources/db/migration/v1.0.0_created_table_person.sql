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

create table if not exists contact (
  id          int(11)      not null auto_increment,
  picture     binary       null,
  email       varchar(255) not null,
  mobilephone varchar(12),
  phone       varchar(12),
  person_id   int(11)      not null,
  primary key (id),
  constraint contact_person foreign key (person_id) references person (id)
    on delete no action
    on update cascade,
  constraint contact_mobilephone unique key (email, mobilephone)
);

create table if not exists person_audit (
  id         int(11)       not null auto_increment,
  fields     varchar(1024) not null,
  created    date          not null,
  modified   date,
  deleted    date,
  createdBy  varchar(255)  not null,
  modifiedBy varchar(255),
  deletedBy  varchar(255),
  person_id  int(11)       not null,
  primary key (id),
  constraint person_audit foreign key (person_id) references person (id)
    on update no action
    on delete no action
);

create view contact_person as
  select
    p.uuid,
    p.firstName || ' ' || coalesce(p.secondName, '') || ' ' || p.lastName as fullName,
    c.picture,
    c.email,
    c.mobilephone
  from person p
    join contact c on p.id = c.person_id
    join person_audit pa on p.id = pa.person_id
  where pa.deleted is null
