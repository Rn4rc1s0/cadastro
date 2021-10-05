package com.narciso.cadastropessoa.filtro;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

@AllArgsConstructor
public enum PessoaQueryEnum {
    QUERY_SEM_WHERE("SPS.PESSOA.ORDER_BY."),
    QUERY_WHERE_ID_PESSOA("SPS.PESSOA.WHERE.ID_PESSOA.ORDER_BY."),
    QUERY_WHERE_NM_PESSOA("SPS.PESSOA.WHERE.NM_PESSOA.ORDER_BY."),
    QUERY_WHERE_NR_CPF("SPS.PESSOA.WHERE.NR_CPF.ORDER_BY."),
    QUERY_TODOS("SPS.PESSOA.WHERE.TODOS.ORDER_BY.");

    private String queryProperty;

    public String getQueryProperty(Pageable pageable) {
        return queryProperty.concat(getSortColumn(pageable));
    }

    private String getSortColumn(Pageable pageable) {
        Sort.Order order = pageable.getSort().get().findFirst().orElse(Sort.Order.asc("id"));
        return PessoaSortEnum.buscaPorCampo(order.getProperty()).concat(".").concat(order.getDirection().name());
    }

    @AllArgsConstructor
    private enum PessoaSortEnum{
        NM_PESSOA("nmPessoa"),
        NR_CPF("nrCpf"),
        ID_PESSOA("id");

        private String campo;

        public static String buscaPorCampo(String busca){
            return Stream.of(PessoaSortEnum.values()).filter(pessoaSortEnum ->
                            pessoaSortEnum.campo.equals(busca))
                    .findFirst()
                    .orElse(PessoaSortEnum.ID_PESSOA).toString();
        }
    }
}
