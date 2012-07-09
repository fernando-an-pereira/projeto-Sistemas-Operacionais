package jobs;

import java.util.ArrayList;
import java.util.Random;

import recursos.Segmento;
import util.ArvoreN;

public class Job implements Comparable<Job> {
	
	private int id;
	private int tempoDeProcessamento;
	private int memoriaRequisitada;
	private int requisicoesES;
	private int acessoArquivos;
	private int instanteDeChegada;
	private int tempoRodado;
	private ArrayList<Integer> tempoRequisicoesES = new ArrayList<Integer>();
	private ArrayList<Integer> tempoAcessoArquivos = new ArrayList<Integer>();
	private ArvoreN<Segmento> segmentos;
	
	
	public Job(int id, int tempoDeProcessamento, int memoriaRequisitada, int requisicoesES, int acessoArquivos, int instanteDeChegada) {
		this.id = id;
		this.instanteDeChegada = instanteDeChegada;
		this.tempoDeProcessamento = tempoDeProcessamento;
		this.memoriaRequisitada = memoriaRequisitada;
		this.requisicoesES = requisicoesES;
		this.acessoArquivos = acessoArquivos;
		this.tempoRodado = 0;
		
		int tdp = tempoDeProcessamento; 
		
		Random rd = new Random(id + instanteDeChegada + tempoDeProcessamento + memoriaRequisitada + requisicoesES + acessoArquivos);
		
		for(int i = 0; i < requisicoesES; i++) {
			
			int tempo = rd.nextInt(tdp / ((requisicoesES - i) * 2)) + tdp / ((requisicoesES - i) * 2);
			
			tdp -= tempo;
			
			int tempoAnt;
			
			try {
				tempoAnt = tempoRequisicoesES.get(i - 1);
			}
			catch (Exception e) {
				tempoAnt = 0;
			}
			
			tempoRequisicoesES.add(tempo + tempoAnt);
			
		}
		
		tdp = tempoDeProcessamento; 
		
		for(int i = 0; i < acessoArquivos; i++) {
			
			int tempo = rd.nextInt(tdp / ((acessoArquivos - i) * 2)) + tdp / ((acessoArquivos - i) * 2);
			
			tdp -= tempo;
			
			int tempoAnt;
			
			try {
				tempoAnt = tempoAcessoArquivos.get(i - 1);
			}
			catch (Exception e) {
				tempoAnt = 0;
			}
			
			tempoAcessoArquivos.add(tempo + tempoAnt);
			
		}
		
		
		
	}

	public int getId() {
		return id;
	}
	
	public int getTempoDeProcessamento() {
		return tempoDeProcessamento;
	}

//	public void setTempoDeProcessamento(int tempoDeProcessamento) {
//		this.tempoDeProcessamento = tempoDeProcessamento;
//	}

	public int getMemoriaRequisitada() {
		return memoriaRequisitada;
	}

//	public void setMemoriaRequisitada(int memoriaRequisitada) {
//		this.memoriaRequisitada = memoriaRequisitada;
//	}

	public int getRequisicoesES() {
		return requisicoesES;
	}
	
	public int getAcessoArquivos() {
		return acessoArquivos;
	}
	
	public int getInstanteDeChegada(){
		return this.instanteDeChegada;
	}
	
	public void diminuiRequisicoes(){
		this.requisicoesES--;
	}
	
	public void diminuiAcessos() {
		this.acessoArquivos--;
	}

//	public void setRequisicoesES(int requisicoesES) {
//		this.requisicoesES = requisicoesES;
//	}
	
	@Override
	public int compareTo(Job j) {
		return ((Integer)this.instanteDeChegada).compareTo((Integer)j.getInstanteDeChegada());
	}
	
	public int getTempoRodado() {
		return tempoRodado;
	}
	
	public void incrementaTempoRodado(int t) {
		tempoRodado += t;
	}
	
	public int proximaRequisicaoES() {
		return (tempoRequisicoesES.get(tempoRequisicoesES.size() - requisicoesES));
	}
	
	public int proximaAcessoArquivo() {
		return (tempoAcessoArquivos.get(tempoAcessoArquivos.size() - acessoArquivos));
	}
	
}
