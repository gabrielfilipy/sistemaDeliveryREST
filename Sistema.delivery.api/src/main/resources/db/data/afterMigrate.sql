set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from item_pedido;
delete from pedido;
delete from pagamento;
delete from grupo;
delete from grupo_permissoes;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from foto_produto;
delete from oauth_client_details;

set foreign_key_checks = 1;

alter table item_pedido auto_increment = 1;
alter table pedido auto_increment = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1; 
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

INSERT INTO cozinha (id, nome) VALUES (NULL, "Norte-Americana");
INSERT INTO cozinha (id, nome) VALUES (NULL, "Manauara");

INSERT INTO estado (id, nome) VALUES (NULL, "Amazonas");
INSERT INTO estado (id, nome) VALUES (NULL, "Pará");

INSERT INTO grupo (id, nome) VALUES (NULL, "Gerente");
INSERT INTO grupo (id, nome) VALUES (NULL, "Vendedor");
INSERT INTO grupo (id, nome) VALUES (NULL, "Secretário");
INSERT INTO grupo (id, nome) VALUES (NULL, "Cadastrador");

insert into permissao (id, nome, descricao) values (1, 'EDITAR_COZINHAS', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_FORMAS_PAGAMENTO', 'Permite criar ou editar formas de pagamento');
insert into permissao (id, nome, descricao) values (3, 'EDITAR_CIDADES', 'Permite criar ou editar cidades');
insert into permissao (id, nome, descricao) values (4, 'EDITAR_ESTADOS', 'Permite criar ou editar estados');
insert into permissao (id, nome, descricao) values (5, 'CONSULTAR_USUARIOS', 'Permite consultar usuários');
insert into permissao (id, nome, descricao) values (6, 'EDITAR_USUARIOS', 'Permite criar ou editar usuários');
insert into permissao (id, nome, descricao) values (7, 'EDITAR_RESTAURANTES', 'Permite criar, editar ou gerenciar restaurantes');
insert into permissao (id, nome, descricao) values (8, 'CONSULTAR_PEDIDOS', 'Permite consultar pedidos');
insert into permissao (id, nome, descricao) values (9, 'GERENCIAR_PEDIDOS', 'Permite gerenciar pedidos');
insert into permissao (id, nome, descricao) values (10, 'GERAR_RELATORIOS', 'Permite gerar relatórios');

# Adiciona todas as permissoes no grupo do gerente
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 2);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 3);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 4);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 5);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 6);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 7);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 8);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 9);
insert into grupo_permissoes (grupo_id, permissao_id) values (1, 10);


# Adiciona permissoes no grupo do vendedor
insert into grupo_permissoes (grupo_id, permissao_id)
select 2, id from permissao where nome like 'CONSULTAR_%';

insert into grupo_permissoes (grupo_id, permissao_id) values (2, 5);

# Adiciona permissoes no grupo do auxiliar
insert into grupo_permissoes (grupo_id, permissao_id)
select 3, id from permissao where nome like 'CONSULTAR_%';

# Adiciona permissoes no grupo cadastrador
insert into grupo_permissoes (grupo_id, permissao_id)
select 4, id from permissao where nome like '%_RESTAURANTES' or nome like '%_PRODUTOS';
 

INSERT INTO cidade (id, nome, estado_id) VALUES (NULL, "Manacapuru", 1);
INSERT INTO cidade (id, nome, estado_id) VALUES (NULL, "Novo Airão", 2);

