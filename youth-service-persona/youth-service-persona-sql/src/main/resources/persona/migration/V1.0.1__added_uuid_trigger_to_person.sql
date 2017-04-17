create trigger `persona`.`before_insert_uuid`
before insert on `person`
for each row
set new.uuid = uuid();
