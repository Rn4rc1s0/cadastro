package com.narciso.cadastropessoa.utils;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.narciso.cadastropessoa.dto.AbstractDto;
import org.springframework.data.domain.Page;

import lombok.SneakyThrows;

public class Utils {
	
	private Utils() {
		super();
	}


	public static final String BIG_DECIMAL_REGEX = "\\d+(\\.\\d{1,2})?";
	
	public static final String NUMBER_REGEX = "^\\d+$";
	
	public static final String LOCAL_DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
	
	public static final String ANYTHING_REGEX = "(?s).*";
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public static <D extends AbstractDto<?, ?>, T> D converter(T entidade, Function<T, D> construtor) {
		if (Objects.nonNull(entidade)) 
			return construtor.apply(entidade);
		
		return (D) AbstractDto.class.newInstance();
	}
	
	public static <T> T setDefaultValue(T value, T value2){
		if(Objects.nonNull(value)) {
		return value;
		}
		return value2;
	}
	
	public static String formatarMoeda(BigDecimal valor) {
		DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator(',');
		decimalSymbols.setGroupingSeparator('.');
		return new java.text.DecimalFormat("#,###,##0.00", decimalSymbols).format(valor);
	}

	
	@SneakyThrows
	public static <D extends AbstractDto<?, ?>, T> D converter(T entidade, Function<T, D> construtor, Class<D> clazz) {
		if (Objects.nonNull(entidade)) 
			return construtor.apply(entidade);

		return clazz.newInstance();
	}
	
	public static <D extends AbstractDto<?, ?>, T> Page<D> converter(Page<T> lsEntidade, Function<T, D> construtor) {
		if (Objects.nonNull(lsEntidade)) {
			return lsEntidade.map(construtor);	
		}
		return Page.empty();
	}
	
	public static <D extends AbstractDto<?, ?>, T> List<D> converter(List<T> lsEntidade, Function<T, D> construtor) {
		if (Objects.nonNull(lsEntidade)) {
			return lsEntidade.stream().map(construtor).collect(Collectors.toList());	
		}
		return new ArrayList<>();
	}
	
	public static String removerAcentosCaracteres(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{Alnum}@\\.\\s,-]", "");
	}

	public static String completaComZeros(int tamanho, String valor) {
		StringBuilder builder = new StringBuilder(Utils.removerAcentosCaracteres(valor));
		while (builder.length() < tamanho)
			builder.insert(0, "0");

		return builder.toString();
	}

	public static String completaComEspacos(int tamanho, String valor) {
		StringBuilder builder = new StringBuilder(Utils.removerAcentosCaracteres(valor));
		while (builder.length() < tamanho)
			builder.append(" ");

		return builder.toString();
	}
	
	public static <T, D> List<D> mapList(List<T> lista, Function<T, D> function) {
		return lista.stream().map(function).collect(Collectors.toList());
	}

	public static <T, D> Set<D> mapSet(List<T> lista, Function<T, D> function) {
		return lista.stream().map(function).collect(Collectors.toSet());
	}
	
	public static <T, D> Map<D, T> listToMap(List<T> lista, Function<T,D> keyMapper, UnaryOperator<T> keyValue){
		if(lista.isEmpty() && Objects.isNull(keyMapper))
			return Collections.emptyMap();
		return lista.stream().collect(Collectors.toMap(keyMapper, keyValue));
	}
	
	public static <T> List<T> filterList(List<T> lista, Predicate<T> predicado) {
		return lista.stream().filter(predicado).collect(Collectors.toList());
	}
	
	public static <T, D> List<D> filterList(List<T> lista, Predicate<T> predicado, Function<T,D> construtor) {
		return lista.stream().filter(predicado).map(construtor).collect(Collectors.toList());
	}
}
