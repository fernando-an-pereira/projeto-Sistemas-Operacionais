package jobs;

import informacao.Arquivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import recursos.Segmento;
import util.ArvoreN;

public class Job implements Comparable<Job> {
	
	private int id;
	private int tempoDeProcessamento;
	private int requisicoesES;
	private int acessoArquivos;
	private int instanteDeChegada;
	private int tempoRodado;
	private ArrayList<Integer> tempoRequisicoesES = new ArrayList<Integer>();
	private ArrayList<Integer> tempoAcessoArquivos = new ArrayList<Integer>();
	private ArrayList<Arquivo> arquivos;
	private HashMap<Integer, Arquivo> arquivoAcessadoTempo = new HashMap<Integer, Arquivo>();
	private ArvoreN<Segmento> segmentos;
	private Segmento segmentoUso;
	
	
	public Job(int id, int tempoDeProcessamento, int requisicoesES, int acessoArquivos, int instanteDeChegada, ArrayList<Arquivo> arquivos) {
		this.id = id;
		this.instanteDeChegada = instanteDeChegada;
		this.tempoDeProcessamento = tempoDeProcessamento;
		this.requisicoesES = requisicoesES;
		this.acessoArquivos = acessoArquivos;
		this.tempoRodado = 0;
		this.arquivos = arquivos;
		
		int tdp = tempoDeProcessamento; 
		
		Random rd = new Random(id + instanteDeChegada + tempoDeProcessamento + requisicoesES + acessoArquivos);
		
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
		
		for(int tempo : tempoAcessoArquivos) {
			Arquivo arq = arquivos.get(rd.nextInt(arquivos.size()));
			arquivoAcessadoTempo.put(tempo, arq);
		}
		
		Segmento seg = new Segmento(20);
		segmentos = new ArvoreN<Segmento>(seg);
		Segmento seg2 = new Segmento(15);
		segmentos.addReferencia(seg, seg2);
		Segmento seg3 = new Segmento(25);
		segmentos.addReferencia(seg, seg3);
		Segmento seg4 = new Segmento(10);
		segmentos.addReferencia(seg3, seg4);
		
		segmentoUso = segmentos.getCabeca();
		
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
	
	public int proximoAcessoArquivo() {
		return (tempoAcessoArquivos.get(tempoAcessoArquivos.size() - acessoArquivos));
	}
	
	public Arquivo proximoArquivoAcessado() {
		return arquivoAcessadoTempo.get(proximoAcessoArquivo());
	}

	public ArvoreN<Segmento> getSegmentos() {
		return segmentos;
	}
	
	public Segmento proximoSegmentoUso() {
		List<Segmento> refs = segmentos.getReferencias(segmentoUso);
		
		Random rd = new Random();
		
		if(!refs.isEmpty()) {
			segmentoUso = refs.get(rd.nextInt(refs.size() - 1));
		}
		
		return segmentoUso;
	}
	
	public Segmento getSegmentoUso() {
		return segmentoUso;
	}
	
}
