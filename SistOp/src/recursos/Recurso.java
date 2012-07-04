package recursos;

import java.util.Queue;
import java.util.LinkedList;
import jobs.Job;

public class Recurso {
	private Queue<Job> jobs = new LinkedList<Job>();
	
	public void solicita(Job job) {
		jobs.add(job);
	}
	
	public void libera(Job job){
		jobs.remove(job);
	}
	
	
	
}
