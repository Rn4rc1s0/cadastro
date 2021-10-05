package com.narciso.cadastropessoa.domain;

import com.narciso.cadastropessoa.filtro.CadastroHttpEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.narciso.cadastropessoa.filtro.CadastroHttpEnum.HTTP_200;
public interface CadastroResource {

	/**
	 * <p>
	 * Este método será acessado por todas as classes de resource para retornar
	 * sucesso, incluindo o parâmetro "response" na generalização "dados" de
	 * CadastroResponse
	 *
	 * @param response
	 * @return
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarResponse(final CadastroHttpEnum httpEnum,
			final T response) {
		return ResponseEntity.status(httpEnum.getStatus()).body(new CadastroResponse<>(httpEnum, response));
	}

	/*
	 * HTTP 200
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarSucesso(final T response) {
		return response != null ? retornarResponse(HTTP_200, response) : retornarSemConteudo();
	}

	/*
	 * HTTP 200
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<List<T>>> retornarSucesso(final List<T> response) {
		return !response.isEmpty() ? retornarResponse(HTTP_200, response) : retornarSemConteudo();
	}

	/**
	 * <p>
	 * Através de um {@link Optional} verifica se há um retorno para a chamada, em
	 * caso positivo, retorna http 200 com o recurso retornado, caso contrário,
	 * retorna http 204 com uma mensagem padrão de recurso não encontrado.
	 *
	 * @param responseOpt
	 * @return
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarSucesso(final Optional<T> responseOpt) {
		return responseOpt.isPresent() ? retornarResponse(HTTP_200, responseOpt.get()) : retornarSemConteudo();
	}

	/**
	 * Este método deve ser utilizado apenas pelos serviços de health.
	 *
	 * @return
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarSucesso() {
		return retornarResponse(HTTP_200, null);
	}

	/*
	 * HTTP 201
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarCriado(final T response) {
		return retornarResponse(CadastroHttpEnum.HTTP_201, response);
	}

	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarCriado() {
		return retornarResponse(CadastroHttpEnum.HTTP_201, null);
	}

	/**
	 * @deprecated - Utilizar o método {@link CadastroResource#retornarSemConteudo()}
	 * @return
	 */
	@Deprecated
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarExcluido() {
		return retornarSemConteudo();
	}

	/*
	 * HTTP 204
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarSemConteudo() {
		return retornarResponse(CadastroHttpEnum.HTTP_204, null);
	}

	/*
	 * HTTP 304
	 */
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarNaoAlterado() {
		return retornarResponse(CadastroHttpEnum.HTTP_304, null);
	}

	/**
	 * @deprecated - Utilizar o método {@link CadastroResource#retornarSemConteudo()}
	 * @return
	 */
	@Deprecated
	default <T extends Object> ResponseEntity<CadastroResponse<T>> retornarNaoEncontrado() {
		return retornarSemConteudo();
	}
}