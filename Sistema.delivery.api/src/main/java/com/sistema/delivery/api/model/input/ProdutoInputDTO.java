package com.sistema.delivery.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInputDTO {

	@ApiModelProperty(example = "Espetinho de Cupim", required = true)
	@NotBlank
    private String nome;
    
	@ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
    @NotBlank
    private String descricao;
    
	@ApiModelProperty(example = "12.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    
	@ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean ativo;
	
}
