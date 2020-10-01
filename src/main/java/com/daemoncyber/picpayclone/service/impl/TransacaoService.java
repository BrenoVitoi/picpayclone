package com.daemoncyber.picpayclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daemoncyber.conversor.TransacaoConversor;
import com.daemoncyber.picpayclone.dto.TransacaoDTO;
import com.daemoncyber.picpayclone.modelo.Transacao;
import com.daemoncyber.picpayclone.repository.TransacaoRepository;
import com.daemoncyber.picpayclone.service.ICartaoCreditoService;
import com.daemoncyber.picpayclone.service.ITransacaoService;
import com.daemoncyber.picpayclone.service.IUsuarioService;

@Service
public class TransacaoService implements ITransacaoService {

	@Autowired
	private TransacaoConversor transacaoConversor;

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private ICartaoCreditoService cartaoCreditoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public TransacaoDTO processar(TransacaoDTO transacaoDTO) {
		Transacao transacaoSalva = salvar(transacaoDTO);
		cartaoCreditoService.salvar(transacaoDTO.getCartaoCredito());
		usuarioService.atualizarSaldo(transacaoSalva, transacaoDTO.getIsCartaoCredito());
		return transacaoConversor.converterEntidadeParaDto(transacaoSalva);
	}

	private Transacao salvar(TransacaoDTO transacaoDTO) {
		Transacao transacao = transacaoConversor.converterDtoParaEntidade(transacaoDTO);
		usuarioService.validar(transacao.getDestino(), transacao.getDestino());
		return transacaoRepository.save(transacao);
	}

	@Override
	public Page<TransacaoDTO> listar(Pageable paginacao, String loginUsuario) {
		Page<Transacao> transacaoes = transacaoRepository.findByOrigem_LoginOrDestino_Login(loginUsuario, loginUsuario, paginacao);
		return transacaoConversor.converterPageEntidadeParaDto(transacaoes);
	}

}
