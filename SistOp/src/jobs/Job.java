package jobs;

public class Job {
	
	private int id;
	private int tempoDeProcessamento;
	private int memoriaRequisitada;
	private int requisicoesES;
	
	public Job(int id, int tempoDeProcessamento, int memoriaRequisitada, int requisicoesES) {
		this.id = id;
		this.tempoDeProcessamento = tempoDeProcessamento;
		this.memoriaRequisitada = memoriaRequisitada;
		this.requisicoesES = requisicoesES;	
	}
	
}
