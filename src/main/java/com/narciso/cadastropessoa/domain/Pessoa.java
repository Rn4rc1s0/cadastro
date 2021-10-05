package com.narciso.cadastropessoa.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 3332796771419128644L;

    private Long id;
    private String nmPessoa;
    private String nrCpf;
    private LocalDate dtNascimento;
}
