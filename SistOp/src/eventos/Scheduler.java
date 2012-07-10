package eventos;

import informacao.Arquivo;
import informacao.GerenciadorArquivo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import jobs.Job;

import tempo.Relogio;

import recursos.*;


public class Scheduler {
	
	private List<Job> jobs;
	private GerenciadorArquivo gerArq;
	
	LinkedList<Evento> eventos = new LinkedList<Evento>(); 

//	private List<Evento> eventos;
	
	public Scheduler(ArrayList<Job> listaProcessos, GerenciadorArquivo gerArq){
		
		this.jobs = listaProcessos;
		this.gerArq = gerArq;
		
		for(Arquivo arq : gerArq.getArquivos()) {
			arq.setPossuidor(jobs.get(arq.getIdJob() - 1));
		}
		
//		this.eventos = new ArrayList<Evento>();
	}
	
	public ArrayList<String> escalonamento(int tempoInicio, int tempoFim){
		
		Relogio relogio = new Relogio(tempoInicio, tempoFim);
		Memoria memoria = new Memoria(100, 10);
		Disco disco = new Disco(500, 100);
		DispositivoES device = new DispositivoES(1000);
		CPU cpu = new CPU(10, 20);
		int overheadTime = 15; 
		Random rd = new Random();
		int tempoRodadoSlice = 0;
//		List<Job> jobsRodando = new ArrayList<Job>();
		
		ArrayList<String> log = new ArrayList<String>();
		
		for(Job j : jobs) {
			addEvento(new Evento(j.getInstanteDeChegada(),TipoEvento.CHEGADA, j));
//			for(Segmento seg : j.getSegmentos().listaNos()) {
//				System.out.print("segmento: " + seg.getTamanho() +" ,");
//			}
		}
		
		Evento e = eventos.poll();
		
		relogio.setTempo(e.getTempo());
		
		while(e != null && !relogio.tempoEncerrado()) {
			
			String s = e.getTempo() + "\t\t" + e.getJob().getId() + "\t" + e.getTipo() + "\t";
			
			
			switch(e.getTipo()) {
				
				
			case INVALIDO:
				
				addEvento(new Evento(relogio.getTempo(), TipoEvento.TERMINO, e.getJob()));
				s += "\tJob realizou operação inválida";
				
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
					
					int proximaReES = relogio.getTempoFim();
					int proximoAcessoArq = relogio.getTempoFim();
					int tempoTermino = e.getJob().getTempoDeProcessamento() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					
					if(e.getJob().getRequisicoesES() > 0)
						proximaReES = e.getJob().proximaRequisicaoES() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					
					if(e.getJob().getAcessoArquivos() > 0)
						proximoAcessoArq = e.getJob().proximoAcessoArquivo() - e.getJob().getTempoRodado() - tempoRodadoSlice;
					
					int proximaRefSegmento = rd.nextInt(cpu.getQuantum() * 2) + cpu.getQuantum() / 8;
					
					if(proximaReES <= proximoAcessoArq && proximaReES <= proximaRefSegmento && proximaReES < cpu.getQuantum() - tempoRodadoSlice) {
						addEvento(new Evento(relogio.getTempo() + proximaReES, TipoEvento.PEDIDO_E_S, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else if(proximoAcessoArq < proximaReES && proximoAcessoArq <= proximaRefSegmento && proximoAcessoArq < cpu.getQuantum() - tempoRodadoSlice) {
//						System.out.println("proximo acesso: " + proximoAcessoArq + ", tempo slice: " + tempoRodadoSlice + ", tempo rodando: " + e.getJob().getTempoRodado());
						addEvento(new Evento(relogio.getTempo() + proximoAcessoArq, TipoEvento.ACESSA_ARQUIVO, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else if(proximaRefSegmento < cpu.getQuantum() - tempoRodadoSlice && proximaRefSegmento < tempoTermino) {
						addEvento(new Evento(relogio.getTempo() + proximaRefSegmento, TipoEvento.REQUISICAO_SEGMENTO, e.getJob()));
						tempoRodadoSlice += proximaRefSegmento;
					}
					else if(tempoTermino <= cpu.getQuantum() - tempoRodadoSlice) {
						addEvento(new Evento(relogio.getTempo() + tempoTermino, TipoEvento.TERMINO, e.getJob()));
						tempoRodadoSlice = 0;
					}
					else {
						addEvento(new Evento(relogio.getTempo() + cpu.getQuantum() - tempoRodadoSlice, TipoEvento.TIME_SLICE, e.getJob()));
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
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j));
				}
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_E_S, e.getJob()));
				
				s += "\tJob libera o processador e espera pelo dispositivo";
				
				break;
				
			case REQUISICAO_E_S:
				
				if(device.getJobRodando() == e.getJob() || device.solicita(e.getJob(), relogio.getTempo())) {
					s += "\tJob recebe o dispositivo";
					addEvento(new Evento(relogio.getTempo() + disco.getTempoUsoJob(), TipoEvento.LIBERA_E_S, e.getJob()));
					e.getJob().diminuiRequisicoes();
				}
				else
					s += "\tJob entra na fila do dispositivo";
				
				break;
				
			case LIBERA_E_S:
				
				Job job = device.libera(e.getJob(), relogio.getTempo());
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
				
				if(job != null) {
	//				System.out.println("Jobd: " + job.getId());
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_E_S, job));
				}
				
				s += "\tJob libera o dispositivo";
				
				
				break;
				
			case TERMINO:
				
				s += "\t\tJob libera o processador e a memória";
				
				Job j1 = memoria.libera(e.getJob(), relogio.getTempo());
				
				Job j2 = cpu.libera(e.getJob(), relogio.getTempo());
				
				if(e.getJob().getArquivoEmUso() != null) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.LIBERA_ARQUIVO, e.getJob()));
				}
				
