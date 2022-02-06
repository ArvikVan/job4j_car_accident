create table if not exists accident (
    id serial primary key,
    name varchar (5000),
    text varchar (5000),
    address varchar (5000)
);
create table if not exists rule (
    id serial primary key,
    name varchar (500)
);
create table if not exists accident_type (
    id serial primary key,
    name varchar (500)
);
create table if not exists accident_rule (
    id serial primary key,
    accident_id int references accident(id),
    rule_id int references rule(id)
);

insert into rule (name) values ('Статья 1 п.3db');
insert into rule (name) values ('Статья 12 п.2db');
insert into rule (name) values ('Статья 23 п.1');
insert into accident_type (name) values ('Two cars');
insert into accident_type (name) values ('Human and vehicle');
insert into accident_type (name) values ('vehicle and bycicle');
insert into accident (name_accident, text, address)  values (
    'ДТП', 'Лобовое столкновение, тс восстановлению не подлежат', 'Комсомольская/Пролетарская!');
insert into accident (name_accident, text, address)  values (
    'Автомобиль разбился', 'Водитель ТС не справился с управлением и врезался в столб!', 'Кирова/Партизанская');
insert into accident (name_accident, text, address) values (
    'Порча имущества', 'Гр.Залупин бросил кирпич в ветровое стекло и разбил его!', 'ПромЗона-246');
