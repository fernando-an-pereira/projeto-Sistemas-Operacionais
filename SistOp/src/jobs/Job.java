package jobs;

public class Job {
	
	private int tempoDeProcessamento;
	private int memoriaRequisitada;
	private int requisicoesES;
	
	public Job(int tempoDeProcessamento, int memoriaRequisitada, int requisicoesES) {
		this.tempoDeProcessamento = tempoDeProcessamento;
		this.memoriaRequisitada = memoriaRequisitada;
		this.requisicoesES = requisicoesES;	
	}

	public int getTempoDeProcessamento() {
		return tempoDeProcessamento;
	}

//	public void setTempoDeProcessamento(int tempoDeProcessamento) {
//		this.tempoDeProcessamento = tempoDeProcessamento;
//	}

	public int getMemoriaRequisitada() {
		return memoriaRequisitada;
	}

//	public void setMemoriaRequisitada(int memoriaRequisitada) {
//		this.memoriaRequisitada = memoriaRequisitada;
//	}

	public int getRequisicoesES() {
		return requisicoesES;
	}

//	public void setRequisicoesES(int requisicoesES) {
//		this.requisicoesES = requisicoesES;
//	}
	
	
	
}
