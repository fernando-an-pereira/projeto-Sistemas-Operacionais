package informacao;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivo {
	
	private List<Arquivo> arquivos = new ArrayList<Arquivo>();
	
	public void addArquivo(Arquivo arq) {
		arquivos.add(arq);
	}
	
	public Arquivo getArquivo(String nome) {
		
		for(Arquivo arq : arquivos) {
			if(arq.getNome().equals(nome))
				return arq;
		}
		
		return null;
	}
	
	public void addArquivos(ArrayList<Arquivo> arqs) {
		arquivos.addAll(arqs);
	}

}
