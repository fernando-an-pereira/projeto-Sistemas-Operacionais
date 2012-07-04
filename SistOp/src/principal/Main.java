package principal;

import java.util.ArrayList;

import tempo.Relogio;
import jobs.Job;
import leitor.Leitor;
import eventos.Scheduler;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Leitor leitor = new Leitor("jobs.txt");
		
		int[] te = leitor.retornaTempoExecucao();
		Relogio relogio = new Relogio(te[0], te[1]);
		ArrayList<Job> jobs = leitor.retornaJobs();
		Scheduler scheduler = new Scheduler(jobs);
		scheduler.escalonamento();
		
		int[] tc = leitor.retornaTempoDeChegada(jobs.size());
		leitor.encerrar();
		
		
	}

}
