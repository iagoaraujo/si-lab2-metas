package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="meta")
public class Meta implements Comparable<Meta>{
	
	private final String PRIORIDADE_BAIXA = "baixa";
	private final String PRIORIDADE_MEDIA = "media";
	private final String PRIORIDADE_ALTA = "alta";

	@Id
	@SequenceGenerator(name = "META_SEQUENCE", sequenceName = "META_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String descricao;
	
	@Column
	private String prazo;
	
	@Column
	private boolean cumprida = false;;
	
	@Column
	private String prioridade;

	public Meta() {
	}
	
	public Meta(String nome, String descricao, String prazo, String prioridade) {
		this.nome = nome;
		this.descricao = descricao;
		this.prazo = prazo;
		this.prioridade = prioridade;
	}
	
	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public boolean isCumprida() {
		return cumprida;
	}

	public void setCumprida(boolean cumprida) {
		this.cumprida = cumprida;
	}

	@Override
	public int compareTo(Meta outraMeta) {
		if(this.prioridade.equals(PRIORIDADE_BAIXA)){
			if(!outraMeta.prioridade.equals(PRIORIDADE_BAIXA)){
				return -1;
			}
		}
		else if(this.prioridade.equals(PRIORIDADE_MEDIA)){
			if(outraMeta.prioridade.equals(PRIORIDADE_BAIXA)){
				return 1;
			}
			else if(outraMeta.prioridade.equals(PRIORIDADE_ALTA)){
				return -1;
			}
		}
		else if(this.prioridade.equals(PRIORIDADE_ALTA)){
			if(!outraMeta.prioridade.equals(PRIORIDADE_ALTA)){
				return 1;
			}
		}
		return this.nome.compareTo(outraMeta.nome);
	}
}
