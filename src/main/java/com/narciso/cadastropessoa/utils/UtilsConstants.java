package com.narciso.cadastropessoa.utils;


import static com.narciso.cadastropessoa.utils.StringUtils.getMensagemPadrao;

public class UtilsConstants {

	private UtilsConstants() {
		super();
	}

	/*
	 * Mensagens padrões de Response de acordo com o código HTTP retornado
	 */
	public static final String RESPONSE_200 = getMensagemPadrao("response.code200");
	public static final String RESPONSE_201 = getMensagemPadrao("response.code201");
	public static final String RESPONSE_204 = getMensagemPadrao("response.code204");

	public static final String RESPONSE_304 = getMensagemPadrao("response.code304");

	public static final String RESPONSE_400 = getMensagemPadrao("response.code400");
	public static final String RESPONSE_401 = getMensagemPadrao("response.code401");
	public static final String RESPONSE_404 = getMensagemPadrao("response.code404");

	public static final String RESPONSE_500 = getMensagemPadrao("response.code500");

	public static final String EXCECAO_ARGUMENTO_INVALIDO_MSG = "Erro de parâmetro(s) inválido(s).";
	public static final String EXCECAO_ARGUMENTO_INVALIDO_DTL = "%s; Campo = '%s'; Valor = '%s'.";

	public static final String LISTA_VAZIA_MSG = "A lista está vazia.";
	public static final String ENTIDADE_NAO_ENCONTRADA_MSG = "Não foi encontrada nenhuma entidade.";

	public static final String ID_TRANSACAO = "nmIdTransacao";
	public static final String SPAN_ID = "X-B3-SpanId";
	public static final String TOKEN = "nmIdSessao";
	public static final String AUTHORIZATION = "Authorization";
	public static final String USUARIO = "userName";

	public static final String NOME_PROJETO = "NOME_PROJETO";
}
