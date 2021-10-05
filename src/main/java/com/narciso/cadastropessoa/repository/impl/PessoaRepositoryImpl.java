package com.narciso.cadastropessoa.repository.impl;

import com.narciso.cadastropessoa.domain.Pessoa;
import com.narciso.cadastropessoa.dto.PessoaDto;
import com.narciso.cadastropessoa.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@RequiredArgsConstructor
@PropertySource("classpath:query/pessoa.properties")
public class PessoaRepositoryImpl implements PessoaRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    protected Environment env;

    @Value("${SPI.PESSOA.SALVAR}")
    private String querySalvar;

    @Value("${SPU.PESSOA.ALTERAR}")
    private String queryAlterar;

    @Value("${SPD.PESSOA.DELETAR}")
    private String queryDeletar;

    @Value("${SPS.PESSOA.WHERE.ID}")
    private String queryBuscarPorId;
    
    @Override
    public PessoaDto salvar(Pessoa pessoa) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(pessoa);
        namedParameterJdbcTemplate.update(querySalvar, parameterSource, keyHolder);
        pessoa.setId(keyHolder.getKey().longValue());
        return new PessoaDto(pessoa);
    }

    @Override
    public PessoaDto alterar(Pessoa pessoa) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(pessoa);
        namedParameterJdbcTemplate.update(queryAlterar, parameterSource);
        return new PessoaDto(pessoa);
    }

    @Override
    public void deletar(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(queryDeletar, parameterSource);
    }

    @Override
    public PessoaDto buscarPorId(Long id) {
        return jdbcTemplate.queryForObject(queryBuscarPorId, BeanPropertyRowMapper.newInstance(PessoaDto.class), id);
    }

    @Override
    public Page<Pessoa> listar(String query, String filtro, Pageable pageable) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            if (Objects.nonNull(filtro) && !"".equals(filtro)) {
                mapSqlParameterSource.addValue("filtro", filtro);
            }
            mapSqlParameterSource.addValue("offset", 0);
            mapSqlParameterSource.addValue("size", Integer.MAX_VALUE);

            List<Pessoa> lsPessoaCount = namedParameterJdbcTemplate.query(env.getProperty(query), mapSqlParameterSource,
                    BeanPropertyRowMapper.newInstance(Pessoa.class));

            mapSqlParameterSource.addValue("offset", pageable.getOffset());
            mapSqlParameterSource.addValue("size", pageable.getPageSize());

            List<Pessoa> lsPessoa = namedParameterJdbcTemplate.query(env.getProperty(query), mapSqlParameterSource,
                    BeanPropertyRowMapper.newInstance(Pessoa.class));
            return new PageImpl<>(lsPessoa, pageable, lsPessoaCount.size());
    }
}
