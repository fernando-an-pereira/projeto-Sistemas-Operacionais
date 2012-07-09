package leitor;

import informacao.Arquivo;
import informacao.GerenciadorArquivo;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import recursos.Segmento;

import util.ArvoreN;

import jobs.Job;

public class Leitor {
	
	private FileInputStream fis;
	private DataInputStream dis;
	private BufferedReader bf;
	private static ArvoreN<Segmento> arvoreN;
	
	public Leitor(String nomeArquivo) throws FileNotFoundException {
		this.fis = new FileInputStream(nomeArquivo);
		this.dis = new DataInputStream(fis);
		this.bf = new BufferedReader (new InputStreamReader(dis));
	}
	
	public int[] retornaTempoExecucao() throws Exception {
		int[] te = new int[2];
		String linha;
		
		linha = bf.readLine();
		while(linha.charAt(0) == ';') linha = bf.readLine();
		String[] lol = linha.split(" ");
		te[0] = Integer.valueOf(lol[0]);
		
		linha = bf.readLine();
		while(linha.charAt(0) == ';') linha = bf.readLine();
		lol = linha.split(" ");
		te[1] = Integer.valueOf(lol[0]);
		
		linha = bf.readLine();
		
		return te;
	}
	
	public ArrayList<Job> retornaJobs(GerenciadorArquivo gerArq) throws Exception {
		ArrayList<Job> jobs = new ArrayList<Job>();	

		
		String linha;
		
		linha = bf.readLine();
		
		while(linha.length() > 2) {
			if(linha.charAt(0) != ';') {
				String[] lol = linha.split(" ");
				int id = Integer.valueOf(lol[0]);
				int tdp = Integer.valueOf(lol[1]);
				int mr = Integer.valueOf(lol[2]);
				int res = Integer.valueOf(lol[3]);
				int ra = Integer.valueOf(lol[4]);
				int idc = Integer.valueOf(lol[5]);
				
				ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
				
				for(int i = 6; i < lol.length -1; i++) {
					String nome = lol[i];
					arquivos.add(gerArq.getArquivo(nome));
				}
				
//				jobs.add(id - 1, new Job(id, tdp, mr, res, ra, idc));
				
				
			}
			linha = bf.readLine();
		}
		
		return jobs;
	}
	
	public int[] retornaTempoDeChegada(int numJobs) throws Exception {
		int[] tc = new int[numJobs];  
		
		String linha;
		
		linha = bf.readLine();
		
		while(linha.length() > 2) {
			if(linha.charAt(0) != ';') {
				String[] lol = linha.split(" ");
				int id = Integer.valueOf(lol[0]);
				int tdc = Integer.valueOf(lol[1]);
				tc[id - 1] = tdc;
			}
			linha = bf.readLine();
		}
		
		return tc;
	}
	
	public void encerrar() throws IOException {
		dis.close();
		fis.close();
	}
	
//	public static ArvoreN<Segmento> geraArvore(String str) {		
//		String[] lol = str.split("(", 2);
//		
//		Segmento seg = new Segmento(Integer.valueOf(lol[0]));
//		
//		arvoreN = new ArvoreN<Segmento>(seg);
//		
//		passoGeraArvore(seg, lol[1]);
//		
//		return arvoreN;
//	}
//	
//	private static void passoGeraArvore(Segmento seg, String str) {
//		
//		String s = str;
//		
//		int open = s.indexOf("(");
//		int close = s.indexOf(")");
//		int coma = s.indexOf(",");
//		
//		String[] lol;
//		
//		if(str == ";")
//			return;
//		
//		if(coma < close && coma < open && coma > 0) {
//			lol = s.split(",", 2);
//			arvoreN.addReferencia(seg, new Segmento(Integer.valueOf(lol[0])));
//			passoGeraArvore(seg, lol[1]);
//		}
//		else if(open < close && open < coma && open > 0) {
//			lol = s.split("\\(", 2);
//			Segmento cab = new Segmento(Integer.valueOf(lol[0]));
//			arvoreN.addReferencia(seg, cab);
//			passoGeraArvore(cab, lol[1]);
//		}
//		else {
//			lol = s.split("\\)", 2);
//			arvoreN.addReferencia(seg, new Segmento(Integer.valueOf(lol[0])));
//			
//		}
//		
//	}
	
	public ArrayList<Arquivo> arquivos() throws IOException {
		ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
		
		String linha;
		
		linha = bf.readLine();
		
		while(linha.length() > 2) {
			if(linha.charAt(0) != ';') {
				String[] lol = linha.split(" ");
				String nome = lol[0];
				int tam = Integer.valueOf(lol[1]);
				int pub = Integer.valueOf(lol[2]);
				int idJob = Integer.valueOf(lol[3]);
				boolean publico;
				if(pub == 0)
					publico = false;
				else
					publico = true;
				arquivos.add(new Arquivo(nome, tam, publico, idJob));
			}
			linha = bf.readLine();
		}
		
		return arquivos;
	}
	
}
