package informacao;

import jobs.Job;

public class Arquivo {
	private boolean sendoEscrito;
	private int tamanho;
	private String nome;
	private boolean publico;
	private Job possuidor;
	private int idJob;
	
	public Job getPossuidor() {
		return possuidor;
	}

	public void setPossuidor(Job possuidor) {
		this.possuidor = possuidor;
	}

	public Arquivo(String nome, int tamanho, boolean publico, int idJob){
		this.setNome(nome);
		this.setTamanho(tamanho);
		this.sendoEscrito = false;
		this.publico = publico;
		this.idJob = idJob;
	}
	
	public boolean livre(){
		return !this.sendoEscrito;
	}
	
	public boolean  possuiPermissao (Job job) {
		return (publico || job == possuidor);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
	
}
