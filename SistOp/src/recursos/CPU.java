package recursos;

public class CPU extends Recurso {

	private final int timeout;
	
	public CPU(int timeout) {
		this.timeout = timeout;
	}
	
	public int getTimeout(){
		return this.timeout;
	}

}
