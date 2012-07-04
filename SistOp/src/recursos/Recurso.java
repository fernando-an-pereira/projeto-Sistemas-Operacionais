package recursos;

import java.util.Queue;
import java.util.LinkedList;
import jobs.Job;

public class Recurso {
	private Queue<Job> jobs = new LinkedList<Job>();
	private boolean ocupado;
	private Job jobRodando;
	private int tempoRestanteJobRodando;
	
	public void solicita(Job job) {
		if(this.ocupado == false){
			this.adicionaJobUtilizandoRecurso(job);
		}
		else{
			jobs.add(job);
		}
	}
	
	public void libera(Job job){
		jobs.remove(job);
		this.jobRodando = null;
		if (jobs.peek() == null){ // verifica se tem algo no topo
			this.adicionaJobUtilizandoRecurso(job);
		}
		else{
			solicita(job);
		}
	}
	
	public boolean estaOcupado(){
		return ocupado;
	}
	
	public void forcaOcupado(){
		this.ocupado = true;
	}
	
	public boolean atualizaTempoJobEmExecucao(int tempoPercorrido){ // retorna true caso o recurso seja liberado
		this.tempoRestanteJobRodando = this.tempoRestanteJobRodando - tempoPercorrido;
		if(this.tempoRestanteJobRodando <= 0){
			this.libera(this.jobRodando);
			return true;
		}
		else
			return false;
	}
	
	private void adicionaJobUtilizandoRecurso(Job job){
		this.ocupado = true;
		this.jobRodando = job;
		this.tempoRestanteJobRodando = jobRodando.getTempoDeProcessamento();
	}
	
	public Job getJobRodando(){
		return this.jobRodando;
	}
	
	
	
}
