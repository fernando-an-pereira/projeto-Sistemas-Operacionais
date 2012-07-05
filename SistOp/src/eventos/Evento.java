package eventos;

public class Evento implements Comparable<Evento> {
	
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
	
	@Override
	public int compareTo(Evento e) {
		return ((Integer)tempo).compareTo((Integer)e.getTempo());
	}
	
}
