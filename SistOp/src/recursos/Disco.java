package recursos;

public class Disco extends Recurso {

	private final int tempoUsoJob;
	
	public Disco(int tempoUsoJob) {
		this.tempoUsoJob = tempoUsoJob;
	}
	
	public boolean pedidoPronto(int tempoAtual) {
		return (this.getTempoRodando(tempoAtual) >= tempoUsoJob);
	}

	
//	public void solicitaES(Job job) {
		
//		if(this.estaOcupado() == false){
//			this.forcaOcupado();
								// job.diminuiRequisicoes();
								// mas e o tempo de uso do DISCO???? -> atualizaTempoJobExecucao
//			if( job.getRequisicoesES() != 0){
//				 this.solicita(job);		
//			}
//			else{
				// job vai pedir CPU
//			}   
			
//		}
//		else{
//			this.solicita(job);
//		}
		
		
//	}
	
//	public void atualizaTempoJobEmExecucaoDisco(int tempoPercorrido){ // NO DISCO EH DIFERENTE! pois temos que contar quantas requisi��es faltam, se faltar mais naum libera e sim coloca na fila novamente
//		Job temp = this.getJobRodando();
//		if ( atualizaTempoJobEmExecucao(tempoPercorrido) == true){
//			if (temp.getRequisicoesES() > 1){
//				temp.diminuiRequisicoes();
//				this.solicitaES(temp);
//			}
//		}
		
		
//	}
	
	
}
