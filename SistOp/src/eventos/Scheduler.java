package eventos;

import java.util.ArrayList;
import java.util.List;

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
		Memoria memoria = new Memoria(80, 10);
		Disco disco = new Disco(500);
		CPU cpu = new CPU(10);
		List<Job> jobsRodando = jobs;
		
		while(!jobs.isEmpty() && !relogio.avanca(INTERVALO)) {
			
			if(verificaChegadaJob(relogio.getTempo()) != null) {
				Job j = verificaChegadaJob(relogio.getTempo());
				Evento e = new Evento(relogio.getTempo(), TipoEvento.CHEGADA, j);
				eventos.add(e);
				if(memoria.solicita(j, relogio.getTempo())) {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, j);
					eventos.add(e);
				}
				else {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, j);
					eventos.add(e);
				}
			}
			
			//memoria alocada
			if(memoria.memoriaAlocada(relogio.getTempo())) {
				
				
				
				if(cpu.solicita(job, relogio.getTempo())) {
					Evento e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, job);
					eventos.add(e);
				}
				else {
					Evento e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, job);
					eventos.add(e);
				}
			}
			
			// pedido E/S
			if(cpu.getJobRodando().getRequisicoesES() > 0) {
				Job j = cpu.getJobRodando();
				Evento e = new Evento(relogio.getTempo(), TipoEvento.PEDIDO_E_S, j);
				eventos.add(e);
				Job jobProcessador = cpu.libera(j, relogio.getTempo());
				if (jobProcessador != null) {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, jobProcessador);
					eventos.add(e);
				}
				
			}
			
			//requisição E/S
			if(disco.pedidoPronto(relogio.getTempo())) {
				Job job = disco.getJobRodando();
				Evento e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_E_S, job);
				eventos.add(e);
				if(cpu.solicita(job, relogio.getTempo())) {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, job);
					eventos.add(e);
				}
				else {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, job);
					eventos.add(e);
				}
				disco.libera(job, relogio.getTempo());
			}
			
			//
			
			//libera
			if(cpu.tempoFinalizado(relogio.getTempo())) {
				Job job = cpu.getJobRodando();
				Evento e = new Evento(relogio.getTempo(), TipoEvento.TERMINO, job);
				eventos.add(e);
				Job j1 = cpu.libera(job, relogio.getTempo());
				if(j1 != null) {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j1);
					eventos.add(e);
				}
				Job j2 = memoria.libera(job, relogio.getTempo());
				if(j2 != null) {
					e = new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, j2);
					eventos.add(e);
				}
				jobsRodando.remove(job);
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
