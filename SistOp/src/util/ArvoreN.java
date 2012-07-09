package util;

import java.util.ArrayList;
import java.util.List;

public class ArvoreN<E> {
	
	private static class No<E> {
		private E e;
		private List<No<E>> referencias;
		
		private No(E e) {
			this.e = e;
		}
		
	}
	
	private No<E> cabeca;
	private List<No<E>> nos = new ArrayList<No<E>>();
	
	public ArvoreN () {
		cabeca = null;
	}
	
	public ArvoreN (E cabeca) {
		No<E> no = new No<E>(cabeca);		
		nos.add(no);
		this.cabeca = no;
	}
	
	public void setCabeca(E e) {
		
		for(No<E> no : nos) {	
			if(no.e == e) {
				cabeca = no;
				return;
			}	
		}
		
		No<E> no = new No<E>(e);
		nos.add(no);
		cabeca = no;
		
	}
	
	public boolean add(E e) {
		
		for(No<E> no : nos) {
			if(no.e == e)
				return false;
		}
		
		nos.add(new No<E>(e));
		
		return true;
		
	}
	
	public boolean addReferencia(E e, E ref) {
		
		for(No<E> no1 : nos) {
			if(no1.e == e) {
				for(No<E> no2 : nos) {
					if(no2.e == ref) {
						no1.referencias.add(no2);
						return true;
					}
				}
				No<E> no = new No<E>(ref);
				no1.referencias.add(no);
				nos.add(no);
			}
		}
		
		return false;
	}
	
	public boolean addReferencia(E e, List<E> refs) {
		
		for(E ref : refs) {
			if(!addReferencia(e, ref))
				return false;
		}
		
		return true;
	}
	
	public List<E> getReferencias(E e) {
		
		List<E> referencias = new ArrayList<E>();
		
		for(No<E> no : nos) {
			if(no.e == e) {
				for(No<E> noRef : no.referencias) {
					referencias.add(noRef.e);
				}
				return referencias;
			}
		}
		
		return null;
	}
	
	public E getCabeca() {
		return cabeca.e;
	}
	
	public List<E> listaNos() {
		
		List<E> elementosNos = new ArrayList<E>();
		
		for(No<E> no : nos) {
			elementosNos.add(no.e);
		}
		
		return elementosNos;
		
	}

}
