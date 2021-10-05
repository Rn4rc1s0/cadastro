package com.narciso.cadastropessoa.resource.write;

import com.narciso.cadastropessoa.domain.CadastroResource;
import com.narciso.cadastropessoa.domain.CadastroResponse;
import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.form.PessoaForm;
import com.narciso.cadastropessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cadastro")
public class PessoaWriteResource implements CadastroResource {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<CadastroResponse<PessoaDto>> criar(@RequestBody @Valid PessoaForm pessoaForm){
        return retornarCriado(pessoaService.salvar(pessoaForm));
    }

    @PutMapping
    public  ResponseEntity<CadastroResponse<PessoaDto>> alterar(@RequestBody @Valid PessoaForm pessoaForm) {
        return retornarSucesso(pessoaService.alterar(pessoaForm));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<CadastroResponse<PessoaDto>> deletar(@PathVariable("id") Long id) {
        pessoaService.deletar(id);
        return retornarSucesso();
    }
}
