package com.sistema.delivery.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.delivery.api.domain.exception.FormaPagamentoNaoExisteExeption;
import com.sistema.delivery.api.domain.exception.PedidoNaoEncontradoException;
import com.sistema.delivery.api.domain.model.Cidade;
import com.sistema.delivery.api.domain.model.Pagamento;
import com.sistema.delivery.api.domain.model.Pedido;
import com.sistema.delivery.api.domain.model.Produto;
import com.sistema.delivery.api.domain.model.Restaurante;
import com.sistema.delivery.api.domain.model.Usuario;
import com.sistema.delivery.api.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private FormaPagamentoService formaPagamentoRepositoryService;
	
	@Autowired
	private CidadeRepositoryService cidadeRepositoryService;
	
	@Autowired
	private RestauranteRepositoryServive restauranteRepositoryService;
	
	@Autowired
	private UsuarioRepositoryService usuarioRepositoryService;
	
	@Autowired
	private ProdutoRepositoryService produtoRepositoryService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	/*
	
	private void validarPedido(Pedido pedido) {
    Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
    Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
    FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

    pedido.getEnderecoEntrega().setCidade(cidade);
    pedido.setCliente(cliente);
    pedido.setRestaurante(restaurante);
    pedido.setFormaPagamento(formaPagamento);
    
    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                formaPagamento.getDescricao()));
    }
	}
*/
	
	@Transactional
	public Pedido emitir(Pedido pedido)
	{
		validarPedido(pedido);
		validarItes(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido); 
	}
	
	private void validarPedido(Pedido pedido)
	{
		Cidade cidade = cidadeRepositoryService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario usuario = usuarioRepositoryService.existeOuNao(pedido.getCliente().getId());
		Restaurante restaurante = restauranteRepositoryService.buscarOuFalhar(pedido.getRestaurante().getId());
		Pagamento pagamento = formaPagamentoRepositoryService.existeOuNao(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(usuario);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(pagamento);
		
		if(restaurante.naoAceitaFormaPagamento(pagamento))
		{
			throw new FormaPagamentoNaoExisteExeption(
					String.format("Não existe essa forma %s de pagamento para esse Restaurante.", 
							pagamento.getDescricao()));
		}
		
	}
	
	private void validarItes(Pedido pedido)
	{
		pedido.getItens().forEach(item -> {
			Produto produto = produtoRepositoryService.existeOuNao(
					item.getProduto().getId(), pedido.getRestaurante().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	public Pedido buscarOuFalhar(String codigo)
	{
		return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(
				String.format("O Pedido %s não existe!", codigo)));
	}
	
}
