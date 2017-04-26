create table `persona`.`person` (
  `id`                 int(12) not null auto_increment,
  `uuid`               char(36) not null,
  `first_name`         varchar(45) not null,
  `second_name`        varchar(45) null,
  `first_family_name`  varchar(65) not null,
  `second_family_name` varchar(65) null,
  `birth_date`         date        not null,
  `fiscal_code`        varchar(16) not null,
  primary key (`id`),
  unique index `person_uuid` (`uuid` asc),
  unique index `person_fiscal` (`first_name` asc, `first_family_name` asc, `fiscal_code` asc)
);
