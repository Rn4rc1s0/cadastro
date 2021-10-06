package com.narciso.cadastropessoa;

import com.narciso.cadastropessoa.domain.Contato;
import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.filtro.PessoaQueryEnum;
import com.narciso.cadastropessoa.form.PessoaForm;
import com.narciso.cadastropessoa.repository.ContatoRepository;
import com.narciso.cadastropessoa.repository.PessoaRepository;
import com.narciso.cadastropessoa.service.PessoaService;
import com.narciso.cadastropessoa.service.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PessoaServiceImplTest {

    @InjectMocks
    private PessoaService pessoaService = new PessoaServiceImpl();

    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private ContatoRepository contatoRepository;

    private Pageable page = PageRequest.of(1, 10);
    private PessoaDto pessoa;
    private Contato contato;
    private List<PessoaDto> lsPessoa = new ArrayList<>();
    private List<Contato> lsContato = new ArrayList<>();
    private PessoaForm pessoaForm;
    private Page<PessoaDto> pgPessoa;

    @Before
    public void setup() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

        Date data = formato.parse("01-11-1980");
        contato = Contato.builder()
                .id(1L)
                .idPessoa(1L)
                .nmContato("Nome Contato")
                .nrDdd(21)
                .nmTelefone("999998888")
                .nmEmail("contato@contato.com")
                .build();

        lsContato.add(contato);

        pessoa = PessoaDto.builder()
                .id(1L)
                .nmPessoa("Pessoa Teste")
                .nrCpf("23615639081")
                .dtNascimento(data)
                .lsContato(lsContato)
                .build();

        lsPessoa.add(pessoa);

        pessoaForm = PessoaForm.builder()
                .id(1L)
                .nmPessoa("Pessoa Teste")
                .nrCpf("23615639081")
                .dtNascimento(formato.parse("01-11-1980"))
                .lsContato(lsContato)
                .build();

        pgPessoa = new PageImpl<PessoaDto>(lsPessoa, page, lsPessoa.size());
    }

    @Test
    public void deveSalvarPessoa() throws Exception {
        when(pessoaRepository.salvar(Mockito.any())).thenReturn(pessoa);
        when(contatoRepository.salvarTodos(Mockito.anyList())).thenReturn(lsContato);
        pessoaService.salvar(pessoaForm);
        verify(pessoaRepository, Mockito.times(1)).salvar(Mockito.any());
    }
    @Test
    public void deveAlterarPessoa() throws Exception {
        when(pessoaRepository.alterar(Mockito.any())).thenReturn(pessoa);
        doNothing().when(contatoRepository).deletarPorIdPessoa(Mockito.anyLong());
        when(contatoRepository.salvarTodos(Mockito.anyList())).thenReturn(lsContato);
        pessoaService.alterar(pessoaForm);
        verify(pessoaRepository, Mockito.times(1)).alterar(Mockito.any());
    }
    @Test
    public void deveDeletarPessoa() throws Exception {
        doNothing().when(contatoRepository).deletarPorIdPessoa(Mockito.anyLong());
        doNothing().when(pessoaRepository).deletar(Mockito.anyLong());
        pessoaService.deletar(1L);
        verify(contatoRepository, Mockito.times(1)).deletarPorIdPessoa(Mockito.anyLong());
        verify(pessoaRepository, Mockito.times(1)).deletar(Mockito.anyLong());
    }
    @Test
    public void deveBuscarPessoaPorId() throws Exception {
        when(pessoaRepository.buscarPorId(Mockito.anyLong())).thenReturn(pessoa);
        when(contatoRepository.buscarLsContatoPorIdPessoa(Mockito.anyLong())).thenReturn(lsContato);
        pessoaService.buscarPorId(1L);
        verify(pessoaRepository, Mockito.times(1)).buscarPorId(Mockito.anyLong());
    }
    @Test
    public void deveListarPessoa() throws Exception {
        String queryProperty = PessoaQueryEnum.QUERY_WHERE_NM_PESSOA.getQueryProperty(page);
        String filtro =  "%Pessoa%";
        when(pessoaRepository.listar(queryProperty, filtro, page)).thenReturn(pgPessoa);
        when(contatoRepository.buscarLsContatoPorLsIdsPessoa(Mockito.anyList())).thenReturn(lsContato);
        pessoaService.listar(queryProperty, "Pessoa", page);
        verify(pessoaRepository, Mockito.times(1)).listar(queryProperty, filtro, page);
    }

}
