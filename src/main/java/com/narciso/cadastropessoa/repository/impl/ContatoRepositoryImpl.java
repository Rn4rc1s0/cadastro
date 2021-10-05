package com.narciso.cadastropessoa.repository.impl;

import com.narciso.cadastropessoa.domain.Contato;
import com.narciso.cadastropessoa.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
@PropertySource("classpath:query/contato.properties")
public class ContatoRepositoryImpl implements ContatoRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SPS.CONTATO.WHERE.ID_PESSOA}")
    private String queryBuscarPorIdPessoa;

    @Value("${SPI.CONTATO.SALVAR}")
    private String querySalvar;

    @Value("${SPU.CONTATO.ALTERAR}")
    private String queryAlterar;

    @Value("${SPD.CONTATO.DELETAR}")
    private String queryDeletar;

    @Override
    public List<Contato> buscarLsContatoPorIdPessoa(Long idPessoa) {
        return jdbcTemplate.query(queryBuscarPorIdPessoa, BeanPropertyRowMapper.newInstance(Contato.class), idPessoa);
    }

    @Override
    public List<Contato> salvarTodos(List<Contato> lsContato) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(lsContato.toArray());
        namedParameterJdbcTemplate.batchUpdate(querySalvar, batch);
        return lsContato;
    }

    @Override
    public List<Contato> alterarTodos(List<Contato> lsContato) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(lsContato.toArray());
        namedParameterJdbcTemplate.batchUpdate(queryAlterar, batch);
        return lsContato;
    }

    @Override
    public void deletarPorIdPessoa(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(queryDeletar, parameterSource);
    }
}
