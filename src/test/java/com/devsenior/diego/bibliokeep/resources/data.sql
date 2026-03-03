insert into roles (id, name) values (1, 'ADMIN');
insert into roles (id, name) values (2, 'USER');

insert into users (id, annual_goal, email, password)
values ('07d80487-ad59-4f99-9cd1-408e18147538', 10, 'maria@example.com', 
        '$2a$10$jzAvI7pIuOcUntYH6wig0u7ZtPXZJYkcbdoYmrtj3hs9B6u4mz1me');

insert into user_roles (user_id, role_id)
values ('f53b6038-52dd-45de-a123-1ebcd597efd5', 1);
insert into user_roles (user_id, role_id)
values ('1877a0af-afae-4360-9880-c5a2f91100be', 2);