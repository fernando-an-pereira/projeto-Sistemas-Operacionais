package eventos;

import jobs.Job;

public class Evento implements Comparable<Evento> {
	
	private int tempo;
	private TipoEvento tipo;
	private Job job;
	
	public Evento(int tempo, TipoEvento tipo, Job job) {
		this.tempo = tempo;
		this.tipo = tipo;
		this.job = job;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public TipoEvento getTipo() {
		return tipo;
	}
	
	public Job getJob() {
		return job;
	}
	
	@Override
	public int compareTo(Evento e) {
		return ((Integer)tempo).compareTo((Integer)e.getTempo());
	}
	
}
