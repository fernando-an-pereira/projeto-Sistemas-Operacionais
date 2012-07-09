package recursos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jobs.Job;

public class Memoria extends Recurso {

	private final int tamanho;
	private final int tempoDeRelocacao;
	//private final int tamanhoSegmento;
	private LinkedList<Segmento> segmentosMemoria = new LinkedList<Segmento>();
	private LinkedList<Segmento> segmentosDisco = new LinkedList<Segmento>();

	private ArrayList<Job> jobsRodando = new ArrayList<Job>();
	
	
	
	public Memoria(int tamanho, int tempoDeRelocacao){
	//public Memoria(int tamanho, int tempoDeRelocacao, int tamanhoSegmento){
		this.tamanho = tamanho;
		this.tempoDeRelocacao = tempoDeRelocacao;
		Segmento seg = new Segmento(tamanho);
		seg.libera();
		this.segmentosMemoria.add(seg);
		//this.tamanhoSegmento = tamanhoSegmento;
	}
	
	public void memoriaParaDisco(Segmento segmento) {
		Segmento livre = new Segmento(segmento.getTamanho());
		livre.libera();
		segmentosDisco.add(segmento);
		segmentosMemoria.set(segmentosMemoria.indexOf(segmento), livre);
	}
	
	public boolean discoParaMemoria(Segmento segmento) {
		Segmento seg = procuraSegmento(segmento.getTamanho());
		
		if(seg == null) {
			if(!segmentosDisco.contains(segmento))
				segmentosDisco.add(segmento);
			return false;
		}
		
		seg.quebra(segmento);
		
		segmentosDisco.remove(segmento);
		
		segmentosMemoria.add(segmentosMemoria.indexOf(seg) + 1, segmento);
		
		return true;
		
	}
	
	public boolean verificaSegmentoMemoria(Segmento segmento) {
		return segmentosMemoria.contains(segmento);
	}
	
	public boolean verificaSegmentoDisco(Segmento segmento) {
		return segmentosDisco.contains(segmento);
	}
	
	public void adicionaSegmento(Segmento segmento) {
		
	}
	
//	public void atribui(Job job){ 
//		this.solicita(job);										// 2 solicita se mem requisitada > tamanho vago ?
//		if (job.getMemoriaRequisitada() > this.tamanhovago){
//			this.solicita(job);
//		}
//		else{
//			this.tamanhovago -= job.getMemoriaRequisitada();
//			// job pede CPU
//		}
//		
//	}
	
//	public void desaloca(Job job){
//		this.tamanhovago += job.getMemoriaRequisitada();
//		this.libera(job);
//	}
	
	public List<Job> getJobsRodando() {
		return this.jobsRodando;
	}
	
	public boolean memoriaAlocada(int tempoDoRelogio){
		if(ocupado) {
			ocupado = false;
			return (this.getTempoRodando(tempoDoRelogio) >=  tempoDeRelocacao);
		}
		return false;
	}
	
	public int getTamanho() {
		return this.tamanho;
	}
	
	public int getTempoDeRelocacao() {
		return this.tempoDeRelocacao;
	}
	
	public void garbageCollector() {
		int tamanhoLivre = 0;
		for(Segmento seg : segmentosMemoria) {
			if(!seg.estaOcupado()) {
				tamanhoLivre += seg.getTamanho();
				segmentosMemoria.remove(seg);
			}
		}
		
		Segmento seg = new Segmento(tamanhoLivre);
		seg.libera();
		
		segmentosMemoria.addFirst(seg);
		
	}
	
	public boolean insereSegmento(int tamanho) {
		Segmento seg = procuraSegmento(tamanho);
		
		if(seg == null) {
			return false;
		}
		
		Segmento seg2 = seg.quebra(tamanho);
		
		seg2.ocupa();
		
		segmentosMemoria.add(segmentosMemoria.indexOf(seg) + 1, seg2);
		
		return true;
	}
	
	@Override 
	public boolean solicita(Job job, int instante) {
		
		
		
		return false;
		
	}
	
	@Override
	public Job libera(Job job, int instante) {
		
		if(!jobsRodando.contains(job)) {
			jobs.remove(job);
			return null;
		}
		
		Job j = null;
		
		return j;
	}
	
	private Segmento procuraSegmento(int tamanho) {
		
		for(Segmento seg : segmentosMemoria) {
			if(!seg.estaOcupado() && seg.getTamanho() >= tamanho)
				return seg;	
		}
		
		return null;
	}
	
	public int posicaoSegmento(Segmento segmento) {
		int pos = 0;
		for(Segmento seg : segmentosMemoria) {
			if(seg == segmento)
				break;
			
			pos += seg.getTamanho();
		}
		
		return pos;
		
	}
	
}
