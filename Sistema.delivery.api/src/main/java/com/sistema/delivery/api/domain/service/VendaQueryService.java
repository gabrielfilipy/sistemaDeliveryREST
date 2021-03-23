package com.sistema.delivery.api.domain.service;

import java.util.List;

import com.sistema.delivery.api.domain.filtragem.VendasDiariaFiltra;
import com.sistema.delivery.api.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiaria(VendasDiariaFiltra vendasDiariasFiltra, String timeOffset);
	
}
