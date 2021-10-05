package com.narciso.cadastropessoa.domain;

import com.narciso.cadastropessoa.filtro.CadastroHttpEnum;

import java.time.LocalDateTime;

import static com.narciso.cadastropessoa.utils.UtilsConstants.NOME_PROJETO;

public class CadastroResponse<T> {
	private static final long serialVersionUID = 1L;

	private LocalDateTime timestamp;
	private Integer nrStatus;
	private transient T dados;
	private String nmSiglaProjeto;
	private String txMensagem;

	public CadastroResponse() {
		super();
	}

	public CadastroResponse(final CadastroHttpEnum status, final T dados) {
		super();
		this.timestamp = LocalDateTime.now();
		this.nrStatus = status.getStatus().value();
		this.txMensagem = status.getMensagem();
		this.nmSiglaProjeto = System.getenv(NOME_PROJETO);
		this.dados = dados;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getNrStatus() {
		return nrStatus;
	}

	public void setNrStatus(Integer nrStatus) {
		this.nrStatus = nrStatus;
	}

	public String getNmSiglaProjeto() {
		return nmSiglaProjeto;
	}

	public void setNmSiglaProjeto(String nmSiglaProjeto) {
		this.nmSiglaProjeto = nmSiglaProjeto;
	}

	public String getTxMensagem() {
		return txMensagem;
	}

	public void setTxMensagem(String txMensagem) {
		this.txMensagem = txMensagem;
	}

	public T getDados() {
		return dados;
	}

	public void setDados(final T dados) {
		this.dados = dados;
	}

	@Override
	public String toString() {
		return String.format("UltronResponse [timeStamp=%s, nrStatus=%s, dados=%s, nmSiglaProjeto=%s, txMensagem=%s]",
				timestamp, nrStatus, dados, nmSiglaProjeto, txMensagem);
	}
}