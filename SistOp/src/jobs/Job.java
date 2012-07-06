package jobs;

public class Job implements Comparable<Job> {
	
	private int id;
	private int tempoDeProcessamento;
	private int memoriaRequisitada;
	private int requisicoesES;
	private int instanteDeChegada;
	private int tempoRodado;
	
	public Job(int id, int tempoDeProcessamento, int memoriaRequisitada, int requisicoesES, int instanteDeChegada) {
		this.id = id;
		this.instanteDeChegada = instanteDeChegada;
		this.tempoDeProcessamento = tempoDeProcessamento;
		this.memoriaRequisitada = memoriaRequisitada;
		this.requisicoesES = requisicoesES;
		this.tempoRodado = 0;
	}

	public int getId() {
		return id;
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
	
	public int getInstanteDeChegada(){
		return this.instanteDeChegada;
	}
	
	public void diminuiRequisicoes(){
		this.requisicoesES--;
	}

//	public void setRequisicoesES(int requisicoesES) {
//		this.requisicoesES = requisicoesES;
//	}
	
	@Override
	public int compareTo(Job j) {
		return ((Integer)this.instanteDeChegada).compareTo((Integer)j.getInstanteDeChegada());
	}
	
	public int getTempoRodado() {
		return tempoRodado;
	}
	
	public void incrementaTempoRodado(int t) {
		tempoRodado += t;
	}
	
	
}
