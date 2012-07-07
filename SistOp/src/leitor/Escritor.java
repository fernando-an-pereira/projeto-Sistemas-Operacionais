package leitor;

import java.io.*;

public class Escritor {
	
	private static BufferedWriter bwSaida;
	private FileWriter fwsaida;
	private int inicio;
	private int fim;
	private int intervalo;
	private final String nomeArquivoDeSaida = "resultados.txt";
	
	
	
	public Escritor(int inicio, int fim, int intervalo){
		this.inicio = inicio;
		this.fim = fim;
		this.intervalo = inicio;
		
		File arquivo = new File(nomeArquivoDeSaida);
		
		try {
			fwsaida = new FileWriter(arquivo, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bwSaida = new BufferedWriter(fwsaida);

	}
	
	
	public void write(int instante, String tipoDeEvento, String nomePrograma, String acaoExecutada, String resultado){
		
		/* if(  ){  SE TEMPO DA SAIDA >= TEMPO ATUAL DA SIMULACAO */ 
		
			try {
				bwSaida.write("Instante: " + instante + "\t" + tipoDeEvento + "\t" + nomePrograma + "\t" + acaoExecutada + "\t" + resultado);
				bwSaida.newLine();
				bwSaida.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		
	}
	
	
}
