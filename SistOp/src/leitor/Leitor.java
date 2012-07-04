package leitor;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;

import jobs.Job;

public class Leitor {
	
	public List<Job> retornaJobs(BufferedReader bf) throws Exception {
		List<Job> jobs = new LinkedList<Job>();	
//		FileInputStream fis = new FileInputStream(nomeArquivo);
//		DataInputStream dis = new DataInputStream(fis);
//		BufferedReader bf = new BufferedReader (new InputStreamReader(dis));
		
		String linha;
		
		linha = bf.readLine();
		
		while(linha.length() > 2) {
			if(linha.charAt(0) != ';') {
				String[] lol = linha.split(" ");
				int id = Integer.valueOf(lol[0]);
				int tdp = Integer.valueOf(lol[1]);
				int mr = Integer.valueOf(lol[2]);
				int res = Integer.valueOf(lol[3]);
				jobs.add(new Job(id, tdp, mr, res));
			}
			linha = bf.readLine();
		}
		
		return jobs;
	}
	
	public Map<Integer, Integer> retornaTempoDeChegada() throws Exception {
		Map<Integer, Integer> tc = new HashMap<Integer, Integer>();
		
		return tc;
	}
	
}
