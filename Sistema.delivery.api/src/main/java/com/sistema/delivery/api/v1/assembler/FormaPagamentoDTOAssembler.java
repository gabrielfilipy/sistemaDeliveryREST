package com.sistema.delivery.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.v1.SistemaLink;
import com.sistema.delivery.api.v1.controller.FormaPagamentoController;
import com.sistema.delivery.api.v1.model.FormaPagamentoDTO;

@Component
public class FormaPagamentoDTOAssembler  extends RepresentationModelAssemblerSupport<Pagamento, FormaPagamentoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }
	
	@Override
	public FormaPagamentoDTO toModel(Pagamento pagamento)
	{
		FormaPagamentoDTO formaPagamentoModel = 
                createModelWithId(pagamento.getId(), pagamento);
        
        modelMapper.map(pagamento, formaPagamentoModel);
        
        if (sistemaSecurity.podeConsultarFormasPagamento()) {
        formaPagamentoModel.add(sistemaLink.linkToFormasPagamento("formasPagamento"));
        }
        
        return formaPagamentoModel;
	}
	
	 @Override
	 public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends Pagamento> entities) 
	 {
		 CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);
		    
		 if (sistemaSecurity.podeConsultarFormasPagamento()) {
		    collectionModel.add(sistemaLink.linkToFormasPagamento());
		 }
		        
		 return collectionModel;
	}
	
}
