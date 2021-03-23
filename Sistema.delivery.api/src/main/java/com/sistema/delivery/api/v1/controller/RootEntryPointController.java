package com.sistema.delivery.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.SistemaSecurity;
import com.sistema.delivery.api.v1.SistemaLink;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private SistemaLink sistemaLink;
	
	@Autowired
	private SistemaSecurity sistemaSecurity;
	
	@GetMapping
	public RootEntryPointModel root()
	{
		var rootEntryPointModel = new RootEntryPointModel();
		
		if (sistemaSecurity.podeConsultarCozinhas()) {
		rootEntryPointModel.add(sistemaLink.linkToCozinhas("cozinhas"));
		}
		
		if (sistemaSecurity.podePesquisarPedidos()) {
		rootEntryPointModel.add(sistemaLink.linkToPedidos("pedidos"));
		}
		
		if (sistemaSecurity.podeConsultarRestaurantes()) {
		rootEntryPointModel.add(sistemaLink.linkToRestaurantes("restaurantes"));
		}
		
		if (sistemaSecurity.podeConsultarUsuariosGruposPermissoes()) {
		rootEntryPointModel.add(sistemaLink.linkToGrupos("grupos"));
		rootEntryPointModel.add(sistemaLink.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(sistemaLink.linkToUsuarios("permissoes"));
		}
		
		if (sistemaSecurity.podeConsultarFormasPagamento()) {
		rootEntryPointModel.add(sistemaLink.linkToFormasPagamento("formas-pagamento"));
		}
		
		if (sistemaSecurity.podeConsultarEstados()) {
		rootEntryPointModel.add(sistemaLink.linkToEstados("estados"));
		}
		
		if (sistemaSecurity.podeConsultarCidades()) {
		rootEntryPointModel.add(sistemaLink.linkToCidades("cidades"));
		}
		
		if (sistemaSecurity.podeConsultarEstatisticas()) {
		rootEntryPointModel.add(sistemaLink.linkToEstatisticas("estatisticas"));
		}
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>
	{
		
	}
	
}
