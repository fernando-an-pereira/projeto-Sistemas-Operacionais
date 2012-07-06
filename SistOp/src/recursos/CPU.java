package recursos;

public class CPU extends Recurso {

	private final int timeout;
	
	public CPU(int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout(){
		return this.timeout;
	}
	
	public boolean tempoFinalizado(int tempoAtual) {
		return (this.getTempoRodando(tempoAtual) + this.getJobRodando().getTempoRodado() >= this.getJobRodando().getTempoDeProcessamento());
	}

}
