package com.daemoncyber.conversor;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daemoncyber.picpayclone.dto.CartaoCreditoDTO;
import com.daemoncyber.picpayclone.dto.TransacaoDTO;
import com.daemoncyber.picpayclone.modelo.CartaoCredito;
import com.daemoncyber.picpayclone.modelo.Transacao;
import com.daemoncyber.picpayclone.service.IUsuarioService;
import com.daemoncyber.picpayclone.utils.CartaoCreditoUtil;

@Component
public class CartaoCreditoConversor extends ConversorBase<CartaoCredito, CartaoCreditoDTO> {

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public CartaoCreditoDTO converterEntidadeParaDto(CartaoCredito entidade) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Transacao, TransacaoDTO>() {
			@Override
			protected void configure() {
			}
		});
		return modelMapper.map(entidade, CartaoCreditoDTO.class);
	}

	@Override
	public CartaoCredito converterDtoParaEntidade(CartaoCreditoDTO dto) {
		return CartaoCredito
				.builder()
				.bandeira(dto.getBandeira())
				.numero(CartaoCreditoUtil.mascarar(dto.getNumero()))
				.numeroToken(dto.getNumeroToken())
				.usuario(usuarioService.consultarEntidade(dto.getUsuario().getLogin()))
				.build();
	}

}
