package recursos;

import jobs.Job;

public class CPU extends Recurso {

	private final int timeout;
	
	public CPU(int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout(){
		return this.timeout;
	}
	
	public boolean tempoFinalizado(int tempoAtual) {
		return (this.estaOcupado() && this.getTempoRodando(tempoAtual) + this.getJobRodando().getTempoRodado() >= this.getJobRodando().getTempoDeProcessamento());
	}
	
	public Job verificaMove(int tempoAtual) {
		if(this.getTempoRodando(tempoAtual) >= timeout) {
			
			return this.getJobRodando();
		}
		
		return null;
	}
	
}
