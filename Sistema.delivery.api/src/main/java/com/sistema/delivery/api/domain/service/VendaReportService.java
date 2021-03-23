package com.sistema.delivery.api.domain.service;

import com.sistema.delivery.api.domain.filtragem.VendasDiariaFiltra;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendasDiariaFiltra filtra, String timeoffset);
	
}
