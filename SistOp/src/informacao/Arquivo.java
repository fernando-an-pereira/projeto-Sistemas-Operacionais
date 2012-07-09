package informacao;

import jobs.Job;

public class Arquivo {
	private boolean sendoEscrito;
	private int tamanho;
	private String nome;
	private boolean publico;
	private Job possuidor;
	
	public Arquivo(String nome, int tamanho){
		this.nome = nome;
		this.tamanho = tamanho;
		this.sendoEscrito = false;
	}
	
	public boolean livre(){
		return !this.sendoEscrito;
	}
	
	public boolean  possuiPermissao (Job job) {
		return (publico || job == possuidor);
	}
	
}
