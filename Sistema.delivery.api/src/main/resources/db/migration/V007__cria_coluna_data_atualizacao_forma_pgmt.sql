alter table pagamento add data_atualizacao datetime null;
update pagamento set data_atualizacao = utc_timestamp;
alter table pagamento modify data_atualizacao datetime not null;