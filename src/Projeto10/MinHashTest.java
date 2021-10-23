package Projeto10;

import java.util.ArrayList;

public class MinHashTest {

	public static void main(String[] args) {
		double[][] JReal = new double[1986][1986];
		double[][] JMinHash = new double[1986][1986];
		double average_diference = 0;
		ArrayList<Noticia> Noticias = new ArrayList<>();
		for (int i=0; i<1986; i++) {
			Noticia a = new Noticia("","");
			a.setTitulo(BloomFilterTest.RandomString(5));
			Noticias.add(a);
		}
		
		ArrayList<Noticia> N2 = Noticias;
		for (int i=0; i<1986; i++) {
			for (int j = i+1; j<1986;j++) {
				JReal[i][j] = 1.0 - ((double)intersectlength(Noticias.get(i).getTitulo(), Noticias.get(j).getTitulo())/(double)unionlength(Noticias.get(i).getTitulo(), Noticias.get(j).getTitulo()));
			}
			
		}

		long startTime = System.currentTimeMillis();
		JacardMinHash JAC = new JacardMinHash(N2,100,1,1);
		JAC.getSimilar('t');
		long endTime = System.currentTimeMillis();
		long execTime = endTime-startTime;
		JMinHash = JAC.getJ();
		for (int i=0; i<1986; i++) {
			for (int j = i+1; j<1986;j++) {
				average_diference += Math.abs(JReal[i][j]-JMinHash[i][j]);
			}
		}
		average_diference /= ((Math.pow(1986,2)-1986)/2);
		System.out.println("Diferença em média: " + average_diference);
		System.out.println("Tempo de execução do MinHash: " + execTime);
	}
	
	public static int intersectlength(ArrayList<String> A, ArrayList<String> B){
		int count = 0;
		for (int i =0; i<A.size();i++) {
			for (int j = 0; j<B.size();j++) {
				if (A.get(i)==B.get(j)) {
					count++;
				}
			}
		}
		return count;
	}
	public static int unionlength(ArrayList<String> A, ArrayList<String> B){
		int count = A.size() + B.size() - intersectlength(A,B);
		return count;
	}

}
