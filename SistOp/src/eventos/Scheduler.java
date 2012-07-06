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
	
	private final static int INTERVALO = 1;
	
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
		
		TreeSet<Evento> treeEventos = new TreeSet<Evento>();
		
		for(Job j : jobs) {
			Evento e = new Evento(j.getInstanteDeChegada(), TipoEvento.CHEGADA, j);
			treeEventos.add(e);
		}
		
		while(!treeEventos.isEmpty() && !relogio.tempoEncerrado()) {
			Evento e = treeEventos.pollFirst();
			
			switch(e.getTipo()) {
			case INVALIDO:
				break;
				
			case CHEGADA:
				break;
				
			case REQUISICAO_MEMORIA:
				break;
				
			case REQUISICAO_PROCESSADOR:
				break;
			
			case PEDIDO_E_S:
				break;
				
			case REQUISICAO_E_S:
				break;
				
			case LIBERA_E_S:
				break;
				
			case TERMINO:
				break;
			
			}
			
			eventos.add(e);
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
