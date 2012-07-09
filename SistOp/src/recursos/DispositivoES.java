package recursos;

public class DispositivoES extends Recurso {

	int tempoAcesso;
	
	public DispositivoES(int tempoAcesso) {
		this.tempoAcesso = tempoAcesso;
	}
	
	public int getTempoAcesso() {
		return tempoAcesso;
	}

}
