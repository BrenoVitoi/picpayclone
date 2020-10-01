package com.daemoncyber.picpayclone.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daemoncyber.conversor.UsuarioConversor;
import com.daemoncyber.picpayclone.constantes.MensagemValidacao;
import com.daemoncyber.picpayclone.dto.UsuarioDTO;
import com.daemoncyber.picpayclone.exception.NegocioException;
import com.daemoncyber.picpayclone.modelo.Transacao;
import com.daemoncyber.picpayclone.modelo.Usuario;
import com.daemoncyber.picpayclone.repository.UsuarioRepository;
import com.daemoncyber.picpayclone.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioConversor usuarioConversor;

	@Override
	public UsuarioDTO consultar(String login) {
		Usuario usuario = consultarEntidade(login);
		return usuarioConversor.converterEntidadeParaDto(usuario);
	}

	@Override
	@Transactional
	public Usuario consultarEntidade(String login) {
		return usuarioRepository.findByLogin(login);
	}

	@Override
	@Async("asyncExecutor")
	@Transactional
	public void atualizarSaldo(Transacao transacaoSalva, Boolean isCartaoCredito) {
		decrementarSaldo(transacaoSalva, isCartaoCredito);
		incrementarSaldo(transacaoSalva);
	}

	private void incrementarSaldo(Transacao transacaoSalva) {
		usuarioRepository.updateIncrementarSaldo(transacaoSalva.getDestino().getLogin(), transacaoSalva.getValor());
	}

	private void decrementarSaldo(Transacao transacaoSalva, Boolean isCartaoCredito) {
		if (!isCartaoCredito) {
			usuarioRepository.updateDecrementarSaldo(transacaoSalva.getOrigem().getLogin(), transacaoSalva.getValor());
		}
	}

	@Override
	public void validar(Usuario... usuarios) {

		Arrays.asList(usuarios).stream().forEach(usuario -> {
			if (usuario == null) {
				throw new NegocioException(MensagemValidacao.ERRO_USUARIO_INEXISTENTE);
			}

		});
	}

	@Override
	public List<UsuarioDTO> listar(String login) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarioConversor.converterEntidadesParaDtos(
				usuarios.stream().filter(v -> !v.getLogin().equals(login)).collect(Collectors.toList()));
	}

}
