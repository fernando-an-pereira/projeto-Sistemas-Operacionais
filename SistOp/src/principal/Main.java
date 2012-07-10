package principal;

import informacao.GerenciadorArquivo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
		
//		String str ="1(2,3,4,5(6,7(8,9)));";
//		StringTokenizer parser = new StringTokenizer(str);
//		
//		String s = str;
//		
//		while(!s.equals(";")) {
//			int open = s.indexOf("(");
//			int close = s.indexOf(")");
//			int coma = s.indexOf(",");
//			
//			String[] lol;
//			
//			if(coma < close && coma < open && coma > 0) {
//				lol = s.split(",", 2);
//			}
//			else if(open < close && open < coma && open > 0) {
//				lol = s.split("\\(", 2);
//			}
//			else {
//				lol = s.split("\\)", 2);
//			}
//			
////			System.out.println("virgula: " + coma + ", (: " + open + ", ):" + close);
//			
//			System.out.println(lol[0]);
//			
//			s = lol[1];
//		
//			System.out.println(lol[1]);
//		}
		
		
		Leitor leitor = new Leitor("jobs.txt");
		GerenciadorArquivo gerArq = new GerenciadorArquivo();
		
		int[] te = leitor.retornaTempoExecucao();
		
		gerArq.addArquivos(leitor.arquivos());
	
		// Relogio relogio = new Relogio(te[0], te[1]);
		ArrayList<Job> jobs = leitor.retornaJobs(gerArq);
		leitor.encerrar();
		
		for(Job j : jobs) {
			System.out.println(j.getId() + " " + j.getTempoDeProcessamento() + " " + j.getRequisicoesES() + " " + j.getInstanteDeChegada());
		}
		
		Scheduler scheduler = new Scheduler(jobs, gerArq);
		List<String> log = scheduler.escalonamento(te[0], te[1]);
		
		// int[] tc = leitor.retornaTempoDeChegada(jobs.size());   
		
		for(String s : log) {
			System.out.println(s);
		}
		
	}

}
