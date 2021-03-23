package com.sistema.delivery.api.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

	public static Pageable translater(Pageable pageable, Map<String, String> fieldsMaps)
	{
		var ordena = pageable.getSort().stream()
			.filter(order -> fieldsMaps.containsKey(order.getProperty()))
			.map(order -> new Sort.Order(order.getDirection(), fieldsMaps.get(order.getProperty())))
			.collect(Collectors.toList());
		
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
				Sort.by(ordena));
	}
	
}
