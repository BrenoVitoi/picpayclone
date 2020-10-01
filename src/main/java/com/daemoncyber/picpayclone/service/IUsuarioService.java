package com.daemoncyber.picpayclone.service;

import java.util.List;

import com.daemoncyber.picpayclone.dto.UsuarioDTO;
import com.daemoncyber.picpayclone.modelo.Transacao;
import com.daemoncyber.picpayclone.modelo.Usuario;

public interface IUsuarioService {

	Usuario consultarEntidade(String login);
	
	UsuarioDTO consultar(String login);

	void atualizarSaldo(Transacao transacaoSalva, Boolean isCartaoCredito);

	void validar(Usuario... usuarios);

	List<UsuarioDTO> listar(String login);

}
