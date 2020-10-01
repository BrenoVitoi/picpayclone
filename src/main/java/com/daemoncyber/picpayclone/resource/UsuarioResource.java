package com.daemoncyber.picpayclone.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daemoncyber.picpayclone.dto.UsuarioDTO;
import com.daemoncyber.picpayclone.resource.swagger.IUsuarioResource;
import com.daemoncyber.picpayclone.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource extends ResourceBase<UsuarioDTO> implements IUsuarioResource {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/{login}/saldo")
	public ResponseEntity<UsuarioDTO> consultarSaldo(@PageableDefault(page = 0, size = 20) Pageable paginacao,
			@PathVariable String login) {
		UsuarioDTO usuarioDTO = usuarioService.consultar(login);
		return responderSucessoComItem(usuarioDTO);
	}
	
	@GetMapping("/contatos")
	@Override
	public ResponseEntity<List<UsuarioDTO>> listar(@RequestParam String login) {
		List<UsuarioDTO> usuarios = usuarioService.listar(login);
		return responderListaDeItens(usuarios);
	}
	
	@GetMapping("/{login}")
	public ResponseEntity<UsuarioDTO> consultar(@PathVariable String login) {
		UsuarioDTO usuario = usuarioService.consultar(login);
		return responderSucessoComItem(usuario);
	}

}
