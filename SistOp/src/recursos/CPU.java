package recursos;

import jobs.Job;

public class CPU extends Recurso {

	private final int timeout;
	private final int quantum;
	private int instanteInterrupcao;
	private boolean interrupcao;
	
	public int getQuantum() {
		return quantum;
	}

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
	
	public boolean interrompido() {
		return interrupcao;
	}
	
	public void pedidoInterrupcao(int instante) {
		interrupcao = true;
		instanteInterrupcao = instante;
	}
	
	public void fimInterrupcao(int instante) {
		interrupcao = false;
		instanteInicial += (instante - instanteInterrupcao);
	}
	
//	public Job verificaMove(int tempoAtual) {
//		if(this.getTempoRodando(tempoAtual) >= timeout) {
//			
//			Job j = this.libera(this.getJobRodando(), tempoAtual);
//			
//			if(j.getTempoRodado() < j.getTempoDeProcessamento());
//				this.solicita(j, tempoAtual);
//			
//			return this.getJobRodando();
//		}
//		
//		return null;
//	}
	
	public Job move(int tempoAtual) {
		
		this.solicita(getJobRodando(), tempoAtual);
		
//		Job j = this.getRodando();
		libera(this.getJobRodando(), tempoAtual);
		
		return this.getJobRodando();
		
	}
	
}
