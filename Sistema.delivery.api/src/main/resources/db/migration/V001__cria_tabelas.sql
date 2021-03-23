create table cidade (id bigint not null auto_increment, nome varchar(255), estado_id bigint, primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255), primary key (id)) engine=InnoDB;
create table grupo_permissoes (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table item_pedido (id bigint not null auto_increment, observacao varchar(255), preco_total decimal(19,2), preco_unitario decimal(19,2), quantidade integer, pedido_id bigint not null, produto_id bigint not null, primary key (id)) engine=InnoDB;
create table pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;


create table pedido (id bigint not null auto_increment, 
data_cancelamento datetime, 
data_confirmacao datetime, 
data_criacao datetime, 
data_entrega datetime, 
endereco_cidade_id bigint not null, 
endereco_logradouro varchar(255), 
endereco_numero varchar(255), 
endereco_bairro varchar(255), 
endereco_cep varchar(255), 
endereco_complemento varchar(255), 
endereco_rua varchar(255), 
status varchar(10) not null, 
subtotal decimal(19,2), 
taxa_frete decimal(19,2), 
valor_total decimal(19,2), 
usuario_cliente_id bigint not null, 
forma_pagamento_id bigint not null, 
restaurante_id bigint not null, 

primary key (id)) engine=InnoDB;

create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table produto (id bigint not null auto_increment, ativo bit, nome varchar(255), preco decimal(19,2), descricao varchar(255), restaurante_id bigint, primary key (id)) engine=InnoDB;
create table restaurante (id bigint not null auto_increment, data_atualizacao datetime not null, data_cadastro datetime not null, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_rua varchar(255), nome varchar(255), taxa_frete decimal(19,2), endereco_cidade_id bigint, endereco_logradouro varchar(255), endereco_numero varchar(255), cozinha_id bigint, primary key (id)) engine=InnoDB;
create table restaurante_forma_pagamento (restaurante_id bigint not null, pagamento_id bigint not null) engine=InnoDB;
create table usuario (id bigint not null auto_increment, data_cadastro datetime, email varchar(255), nome varchar(255), senha varchar(255), primary key (id)) engine=InnoDB;
create table usuario_grupo (usuario_id bigint not null, grupo_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table grupo_permissoes add constraint FKbjj8fbcfxr7joapufexdn7fv0 foreign key (permissao_id) references permissao (id);
alter table grupo_permissoes add constraint FKd7wt9tnvrfttdcl5ofoelgi6j foreign key (grupo_id) references grupo (id);
alter table item_pedido add constraint FK60ym08cfoysa17wrn1swyiuda foreign key (pedido_id) references pedido (id);
alter table item_pedido add constraint FKtk55mn6d6bvl5h0no5uagi3sf foreign key (produto_id) references produto (id);
alter table pedido add constraint FKcccmjvm9ytuxbe00h3wmtm77y foreign key (usuario_cliente_id) references usuario (id);
alter table pedido add constraint FKlywk2iw1c6xydso8skig70m0v foreign key (forma_pagamento_id) references pagamento (id);
alter table pedido add constraint FK3eud5cqmgsnltyk704hu3qj71 foreign key (restaurante_id) references restaurante (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante_forma_pagamento add constraint FKdxvkq3o2vjmq6l40ret7ytr02 foreign key (pagamento_id) references pagamento (id);
alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
