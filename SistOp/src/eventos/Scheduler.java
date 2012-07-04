package eventos;

import java.util.ArrayList;

import jobs.Job;

import tempo.Relogio;

import recursos.*;


public class Scheduler {
	ArrayList <Job> listaProcessos;
	
	public Scheduler(ArrayList <Job> lista){
		
		this.listaProcessos = lista;
	}
	
	public void escalonamento(int tempoInicio, int tempoFim){
		
		// ordena pelo instante de chegada ( menor primeiro ): FALTA TESTAR! espero q quando remova o "j" o q era j+1 vire J!!!

		for(int i = 0; i < listaProcessos.size() - 1; i++){
			for(int j = 0; j < listaProcessos.size() - 1;j++){
				
				if( this.listaProcessos.get(j).getInstanteDeChegada() > this.listaProcessos.get(j+1).getInstanteDeChegada() ){
					Job temp = this.listaProcessos.remove(j);
					this.listaProcessos.add(j, this.listaProcessos.get(j) );
					this.listaProcessos.add(j+1,temp);
				}
			}
		}
		
		Relogio relogio = new Relogio(tempoInicio, tempoFim);
		Memoria memoria = new Memoria(80);
		Disco disco = new Disco(50);
		CPU cpu = new CPU(10);
		
		
		while (listaProcessos.isEmpty() == false){
			Job processo = listaProcessos.remove(0);
			memoria.atribui(processo);
			
			
			
		}
		//FINALIZA!!!!!!!!!!!!!!!!!!
			
	}
			
			
		
	
}
