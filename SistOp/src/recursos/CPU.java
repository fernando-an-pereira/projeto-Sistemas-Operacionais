package recursos;

import jobs.Job;

public class CPU extends Recurso {

	private final int timeout;
	private final int quantum;
	
	public CPU(int timeout, int quantum) {
		this.timeout = timeout;
		this.quantum = quantum;
	}
	
	public int getTimeout(){
		return this.timeout;
	}
	
	public boolean tempoFinalizado(int tempoAtual) {
		return (this.estaOcupado() && this.getTempoRodando(tempoAtual) + this.getJobRodando().getTempoRodado() >= this.getJobRodando().getTempoDeProcessamento());
	}
	
	public Job verificaMove(int tempoAtual) {
		if(this.getTempoRodando(tempoAtual) >= timeout) {
			
			Job j = this.libera(this.getJobRodando(), tempoAtual);
			
			if(j.getTempoRodado() < j.getTempoDeProcessamento());
				this.solicita(j, tempoAtual);
			
			return this.getJobRodando();
		}
		
		return null;
	}
	
}
