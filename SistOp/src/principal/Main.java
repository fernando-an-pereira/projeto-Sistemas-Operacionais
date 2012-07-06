package principal;

import java.util.ArrayList;
import java.util.List;

import tempo.Relogio;
import jobs.Job;
import leitor.Leitor;
import eventos.Evento;
import eventos.Scheduler;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Leitor leitor = new Leitor("jobs.txt");
		
		int[] te = leitor.retornaTempoExecucao();
		// Relogio relogio = new Relogio(te[0], te[1]);
		ArrayList<Job> jobs = leitor.retornaJobs();
		leitor.encerrar();
		
		for(Job j : jobs) {
			System.out.println(j.getId() + " " + j.getTempoDeProcessamento() + " " + j.getMemoriaRequisitada() + " " + j.getRequisicoesES() + " " + j.getInstanteDeChegada());
		}
		
		Scheduler scheduler = new Scheduler(jobs);
		List<Evento> eventos = scheduler.escalonamento(te[0], te[1]);
		
		// int[] tc = leitor.retornaTempoDeChegada(jobs.size());   
		
		for(Evento e : eventos) {
			System.out.println(e.getTempo() + " " + e.getJob().getId() + " " + e.getTipo());
		}
		
	}

}
