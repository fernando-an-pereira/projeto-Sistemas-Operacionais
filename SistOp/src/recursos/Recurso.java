package recursos;

import java.util.Queue;
import java.util.LinkedList;
import jobs.Job;

public class Recurso {
	private Queue<Job> jobs = new LinkedList<Job>();
	private boolean ocupado;
	
	public void solicita(Job job) {
		jobs.add(job);
	}
	
	public void libera(Job job){
		jobs.remove(job);
	}
	
	public void emUso(){
		this.ocupado = true;
	}
	
	public void livre(){
		this.ocupado = false;
	}
	
	public boolean estaOcupado(){
		return ocupado;
	}
	
}
