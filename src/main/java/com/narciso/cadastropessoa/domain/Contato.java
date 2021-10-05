package com.narciso.cadastropessoa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contato implements Serializable {
    private static final long serialVersionUID = -6040071027848985474L;

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long idPessoa;
    @NotNull(message="Campo de preenchimento obrigatório.")
    private Integer nrDdd;
    @NotBlank(message="Campo de preenchimento obrigatório.")
    private String nmContato;
    @NotBlank(message="Campo de preenchimento obrigatório.")
    private String nmTelefone;
    @NotBlank(message="Campo de preenchimento obrigatório.")
    @Pattern(regexp = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$")
    private String nmEmail;
}
