package com.narciso.cadastropessoa.filtro;

import com.narciso.cadastropessoa.utils.StringUtils;
import com.narciso.cadastropessoa.utils.UtilsConstants;
import org.springframework.http.HttpStatus;


public enum CadastroHttpEnum {

	HTTP_200(HttpStatus.OK, UtilsConstants.RESPONSE_200),
	HTTP_201(HttpStatus.CREATED, UtilsConstants.RESPONSE_201),
	HTTP_204(HttpStatus.OK, UtilsConstants.RESPONSE_204),

	HTTP_304(HttpStatus.NOT_MODIFIED, UtilsConstants.RESPONSE_304),

	HTTP_400(HttpStatus.BAD_REQUEST, UtilsConstants.RESPONSE_400),
	HTTP_401(HttpStatus.UNAUTHORIZED, UtilsConstants.RESPONSE_401),
	HTTP_404(HttpStatus.NOT_FOUND, UtilsConstants.RESPONSE_404),
	HTTP_500(HttpStatus.INTERNAL_SERVER_ERROR, UtilsConstants.RESPONSE_500);

	private HttpStatus status;
	private String mensagem;

	private CadastroHttpEnum(final HttpStatus status, final String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public static CadastroHttpEnum fromValue(final HttpStatus status) {

		for (final CadastroHttpEnum httpEnum : values()) {
			if (httpEnum.getStatus().equals(status)) {
				return httpEnum;
			}
		}

		throw new IllegalArgumentException(
				StringUtils.getMensagemPadrao("Código Invalido", status.value()));
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public boolean is2xxSuccessful() {
		return status.is2xxSuccessful();
	}

	public boolean is4xxClientError() {
		return status.is4xxClientError();
	}

	public boolean is5xxServerError() {
		return status.is5xxServerError();
	}
}