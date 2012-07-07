package informacao;

public class Arquivo {
	private boolean sendoEscrito;
	private int tamanho;
	private String nome;
	private boolean publico;
	
	public Arquivo(String nome, int tamanho){
		this.nome = nome;
		this.tamanho = tamanho;
		this.sendoEscrito = false;
	}
	
	public boolean getSendoEscrito(){
		return this.sendoEscrito;
	}
	
}
