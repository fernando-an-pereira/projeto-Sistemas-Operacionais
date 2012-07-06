package eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import jobs.Job;

import tempo.Relogio;

import recursos.*;


public class Scheduler {
	
	private final static int INTERVALO = 1;
	
	private Queue<Job> listaProcessos;

	private Queue<Evento> eventos;
	
	public Scheduler(ArrayList <Job> lista){
		
		Collections.sort(lista);
		
		this.listaProcessos = new LinkedList<Job>(lista);
		
		this.eventos = new LinkedList<Evento>();
	}
	
	public void escalonamento(int tempoInicio, int tempoFim){
		
		Relogio relogio = new Relogio(tempoInicio, tempoFim);
		Memoria memoria = new Memoria(80);
		Disco disco = new Disco(50);
		CPU cpu = new CPU(10);
		
		Evento e = eventos.poll(); 
		
		while(e != null) {
			
			e = eventos.poll();
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
			
			
}
