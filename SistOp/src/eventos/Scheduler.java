package eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import jobs.Job;

import tempo.Relogio;

import recursos.*;


public class Scheduler {
	
	private final static int INTERVALO = 5;
	
	private List<Job> jobs;

	private List<Evento> eventos;
	
	public Scheduler(ArrayList<Job> listaProcessos){
		
		this.jobs = listaProcessos;
		
		this.eventos = new ArrayList<Evento>();
	}
	
	public void escalonamento(int tempoInicio, int tempoFim){
		
		Relogio relogio = new Relogio(tempoInicio, tempoFim);
		Memoria memoria = new Memoria(80);
		Disco disco = new Disco(50);
		CPU cpu = new CPU(10);
		List<Job> jobsRodando;
		
		while(!jobs.isEmpty() && !relogio.avanca(INTERVALO)) {
			
			Evento e = null;
			
			Job j = verificaChegadaJob(relogio.getTempo());
			
			if(j != null) {
				
				eventos.add(e);
			}
			
			if(e != null) {
				
			}
			
			
		}
		
		
		
//		while (listaProcessos.isEmpty() == false | relogio.avanca(INTERVALO) == true ){
//			while( listaProcessos.get(0).getInstanteDeChegada() <= relogio.getTempo() ){
//				Job processo = listaProcessos.remove(0);                // espero q quando remova o "j" o q era j+1 vire J!!!
//				memoria.atribui(processo);
				
				//falta o resto => disco e cpu
				
//			}

//			relogio.avanca(INTERVALO);
//			memoria.atualizaTempoJobEmExecucao(INTERVALO);
//			disco.atualizaTempoJobEmExecucaoDisco(INTERVALO);
//			cpu.atualizaTempoJobEmExecucao(INTERVALO);
			
//		}
		//FINALIZA TUDO!!!!!!!!!!!!!!!!!!
			
	}
	
	private Job verificaChegadaJob(int time) {
		for(Job j : jobs) {
			if (j.getInstanteDeChegada() == time)
				return j;
		}
		return null;
	}
			
			
}