				if(j1 != null)
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_MEMORIA, j1));
				
				if(j2 != null)
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j2));
				
				break;
				
			case PEDIDO_DISCO:
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_DISCO, e.getJob()));
				
				s += "\tJob espera pelo disco";
				
				break;
				
			case REQUISICAO_DISCO:
				
				if(cpu.interrompido() && e.getJob() == cpu.getJobRodando()) {
					if(e.getJob() == disco.getJobRodando() || disco.solicita(e.getJob(), relogio.getTempo())) {
						s += "Tratamento do pedido de interrupcao recebe o disco";
						addEvento(new Evento(relogio.getTempo() + disco.getTempoUsoJob(), TipoEvento.LIBERA_DISCO, e.getJob()));
					}
					else
						s += "Tratamento da interrupcao entra na fila do disco";
					
					break;
				}
				
				if(disco.getJobRodando() == e.getJob() || disco.solicita(e.getJob(), relogio.getTempo())) {
					s += "Job recebe o disco e acessa o arquivo ";
					addEvento(new Evento(relogio.getTempo() + disco.getTempoUsoJob(), TipoEvento.LIBERA_DISCO, e.getJob()));
					e.getJob().setArquivoEmUso(e.getJob().proximoArquivoAcessado());
					s += e.getJob().getArquivoEmUso().getNome();
					e.getJob().diminuiAcessos();
				}
				else
					s += "Job entra na fila do disco";
				
				break;
				
			case LIBERA_DISCO:
				job = disco.libera(e.getJob(), relogio.getTempo());
				
				if(job != null) {
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_DISCO, job));
				}
				
				if(cpu.interrompido() && e.getJob() == cpu.getJobRodando()) {
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.FIM_INTERRUPCAO, e.getJob()));
					s += "\tTratamento do pedido de interrupção libera disco";
					break;
				}
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
				
				s += "\tJob libera o disco";
				
				break;
				
			case REQUISICAO_SEGMENTO:
				Segmento seg = e.getJob().proximoSegmentoUso();
				
				if(!memoria.verificaSegmentoMemoria(seg)) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.FALTA_SEGMENTO, e.getJob()));
					s += "Falta do segmento S" + e.getJob().getSegmentos().listaNos().indexOf(seg);
				}
				else {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
					s += "Referencia o segmento S" + e.getJob().getSegmentos().listaNos().indexOf(seg) + ", presente na memória";
				}
				
				s += ". Tamanho: " + seg.getTamanho();
				
				break;
				
			case FALTA_SEGMENTO:
				seg = e.getJob().getSegmentoUso();
				s += "\tFalta de segmento S" + e.getJob().getSegmentos().listaNos().indexOf(seg) + " na memória";
				
				cpu.pedidoInterrupcao(relogio.getTempo());
				
				if(!memoria.discoParaMemoria(seg)) {
					memoria.garbageCollector();
					if(!memoria.discoParaMemoria(seg)) {
						memoria.trocaSegmentoMaisAntigo(seg.getTamanho());
						memoria.discoParaMemoria(seg);
					}
				}
				
				addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.REQUISICAO_DISCO, e.getJob()));
				
				break;
				
			case FIM_INTERRUPCAO:
				
				cpu.fimInterrupcao(relogio.getTempo());
				
				addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, e.getJob()));
				
				s += "\tFim do tratamento da interrupção";
				
				break;
				
				
			case TIME_SLICE:
				e.getJob().incrementaTempoRodado(cpu.getTempoRodando(relogio.getTempo()));
				
				j = cpu.move(relogio.getTempo());
				
				addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j));
				
				s += "\tEntrega o processador para o job " + j.getId();
				
				break;
				
			case ACESSA_ARQUIVO:
				Arquivo arq = e.getJob().proximoArquivoAcessado();
				
				if(!arq.possuiPermissao(e.getJob())) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.INVALIDO, e.getJob()));
					s += "\tJob não possui permissão para acessar o arquivo";
					break;
				}
				
				
				e.getJob().incrementaTempoRodado(cpu.getTempoRodando(relogio.getTempo()));
				
				j = cpu.libera(e.getJob(), relogio.getTempo());
				
				if(j != null) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.REQUISICAO_PROCESSADOR, j));
				}
				
				s += "\tJob libera o processador e ";
				
				if(e.getJob().getArquivoEmUso() != null) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.LIBERA_ARQUIVO, e.getJob()));
				}
				
				if(arq.getJobRodando() == e.getJob() || arq.solicita(e.getJob(), relogio.getTempo())) {
					addEvento(new Evento(relogio.getTempo(), TipoEvento.PEDIDO_DISCO, e.getJob()));
					s += "acessa o arquivo ";
				}
				else {
					
					s += "entra na fila do arquivo ";
				}
				
				s += arq.getNome();
				
				break;
				
			case LIBERA_ARQUIVO:
				arq = e.getJob().getArquivoEmUso();
				j = arq.libera(e.getJob(), relogio.getTempo());
				if(j != null)
					addEvento(new Evento(relogio.getTempo() + overheadTime, TipoEvento.ACESSA_ARQUIVO, j));
				s += "\tJob libera o arquivo " + arq.getNome();
				break;
				
			default:
				break;
			
			}
			
			if(s != null) {
				log.add(s);
//				System.out.println(s);
			}
			
//			System.out.println("tempo rodando: " + e.getJob().getTempoRodado());
			
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
