package com.narciso.cadastropessoa.service.impl;

import com.narciso.cadastropessoa.domain.Pessoa;
import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.form.PessoaForm;
import com.narciso.cadastropessoa.repository.ContatoRepository;
import com.narciso.cadastropessoa.repository.PessoaRepository;
import com.narciso.cadastropessoa.service.PessoaService;
import com.narciso.cadastropessoa.utils.Validacoes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContatoRepository contatoRepository;

    @Override
    public PessoaDto salvar(PessoaForm pessoaForm){
        Pessoa pessoa = pessoaForm.converter();
        Validacoes.validaCPF(pessoa.getNrCpf());
        PessoaDto pessoaDto = pessoaRepository.salvar(pessoa);
        pessoaForm.getLsContato().forEach(contato -> contato.setIdPessoa(pessoaDto.getId()));
        pessoaDto.setLsContato(contatoRepository.salvarTodos(pessoaForm.getLsContato()));
        return pessoaDto;
    }

    @Override
    public PessoaDto alterar(PessoaForm pessoaForm) {
        Pessoa pessoa = pessoaForm.converter();
        Validacoes.validaCPF(pessoa.getNrCpf());
        PessoaDto pessoaDto = pessoaRepository.alterar(pessoa);
        contatoRepository.deletarPorIdPessoa(pessoaDto.getId());
        pessoaForm.getLsContato().forEach(contato -> contato.setIdPessoa(pessoaDto.getId()));
        pessoaDto.setLsContato(contatoRepository.salvarTodos(pessoaForm.getLsContato()));
        return pessoaDto;
    }

    @Override
    public void deletar(Long id) {
        contatoRepository.deletarPorIdPessoa(id);
        pessoaRepository.deletar(id);
    }

    @Override
    public Page<Pessoa> listar(String queryProps, String filtro, Pageable pageable) {
        return pessoaRepository.listar(queryProps, "%".concat(filtro).concat("%"), pageable);
    }

    @Override
    public PessoaDto buscarPorId(Long id) {
        PessoaDto pessoa =  pessoaRepository.buscarPorId(id);
        pessoa.setLsContato(contatoRepository.buscarLsContatoPorIdPessoa(id));
        return pessoa;
    }
}
