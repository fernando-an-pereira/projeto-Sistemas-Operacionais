package eventos;

import java.util.ArrayList;

import jobs.Job;


public class Scheduler {
	ArrayList <Job> listaProcessos;
	
	public Scheduler(ArrayList <Job> lista){
		
		this.listaProcessos = lista;
	}
	
	public void escalonamento(){
		
		// ordena pelo tempo de execução ( menor primeiro ): FALTA TESTAR! espero q quando remova o "j" o q era j+1 vire J!!!

		for(int i = 0; i < listaProcessos.size() - 1; i++){
			for(int j = 0; j < listaProcessos.size() - 1;j++){
				
				if( this.listaProcessos.get(j).getInstanteDeChegada() > this.listaProcessos.get(j+1).getInstanteDeChegada() ){
					Job temp = this.listaProcessos.remove(j);
					this.listaProcessos.add(j, this.listaProcessos.get(j) );
					this.listaProcessos.add(j+1,temp);
				}
			}
		}
		
		while (listaProcessos.isEmpty() == false){
			Job processo = listaProcessos.remove(0);
			
			
			
		}
		//FINALIZA!!!!!!!!!!!!!!!!!!
			
	}
			
			
		
	
}
