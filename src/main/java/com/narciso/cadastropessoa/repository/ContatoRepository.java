package com.narciso.cadastropessoa.repository;

import com.narciso.cadastropessoa.domain.Contato;

import java.util.List;

public interface ContatoRepository {
    public List<Contato> buscarLsContatoPorIdPessoa (Long idPessoa);
    public List<Contato> buscarLsContatoPorLsIdsPessoa (List<Long> LsIds);
    List<Contato> salvarTodos(List<Contato> lsContato);
    List<Contato> alterarTodos(List<Contato> lsContato);
    void deletarPorIdPessoa(Long id);
}
