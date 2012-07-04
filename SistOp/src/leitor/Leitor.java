package leitor;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import jobs.Job;

public class Leitor {
	
	private FileInputStream fis;
	private DataInputStream dis;
	private BufferedReader bf;
	
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
	
	public ArrayList<Job> retornaJobs() throws Exception {
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
				int tdc = Integer.valueOf(lol[4]);
				jobs.add(id - 1, new Job(tdp, mr, res, tdc));
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
	
}
