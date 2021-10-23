package Projeto10;
//93321 Luis Pereira
//93283 Pedro Amaral

import java.util.ArrayList;

public class BloomFilterTest {

	public static void main(String[] args) {
		int m = 1986; // número de items a adicionar
		int size = 10*m; //tamanho do vetor do filtro
		ArrayList<String> strs1, strs2;
		bloomFilter filtro;
		
		
		// inicializar o filtro com i strings
		filtro = new bloomFilter(size, m);
		strs1 = RandomString(m);
		long startTime = System.currentTimeMillis();
		filtro.addElements(strs1);
		long endTime = System.currentTimeMillis();
		long addTime = endTime - startTime; //tempo de adicionar os elementos
		
		//testar o filtro com i strings que lá estão e i que não estão
		strs2 = RandomString(m);
		
		long verifyTime = 0;
		int positives = 0;
		int realpositives=0;
		for (int j =0; j<m;j++) {
			startTime = System.currentTimeMillis();
			if (filtro.ismember(strs2.get(j))) { positives++;}
			endTime = System.currentTimeMillis();
			verifyTime += endTime-startTime;
			if (strs1.contains(strs2.get(j))) { realpositives++;}
		}

		System.out.println("n=" + size + "; m=" + m);
		System.out.println("Positivos no Filtro de Bloom: " + positives);
		System.out.println("Falsos Positivos: " + (positives - realpositives));
		System.out.println("p = " + ((double)(positives - realpositives)/m));
		System.out.println("Tempo a adicionar m elementos: " + addTime);
		System.out.println("Tempo a verificar m elementos: " + verifyTime);
	}
		
	
	// gerar strings aleatória
    static ArrayList<String> RandomString(int n) 
    { 
    	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"; //caracteres possíveis
    	double l; 
        ArrayList<String> strs = new ArrayList<>();
    	
        for (int j=0; j<n; j++) {
        	l = Math.random()*10+1; //length entre 2 e 12
        	//l=4;
	        // gerar a string
	        String str = new String(); 
	        for (int i = 0; i < (int)l; i++) { 
	            int index = (int)(chars.length() * Math.random()); 
	            str += chars.charAt(index);
	        } 
	        strs.add(str);
        }
        return strs;
    } 

}
