package recursos;

public class Segmento {
	
	private int tamanho;
	private boolean ocupado;
	
	public Segmento (int tamanho) {
		this.setTamanho(tamanho);
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	
	public boolean estaOcupado() {
		return ocupado;
	}
	
	public void ocupa() {
		this.ocupado = true;
	}
	
	public void libera() {
		this.ocupado = false;
	}
	
	public Segmento quebra(int tamanho) {
		if(tamanho > this.tamanho)
			return null;
		
		this.tamanho -= tamanho;
		return new Segmento(tamanho);
		
	}
	
}
