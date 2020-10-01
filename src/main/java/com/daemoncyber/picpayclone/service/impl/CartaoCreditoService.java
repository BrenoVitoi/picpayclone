package com.daemoncyber.picpayclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daemoncyber.conversor.CartaoCreditoConversor;
import com.daemoncyber.picpayclone.dto.CartaoCreditoDTO;
import com.daemoncyber.picpayclone.modelo.CartaoCredito;
import com.daemoncyber.picpayclone.repository.CartaoCreditoRepository;
import com.daemoncyber.picpayclone.service.ICartaoCreditoService;
import com.daemoncyber.picpayclone.service.IUsuarioService;

@Service
public class CartaoCreditoService implements ICartaoCreditoService {

	@Autowired
	private CartaoCreditoRepository cartaoCreditoRepository;

	@Autowired
	private CartaoCreditoConversor cartaoCreditoConversor;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public CartaoCreditoDTO salvar(CartaoCreditoDTO cartaoCreditoDTO) {
		CartaoCreditoDTO cartaoCreditoRetorno = null;
		if (cartaoCreditoDTO.getIsSalva()) {
			CartaoCredito cartaoCredito = cartaoCreditoConversor.converterDtoParaEntidade(cartaoCreditoDTO);
			usuarioService.validar(cartaoCredito.getUsuario());
			CartaoCredito cartaoCreditoSalvo = cartaoCreditoRepository.save(cartaoCredito);
			cartaoCreditoRetorno = cartaoCreditoConversor.converterEntidadeParaDto(cartaoCreditoSalvo);
		}

		return cartaoCreditoRetorno;
	}

}
