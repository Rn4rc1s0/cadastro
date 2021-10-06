package com.narciso.cadastropessoa.service.impl;

import com.narciso.cadastropessoa.domain.Contato;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Validacoes.validaData(pessoa.getDtNascimento());
        PessoaDto pessoaDto = pessoaRepository.salvar(pessoa);
        pessoaForm.getLsContato().forEach(contato -> contato.setIdPessoa(pessoaDto.getId()));
        pessoaDto.setLsContato(contatoRepository.salvarTodos(pessoaForm.getLsContato()));
        return pessoaDto;
    }

    @Override
    public PessoaDto alterar(PessoaForm pessoaForm) {
        Pessoa pessoa = pessoaForm.converter();
        Validacoes.validaCPF(pessoa.getNrCpf());
        Validacoes.validaData(pessoa.getDtNascimento());
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
    public Page<PessoaDto> listar(String queryProps, String filtro, Pageable pageable) {
        Page<PessoaDto> pgPessoa = pessoaRepository.listar(queryProps, "%".concat(filtro).concat("%"), pageable);
        setLsContato(pgPessoa);
        return pgPessoa;
    }

    @Override
    public PessoaDto buscarPorId(Long id) {
        PessoaDto pessoa =  pessoaRepository.buscarPorId(id);
        pessoa.setLsContato(contatoRepository.buscarLsContatoPorIdPessoa(id));
        return pessoa;
    }

    public void setLsContato(Page<PessoaDto> lsPessoa) {
        List<Long> lsIds = lsPessoa.stream().map(PessoaDto::getId).collect(Collectors.toList());
        List<Contato> lsContato = contatoRepository.buscarLsContatoPorLsIdsPessoa(lsIds);
        Map<Long, List<Contato>> mapContato = new HashMap<>();

        lsContato.forEach(contato -> {
            if(!mapContato.containsKey(contato.getIdPessoa())) {
                mapContato.put(contato.getIdPessoa(), new ArrayList<>());
            }
            mapContato.get(contato.getIdPessoa()).add(contato);
        });
        lsPessoa.forEach(pessoa -> pessoa.setLsContato(mapContato.get(pessoa.getId())));
    }
}
