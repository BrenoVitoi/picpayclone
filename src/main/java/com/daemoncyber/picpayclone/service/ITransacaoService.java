package com.daemoncyber.picpayclone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daemoncyber.picpayclone.dto.TransacaoDTO;

public interface ITransacaoService {

	TransacaoDTO processar(TransacaoDTO transacaoDTO);

	Page<TransacaoDTO> listar(Pageable paginacao, String usuario);

}
