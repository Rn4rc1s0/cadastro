package com.narciso.cadastropessoa.resource.read;

import com.narciso.cadastropessoa.annotation.ApiIgnore;
import com.narciso.cadastropessoa.domain.CadastroResource;
import com.narciso.cadastropessoa.domain.CadastroResponse;
import com.narciso.cadastropessoa.domain.Pessoa;
import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.filtro.PessoaQueryEnum;
import com.narciso.cadastropessoa.service.PessoaService;
import com.narciso.cadastropessoa.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cadastro")
public class PessoaReadResource implements CadastroResource {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<CadastroResponse<PessoaDto>> buscarPorId(@PathVariable Long id) {
        return retornarSucesso(pessoaService.buscarPorId(id));
    }

    @GetMapping(params = "id")
    public ResponseEntity<CadastroResponse<Page<PessoaDto>>> listarPorId(@RequestParam(value = "id", required = false) String id,
                                                       @ApiIgnore Pageable pageable) {
        String queryProperty = PessoaQueryEnum.QUERY_WHERE_ID_PESSOA.getQueryProperty(pageable);
        return getResponseEntity(pageable, queryProperty, id);
    }

    @GetMapping(params = "nmPessoa")
    public ResponseEntity<CadastroResponse<Page<PessoaDto>>> listarPorNmPessoa(@RequestParam(required = false) String nmPessoa,
                                                                  @ApiIgnore Pageable pageable) {
        String queryProperty = PessoaQueryEnum.QUERY_WHERE_NM_PESSOA.getQueryProperty(pageable);
        return getResponseEntity(pageable, queryProperty, nmPessoa);
    }

    @GetMapping(params = "nrCpf")
    public ResponseEntity<CadastroResponse<Page<PessoaDto>>> listarPorNrCpf(@RequestParam(required = false) String nrCpf,
                                                                  @ApiIgnore Pageable pageable) {
        String queryProperty = PessoaQueryEnum.QUERY_WHERE_NR_CPF.getQueryProperty(pageable);
        return getResponseEntity(pageable, queryProperty, nrCpf);
    }

    @GetMapping(params = {"todos", "campos"})
    public ResponseEntity<CadastroResponse<Page<PessoaDto>>> listarTodos(@RequestParam(required = false) String todos,
                                                                            @ApiIgnore Pageable pageable) {
        String queryProperty = PessoaQueryEnum.QUERY_TODOS.getQueryProperty(pageable);
        return getResponseEntity(pageable, queryProperty, todos);
    }

    @GetMapping
    public ResponseEntity<CadastroResponse<Page<PessoaDto>>> listar(@ApiIgnore Pageable pageable) {
        String queryProperty = PessoaQueryEnum.QUERY_SEM_WHERE.getQueryProperty(pageable);
        return getResponseEntity(pageable, queryProperty, "");
    }

    private ResponseEntity<CadastroResponse<Page<PessoaDto>>> getResponseEntity(Pageable pageable, String queryProperty, String filtro) {
        Page<PessoaDto> lsPessoa = pessoaService.listar(queryProperty, filtro, pageable);
        return retornarSucesso(lsPessoa);
    }
}
