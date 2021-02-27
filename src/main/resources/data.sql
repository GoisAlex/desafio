delete
from cliente;
delete
from cidade;
insert into cidade(cidade_id, nome, estado)
values (cidade_sequence.NEXTVAL, 'Cuiabá', 'MATO_GROSSO');
insert into cidade(cidade_id, nome, estado)
values (cidade_sequence.NEXTVAL, 'Lucas do Rio Verde', 'MATO_GROSSO');
insert into cidade(cidade_id, nome, estado)
values (cidade_sequence.NEXTVAL, 'São Paulo', 'SAO_PAULO');
insert into cidade(cidade_id, nome, estado)
values (cidade_sequence.NEXTVAL, 'Manaus', 'AMAZONAS');
insert into cliente(cliente_id, nome_completo, sexo, idade, data_nascimento, cidade_id)
values (cliente_sequence.NEXTVAL, 'Alex Gois', 'MASCULINO', 30, '1990-04-20',
        (select cidade_id from cidade where nome = 'Cuiabá'));
insert into cliente(cliente_id, nome_completo, sexo, idade, data_nascimento, cidade_id)
values (999, 'Gois 2', 'MASCULINO', 30, '1990-04-20',
        (select cidade_id from cidade where nome = 'Cuiabá'));
