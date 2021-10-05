package com.narciso.cadastropessoa.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.SneakyThrows;

public class AbstractDto<T, D> {
	private static final long serialVersionUID = -5004021945307872992L;

	@SuppressWarnings("unchecked")
	@SneakyThrows
	public D converter(T entidade, Function<T, D> construtor) {
		if (Objects.nonNull(entidade)) 
			return construtor.apply(entidade);
		
		return (D) getClass().newInstance();
	}
	
	public Page<D> converter(Page<T> lsEntidade, Function<T, D> construtor) {
		if (Objects.nonNull(lsEntidade)) {
			return lsEntidade.map(construtor);	
		}
		return Page.empty();
		
	}
	
	public List<D> converter(List<T> lsEntidade, Function<T, D> construtor) {
		if (Objects.nonNull(lsEntidade)) {
			return lsEntidade.stream().map(construtor).collect(Collectors.toList());	
		}
		return new ArrayList<>();
	}
}
