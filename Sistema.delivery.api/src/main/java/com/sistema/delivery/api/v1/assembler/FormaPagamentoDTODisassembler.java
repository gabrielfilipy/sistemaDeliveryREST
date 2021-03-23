package com.sistema.delivery.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.model.input.FormaPagamentoInputDTO;

@Component
public class FormaPagamentoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO)
	{
		return modelMapper.map(formaPagamentoInputDTO, Pagamento.class);
	}
	
	public void toCopyDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO, 
			Pagamento pagamento)
	{
		modelMapper.map(formaPagamentoInputDTO, pagamento); 
	}
}
