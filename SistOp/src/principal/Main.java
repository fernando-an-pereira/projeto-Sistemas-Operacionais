package principal;

import java.util.ArrayList;

import tempo.Relogio;

import jobs.Job;

import leitor.Leitor;

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
		int[] tc = leitor.retornaTempoDeChegada(jobs.size());
		leitor.encerrar();
		
		for(int i : te)
			System.out.println(i);
		
		for(Job j : jobs) 
			System.out.println(j.getTempoDeProcessamento() + " " + j.getMemoriaRequisitada() + " " + j.getRequisicoesES());
		
		
		for(int t : tc)
			System.out.println(t);
		
		
		
		
	}

}
