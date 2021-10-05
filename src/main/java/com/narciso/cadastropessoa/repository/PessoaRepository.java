package com.narciso.cadastropessoa.repository;

import com.narciso.cadastropessoa.domain.Pessoa;
import com.narciso.cadastropessoa.dto.PessoaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PessoaRepository {
    PessoaDto salvar(Pessoa Pessoa);
    PessoaDto alterar(Pessoa Pessoa);
    void deletar(Long id);
    PessoaDto buscarPorId(Long id);
    Page<Pessoa> listar(String query, String filtro, Pageable pageable);
}