INSERT INTO restaurante (endereco_cidade_id, data_cadastro, data_atualizacao, ativo, aberto, cozinha_id, nome, taxa_frete, endereco_cep, endereco_complemento, endereco_bairro, endereco_rua, endereco_logradouro) VALUES (1, utc_timestamp, utc_timestamp, true, 2, true, "Bobs", 2.99, "69400025", "condominio", "Centro", "Av. Constantino Nery", "LOGRADOURO");
INSERT INTO restaurante (endereco_cidade_id, data_cadastro, data_atualizacao, ativo, aberto, cozinha_id, nome, taxa_frete, endereco_cep, endereco_complemento, endereco_bairro, endereco_rua, endereco_logradouro) VALUES (2, utc_timestamp, utc_timestamp, true, 1, true, "Mcdonalds", 0.0, "69400425", "casa", "Cidade de Deus", "Av. Nossa Senhora da Conceição", "LOGRADOURO");
INSERT INTO restaurante (endereco_cidade_id, data_cadastro, data_atualizacao, ativo, aberto, cozinha_id, nome, taxa_frete, endereco_cep, endereco_complemento, endereco_bairro, endereco_rua, endereco_logradouro) VALUES (1, utc_timestamp, utc_timestamp, true, 2, true, "Girafas", 1.99, "69400222", "condominio", "Centro", "Av. Pedro Haits", "LOGRADOURO");
INSERT INTO restaurante (endereco_cidade_id, data_cadastro, data_atualizacao, ativo, aberto, cozinha_id, nome, taxa_frete, endereco_cep, endereco_complemento, endereco_bairro, endereco_rua, endereco_logradouro) VALUES (2, utc_timestamp, utc_timestamp, true, 1, true, "Del Vale", 0.0, "69400425", "casa", "Nova Cidade", "Rua Mrechal Deodoro", "LOGRADOURO");


INSERT INTO pagamento (descricao, data_atualizacao) VALUES ("Crédito", utc_timestamp);
INSERT INTO pagamento (descricao, data_atualizacao) VALUES ("Débito", utc_timestamp);
INSERT INTO pagamento (descricao, data_atualizacao) VALUES ("Dinheiro", utc_timestamp);

INSERT INTO restaurante_forma_pagamento (restaurante_id, pagamento_id) VALUES (1, 1), (1, 2), (2, 1);

INSERT INTO usuario (nome, email, senha) VALUES ('Filipy Gabriel', 'filipy.hughes@gmail.com', '$2y$12$f8qwd13Ck3YtAEzH3Rf76e3lk7uOxqA1eAoCdnrnICZjwbHrHsY52');
INSERT INTO usuario (nome, email, senha) VALUES ('Tiago Fonseca', 'filipy.hughes+Tiago@gmail.com', '$2y$12$f8qwd13Ck3YtAEzH3Rf76e3lk7uOxqA1eAoCdnrnICZjwbHrHsY52');
INSERT INTO usuario (nome, email, senha) VALUES ('Guilherme Grilo', 'filipy.hughes+Guilher@gmail.com', '$2y$12$f8qwd13Ck3YtAEzH3Rf76e3lk7uOxqA1eAoCdnrnICZjwbHrHsY52');

INSERT INTO restaurante_usuario_responsavel (restaurante_id, usuario_id) VALUES (1, 1), (1, 2), (2, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 0, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 2);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (2, 2), (3, 3);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 2, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'f9981ca4-5a5e-4da3-af04-933861df3e54', 3, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CONFIRMADO', utc_timestamp, 298.90, 10, 308.90);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (3, 'f9981ca4-5a5e-4da3-af04-933861df3e53', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'ENTREGUE', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
values (4, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CONFIRMADO', utc_timestamp, 79, 0, 79);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
values (5, 'd178b637-a785-4768-a3cb-aa1ce5a8cdag', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CONFIRMADO', utc_timestamp, 79, 0, 79); 

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'sistema-web', null, '$2y$12$w3igMjsfS5XoAYuowoH3C.54vRFWlcXSHLjX7MwF990Kc2KKKh72e',
  'READ,WRITE', 'password', null, null,
  60 * 60 * 6, 60 * 24 * 60 * 60, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'foodnanalytics', null, '$2y$12$fahbH37S2pyk1RPuIHKP.earzFmgAJJGo26rE.59vf4wwiiTKHnzO',
  'READ,WRITE', 'authorization_code', 'http://www.foodanalytics.local:8082', null,
  null, null, null
);

#http://localhost:8080/oauth/authorize?response_type=code&client_id=foodnanalytics&state=abc&redirect_uri=http://www.foodanalytics.local:8082

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'faturamento', null, '$2y$12$fHixriC7yXX/i1/CmpnGH.RFyK/l5YapLCFOEbIktONjE8ZDykSnu',
  'READ,WRITE', 'client_credentials', null, 'CONSULTAR_PEDIDOS,GERAR_RELATORIOS',
  null, null, null
);