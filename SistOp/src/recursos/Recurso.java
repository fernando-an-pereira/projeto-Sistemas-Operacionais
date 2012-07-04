package recursos;

import java.util.Queue;
import java.util.LinkedList;
import jobs.Job;

public class Recurso {
	private Queue<Job> jobs = new LinkedList<Job>();
	private boolean ocupado;
	
	public void solicita(Job job) {
		if(this.ocupado == false){
			this.ocupado = true;
		}
		else{
			jobs.add(job);
		}
	}
	
	public void libera(Job job){
		jobs.remove(job);
		if (jobs.peek() == null){ // verifica se tem algo no topo
			this.ocupado = false;
		}
		else{
			this.ocupado = true;
		}
	}
<<<<<<< HEAD
	

=======
>>>>>>> branch 'master' of https://github.com/fernando-an-pereira/projeto-Sistemas-Operacionais.git
	
	public boolean estaOcupado(){
		return ocupado;
	}
	
}
