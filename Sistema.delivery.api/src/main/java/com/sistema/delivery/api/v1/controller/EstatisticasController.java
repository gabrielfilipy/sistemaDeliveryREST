package com.sistema.delivery.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.delivery.api.core.security.CheckSecurity;
import com.sistema.delivery.api.domain.filtragem.VendasDiariaFiltra;
import com.sistema.delivery.api.domain.model.dto.VendaDiaria;
import com.sistema.delivery.api.domain.service.VendaQueryService;
import com.sistema.delivery.api.domain.service.VendaReportService;
import com.sistema.delivery.api.openapi.controller.EstatisticasControllerOpenApi;
import com.sistema.delivery.api.v1.SistemaLink;

@RestController
@RequestMapping(path = "/v1/estatistica/")
public class EstatisticasController implements EstatisticasControllerOpenApi{

	@Autowired
	private VendaQueryService vendaQueryService;
	 
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private SistemaLink sistemaLink;
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendasDiariaFiltra vendasDiariasFiltra, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset)
	{
		return vendaQueryService.consultarVendasDiaria(vendasDiariasFiltra, timeOffset);
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(path = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendasDiariaFiltra vendasDiariasFiltra, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset)
	{
		byte[] pdf = vendaReportService.emitirVendasDiarias(vendasDiariasFiltra, timeOffset);
		
		var headers = new HttpHeaders();
		//TODO para fazer downlod
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers) 
				.body(pdf);
	}
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(sistemaLink.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	}   
	 
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
	
}
