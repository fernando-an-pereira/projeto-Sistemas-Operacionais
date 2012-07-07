package recursos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jobs.Job;

public class Memoria extends Recurso {

	private final int tamanho;
	private final int tempoDeRelocacao;
	//private final int tamanhoSegmento;
	private LinkedList<Segmento> segmentos = new LinkedList<Segmento>();

	private ArrayList<Job> jobsRodando = new ArrayList<Job>();
	
	public Memoria(int tamanho, int tempoDeRelocacao){
	//public Memoria(int tamanho, int tempoDeRelocacao, int tamanhoSegmento){
		this.tamanho = tamanho;
		this.tempoDeRelocacao = tempoDeRelocacao;
		this.segmentos.add(new Segmento(tamanho));
		//this.tamanhoSegmento = tamanhoSegmento;
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
		for(Segmento seg : segmentos) {
			if(!seg.estaOcupado()) {
				tamanhoLivre += seg.getTamanho();
				segmentos.remove(seg);
			}
		}
		
		Segmento seg = new Segmento(tamanhoLivre);
		seg.libera();
		
		segmentos.add(seg);
		
	}
	
	public boolean insereSegmento(int tamanho) {
		Segmento seg = procuraSegmento(tamanho);
		
		if(seg == null) {
			return false;
		}
		
		Segmento seg2 = seg.quebra(tamanho);
		
		
		
		
		return true;
	}
	
	@Override
	public Job libera(Job job, int instante) {
		
		if(!jobsRodando.contains(job)) {
			jobs.remove(job);
			return null;
		}
		
		this.tamanhovago += job.getMemoriaRequisitada();

		Job j = null;
		
		for(Job jb : jobs) {
			if(jb.getMemoriaRequisitada() <= this.tamanhovago){
				j = jb;
				break;
			}
		}
		
		if(j != null) {
			jobsRodando.add(j);
			this.tamanhovago -= j.getMemoriaRequisitada();
			this.instanteInicial = instante;
		}
		
		return j;
	}
	
	private Segmento procuraSegmento(int tamanho) {
		
		for(Segmento seg : segmentos) {
			if(!seg.estaOcupado() && seg.getTamanho() >= tamanho)
				return seg;	
		}
		
		return null;
	}
	
	public int posicaoSegmento(Segmento segmento) {
		int pos = 0;
		for(Segmento seg : segmentos) {
			if(seg == segmento)
				break;
			
			pos += seg.getTamanho();
		}
		
		return pos;
		
	}
	

}
