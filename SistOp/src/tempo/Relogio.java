package tempo;

public class Relogio {
	
	private int tempo;
	private int tempoFim;
	
	public Relogio(int tempoInicio, int tempoFim) {
		this.tempo = tempoInicio;
		this.tempoFim = tempoFim;
	}
	
	public boolean avanca(int intervalo) {
		tempo += intervalo;
		if(tempo >= tempoFim)
			return false;
		return true;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public boolean tempoEncerrado() {
		return (tempo >= tempoFim);
	}
	
}
