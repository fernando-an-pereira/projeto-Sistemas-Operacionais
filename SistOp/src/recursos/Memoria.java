package recursos;

import jobs.Job;

public class Memoria extends Recurso {

	private final int tamanho;
	private int tamanhovago;
	
	
	Memoria(int tam){
		this.tamanho = 1000;
		this.tamanhovago = tamanho;
	}
	
	public void atribui(Job job){
		this.atribui(job);
		if (job.getMemoriaRequisitada() > this.tamanhovago){
			this.atribui(job);
		}
		else{
			this.tamanhovago -= job.getMemoriaRequisitada();
		}
		
	}
	
	public void libera(Job job){
		this.tamanhovago += job.getMemoriaRequisitada();
	}

}
