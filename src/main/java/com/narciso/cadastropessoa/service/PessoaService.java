package com.narciso.cadastropessoa.service;

import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.form.PessoaForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaService {

    public PessoaDto salvar(PessoaForm pessoaForm);
    public PessoaDto alterar(PessoaForm pessoaForm);
    public void deletar(Long id);
    public Page<PessoaDto> listar(String queryProps, String filtro, Pageable pageable);
    public PessoaDto buscarPorId(Long id);
}
