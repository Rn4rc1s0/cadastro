package com.narciso.cadastropessoa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.narciso.cadastropessoa.domain.Contato;
import com.narciso.cadastropessoa.domain.Pessoa;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class PessoaDto extends AbstractDto<Pessoa, PessoaDto> {
    private static final long serialVersionUID = 6807226306814555285L;

    private Long id;
    private String nmPessoa;
    private String nrCpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dtNascimento;
    private List<Contato> lsContato;

    public PessoaDto(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nmPessoa = pessoa.getNmPessoa();
        this.nrCpf = pessoa.getNrCpf();
        this.dtNascimento = pessoa.getDtNascimento();
    }
}
