package com.narciso.cadastropessoa.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.narciso.cadastropessoa.domain.Contato;
import com.narciso.cadastropessoa.domain.Pessoa;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaForm {

    private Long id;
    @NotBlank(message="Campo de preenchimento obrigatório.")
    private String nmPessoa;
    @NotBlank(message="Campo de preenchimento obrigatório.")
    private String nrCpf;
    @NotNull(message="Campo de preenchimento obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dtNascimento;
    private List<Contato> lsContato;

    public Pessoa converter() {
        return Pessoa.builder()
                .id(id)
                .nmPessoa(nmPessoa)
                .nrCpf(nrCpf)
                .dtNascimento(dtNascimento)
                .build();
    }
}
