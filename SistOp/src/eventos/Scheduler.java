package eventos;

import informacao.Arquivo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import jobs.Job;

import tempo.Relogio;

import recursos.*;


public class Scheduler {
	
	private List<Job> jobs;
	private List<Arquivo> arquivos;
	
	LinkedList<Evento> eventos = new LinkedList<Evento>(); 

//	private List<Evento> eventos;
	
	public Scheduler(ArrayList<Job> listaProcessos, ArrayList<Arquivo> arquivos){
		
		this.jobs = listaProcessos;
		this.arquivos = arquivos;
		
		for(Arquivo arq : arquivos) {
			arq.setPossuidor(jobs.get(arq.getIdJob()));
		}
		
//		this.eventos = new ArrayList<Evento>();
	}
	
	public ArrayList<String> escalonamento(int tempoInicio, int tempoFim){
		
		Relogio relogio = new Relogio(tempoInicio, tempoFim);
		Memoria memoria = new Memoria(800, 10);
		Disco disco = new Disco(500, 100);
		DispositivoES device = new DispositivoES(1000);
		CPU cpu = new CPU(10, 20);
		int overheadTime = 100; 
		Random rd = new Random();
		int tempoRodadoSlice = 0;
//		List<Job> jobsRodando = new ArrayList<Job>();
		
		ArrayList<String> log = new ArrayList<String>();
		
		for(Job j : jobs) {
			addEvento(new Evento(j.getInstanteDeChegada(),TipoEvento.CHEGADA, j));
		}
		
		Evento e = eventos.poll();
		
		relogio.setTempo(e.getTempo());
		
		while(e != null && !relogio.tempoEncerrado()) {
			
			String s = e.getTempo() + "\t\t" + e.getJob().getId() + "\t" + e.getTipo() + "\t";
			
			
			switch(e.getTipo()) {
				
				
			case INVALIDO:
				s += "\t\tEvento inválido";
				break;
				
			case CHEGADA:
				addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, e.getJob()));
				s += "\t\tJob chegou no sistema";
				break;
				
			case REQUISICAO_MEMORIA:
				if(memoria.getJobsRodando().contains(e.getJob()) || memoria.solicita(e.getJob(), relogio.getTempo())) {
					s += "Job alocado na memória";
					addEvento(new Evento(relogio.getTempo() + memoria.getTempoDeRelocacao(), TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
				}
				else {
					s += "Job esperando por liberação de memória";
				}
				break;
				
			case REQUISICAO_PROCESSADOR:
				if(cpu.getJobRodando() == e.getJob() || cpu.solicita(e.getJob(), relogio.getTempo())) {
					s+= "Job recebe processador";
					
					int proximaReES = e.getJob().proximaRequisicaoES() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					int proximoAcessoArq = e.getJob().proximoAcessoArquivo() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					int tempoTermino = e.getJob().getTempoDeProcessamento() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					
					int proximaRefSegmento = rd.nextInt(cpu.getQuantum() * 2);
					
					if(proximaReES <= proximoAcessoArq && proximaReES <= proximaRefSegmento && proximaReES < cpu.getQuantum() - tempoRodadoSlice) {
						addEvento(new Evento(relogio.getTempo() + proximaReES, TipoEvento.PEDIDO_E_S, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else if(proximoAcessoArq < proximaReES && proximoAcessoArq <= proximaRefSegmento && proximoAcessoArq < cpu.getQuantum() - tempoRodadoSlice) {
						addEvento(new Evento(relogio.getTempo() + proximoAcessoArq, TipoEvento.REQUISICAO_DISCO, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else if(proximaRefSegmento < cpu.getQuantum() - tempoRodadoSlice && proximaRefSegmento < tempoTermino) {
						addEvento(new Evento(relogio.getTempo() + proximaRefSegmento, TipoEvento.REQUISICAO_SEGMENTO, e.getJob()));
						tempoRodadoSlice = proximaRefSegmento;
					}
					else if(tempoTermino <= cpu.getQuantum() - tempoRodadoSlice) {
						addEvento(new Evento(relogio.getTempo() + tempoTermino, TipoEvento.TERMINO, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else {
						addEvento(new Evento(relogio.getTempo() + cpu.getQuantum(), TipoEvento.TIME_SLICE, e.getJob()));
						tempoRodadoSlice = 0;
					}
					
//					if(e.getJob().getRequisicoesES() > 0 && e.getJob().proximaRequisicaoES() - e.getJob().getTempoRodado() < cpu.getQuantum()) {
//						addEvento(new Evento(relogio.getTempo() + cpu.getTimeout(), TipoEvento.PEDIDO_E_S, e.getJob()));
//					}
//					else if(e.getJob().getTempoDeProcessamento() < e.getJob().getTempoRodado()) {
//						addEvento(new Evento(relogio.getTempo(), TipoEvento.TERMINO, e.getJob()));
//					}
//					else {
//						addEvento(new Evento(relogio.getTempo() + e.getJob().getTempoDeProcessamento() - e.getJob().getTempoRodado(), TipoEvento.TERMINO, e.getJob()));
//					}
				}
				else {
					s += "Job adicionado à fila de espera do processador";
				}
				break;
				
			case PEDIDO_E_S:
				
//				if(disco.estaOcupado() && e.getJob() != disco.getJobRodando()) {
//					s += "Job entra na fila do disco";
//					disco.solicita(e.getJob(), relogio.getTempo());
//					break;
//				}
				
				e.getJob().incrementaTempoRodado(cpu.getTempoRodando(relogio.getTempo()));
				
				Job j = cpu.libera(e.getJob(), relogio.getTempo());
				
				if(j != null) {
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_PROCESSADOR, j));
				}
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_E_S, e.getJob()));
				
				s += "\tJob libera o processador e espera pelo dispositivo";
				
				break;
				
			case REQUISICAO_E_S:
				
				if(disco.getJobRodando() == e.getJob() || disco.solicita(e.getJob(), relogio.getTempo())) {
					s += "\tJob recebe o dispositivo";
					addEvento(new Evento(relogio.getTempo() + disco.getTempoUsoJob(), TipoEvento.LIBERA_E_S, e.getJob()));
					e.getJob().diminuiRequisicoes();
				}
				else
					s += "\tJob entra na fila do dispositivo";
				
				break;
				
			case LIBERA_E_S:
				
				Job job = disco.libera(e.getJob(), relogio.getTempo());
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
				
				if(job != null) {
	//				System.out.println("Jobd: " + job.getId());
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.PEDIDO_E_S, job));
				}
				
				s += "\tJob libera o disco";
				
				
				break;
				
			case TERMINO:
				
				s += "\t\tJob libera o processador e a memória";
				
				Job j1 = memoria.libera(e.getJob(), relogio.getTempo());
				
				Job j2 = cpu.libera(e.getJob(), relogio.getTempo());
				
				if(j1 != null)
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, j1));
				
				if(j2 != null)
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j2));
				
				break;
				
			case REQUISICAO_DISCO:
				break;
				
			case REQUISICAO_SEGMENTO:
				
				Segmento seg = e.getJob().getSegmentoUso();
				
				break;
				
			case FALTA_SEGMENTO:
				
				seg = e.getJob().getSegmentoUso();
				
				s += "Falta de segmento " + e.getJob().getSegmentos().listaNos().indexOf(seg) + " ";
				
				break;
				
			case LIBERA_DISCO:
				break;
			case PEDIDO_DISCO:
				break;
				
				
			case TIME_SLICE:
				j = cpu.move(relogio.getTempo());
				tempoRodadoSlice = 0;
				
				eventos.add(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j));
				
				
				break;
			
			}
			
			if(s != null) {
				log.add(s);
			}
			
			e = eventos.poll();
			
			if(e != null)
				relogio.setTempo(e.getTempo());
			
		}
		
		System.out.println("Fim da simulação: " + relogio.getTempo());
		
		return log;	
		
	}

	
	private Job verificaChegadaJob(int time) {
		for(Job j : jobs) {
			if (j.getInstanteDeChegada() == time)
				return j;
		}
		return null;
	}
			
	
	private void addEvento(Evento evento) {
		for(Evento e : eventos) {
			if(evento.compareTo(e) < 0) {
				eventos.add(eventos.indexOf(e), evento);
				return;
			}
		}
		eventos.add(evento);
	}
	
}
