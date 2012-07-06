package recursos;

import java.util.ArrayList;
import java.util.List;

import jobs.Job;

public class Memoria extends Recurso {

	private final int tamanho;
	private final int tempoDeRelocacao;
	private int tamanhovago;
	//private final int tamanhoSegmento;
	
	// arvore de segmentos

	private ArrayList<Job> jobsRodando = new ArrayList<Job>();
	
	public Memoria(int tamanho, int tempoDeRelocacao){
	//public Memoria(int tamanho, int tempoDeRelocacao, int tamanhoSegmento){
		this.tamanho = tamanho;
		this.tamanhovago = tamanho;
		this.tempoDeRelocacao = tempoDeRelocacao;
		//this.tamanhoSegmento = tamanhoSegmento;
	}
	
//	public void atribui(Job job){ 
//		this.solicita(job);										// 2 solicita se mem requisitada > tamanho vago ?
//		if (job.getMemoriaRequisitada() > this.tamanhovago){
//			this.solicita(job);
//		}
//		else{
//			this.tamanhovago -= job.getMemoriaRequisitada();
//			// job pede CPU
//		}
//		
//	}
	
//	public void desaloca(Job job){
//		this.tamanhovago += job.getMemoriaRequisitada();
//		this.libera(job);
//	}
	
	public List<Job> getJobsRodando() {
		return this.jobsRodando;
	}
	
	public boolean memoriaAlocada(int tempoDoRelogio){
		if(ocupado) {
			ocupado = false;
			return (this.getTempoRodando(tempoDoRelogio) >=  tempoDeRelocacao);
		}
		return false;
	}
	
	public int getTamanho() {
		return this.tamanho;
	}
	
	public int getTempoDeRelocacao() {
		return this.tempoDeRelocacao;
	}
	
	@Override
	public boolean solicita(Job job, int instante) {
		if(job.getMemoriaRequisitada() > this.tamanhovago) {
			jobs.add(job);
			return false;
		}
		
		jobRodando = job;
		
		ocupado = true;
		
		instanteInicial = instante;
		
		this.tamanhovago -= job.getMemoriaRequisitada();
		
		jobsRodando.add(job);
		
		return true;
	}
	
	@Override
	public Job libera(Job job, int instante) {
		
		if(!jobsRodando.contains(job)) {
			jobs.remove(job);
			return null;
		}
		
		this.tamanhovago += job.getMemoriaRequisitada();

		Job j = null;
		
		for(Job jb : jobs) {
			if(jb.getMemoriaRequisitada() <= this.tamanhovago){
				j = jb;
				break;
			}
		}
		
		if(j != null) {
			jobsRodando.add(j);
			this.tamanhovago -= j.getMemoriaRequisitada();
			this.instanteInicial = instante;
		}
		
		return j;
	}
	

}
