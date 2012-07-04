package eventos;

public class Evento {
	
	private int tempo;
	private TipoEvento tipo;
	
	public Evento(int tempo, TipoEvento tipo) {
		this.tempo = tempo;
		this.tipo = tipo;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public TipoEvento getTipo() {
		return tipo;
	}
	
}
