package recursos;

import jobs.Job;

public class Disco extends Recurso {

	private final int tempoUsoJob;
	
	public Disco(int tempoUsoJob) {
		this.tempoUsoJob = tempoUsoJob;
	}

	
	public void solicitaES(Job job) {
		
		if(this.estaOcupado() == false){
			this.forcaOcupado();
			job.diminuiRequisicoes();
								// mas e o tempo de uso do DISCO????
			if( job.getRequisicoesES() != 0){
				 this.solicita(job);		
			}
			else{
				// job vai pedir CPU
			}   
			
		}
		else{
			this.solicita(job);
		}
		
		
	}
	
	
}
