package recursos;

import jobs.Job;

public class Memoria extends Recurso {

	private final int tamanho;
	private int tamanhovago;
	
	
	public Memoria(int tamanho){
		this.tamanho = tamanho;
		this.tamanhovago = tamanho;
	}
	
	public void atribui(Job job){
		this.solicita(job);
		if (job.getMemoriaRequisitada() > this.tamanhovago){
			this.solicita(job);
		}
		else{
			this.tamanhovago -= job.getMemoriaRequisitada();
		}
		
	}
	
	public void desaloca(Job job){
		this.tamanhovago += job.getMemoriaRequisitada();
		this.libera(job);
	}

}
