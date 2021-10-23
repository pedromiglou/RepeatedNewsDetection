package Projeto10;
//93321 Luis Pereira
//93283 Pedro Amaral

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class JacardMinHash {
	
	private int k; //numero de hash functions
	private ArrayList<Noticia> Noticias; //arraylist de noticias
	private int nNot; //numero de noticias
	private ArrayList<int[]> assTitulos; //assinaturas dos titulos
	private ArrayList<int[]> assConteudos; //assinaturas dos conteudos
	private ArrayList<String> shingles;
	private int[] a, b; //vetores de valores aleatorios
	private int sep1;
	private int sep2;
	double[][] J;

	//construtor
	public JacardMinHash(ArrayList<Noticia> Noticias, int k, int sep1, int sep2) {
		// obter os vetores de valores aleatorios que serao usados depois na assinatura()
		Random rand = new Random();
		a = new int[k];
		b = new int[k];
		for (int i=0;i<k;i++) a[i] = rand.nextInt(10000);
		for (int i=0;i<k;i++) b[i] = rand.nextInt(10000);
		
		// parte principal
		this.sep1 = sep1;
		this.sep2 = sep2;
		this.k = k;
		this.Noticias=Noticias;
		this.nNot = Noticias.size();
		
		assTitulos = new ArrayList<>();
		assConteudos = new ArrayList<>();
		for(int n=0; n<Noticias.size(); n++) {
			assTitulos.add(assinatura((Noticias.get(n)).getTitulo(), this.sep1));
			assConteudos.add(assinatura((Noticias.get(n)).getConteudo(), this.sep2));
		}
	}
	
	private int[] assinatura(ArrayList<String> str, int sep) {
		shingles = new ArrayList<>();
		for (int i=0; i<=(str.size()-sep); i++) {
			String s="";
			for (int k=i; k<i+sep; k++) {
				s= s + str.get(k)+" ";
			}
			shingles.add(s);
		}
		int[] out = new int[k];
		for(int i=0; i<k; i++) {
			int minimo = 11000;
			int ai = a[i];
			int bi = b[i];
			for(int j=0; j<shingles.size(); j++) {
				minimo = Math.min((ai*Class1.string2hash(shingles.get(j)) + bi)%10007,minimo);
			}
			out[i]=minimo;
			
		}
		return out;
	}

	public ArrayList<String> getSimilar(char c) {
		J = new double[nNot][nNot];
		
		//escolher se vamos usar titulos ou conteudo
		ArrayList<int[]> assinaturas = new ArrayList<>();
		if (c=='t') assinaturas=assTitulos;
		if (c=='c') assinaturas=assConteudos;
		
		// calcular as distancias de Jacard
		for(int i=0; i<nNot; i++) {
			for(int j=i+1; j<nNot; j++) {
				int soma=0;
				for(int l=0; l<this.k; l++) {
					if(assinaturas.get(i)[l] == assinaturas.get(j)[l]) {
						soma++;
					}
				}
				J[i][j]=1.0 - (double) soma/k;
			}
		}
		
		// fazer a lista dos items semelhantes
		ArrayList<String> SimilarTitles = new ArrayList<>();
		for(int i=0; i<nNot; i++) {
			for(int j=i+1; j<nNot; j++) {
				if(J[i][j]< 0.4) {
					String s1 = "";
					for(int l=0; l<Noticias.get(i).getTitulo().size();l++) {
						s1 +=Noticias.get(i).getTitulo().get(l) + " ";
					}
					s1 = s1 +"("+Noticias.get(i).getnomeficheiro()+".txt)";
					String s2 = "";
					for(int l=0; l<Noticias.get(j).getTitulo().size();l++) {
						s2 +=Noticias.get(j).getTitulo().get(l) + " ";
					}
					s2 = s2 +"("+Noticias.get(j).getnomeficheiro()+".txt)";
					String v = "' " + s1 + "' e ' " + s2 + "' - " + Double.toString(J[i][j]);
					SimilarTitles.add(v);
				}
			}
		}
		return SimilarTitles;
	}
	
	public ArrayList<String> checkIfSimilarTitle(ArrayList<String> str) {
		ArrayList<String> SimilarTitles = new ArrayList<>();
		
		int[] assin = assinatura(str,sep1);
		
		for(int i=0; i<nNot; i++) {
			int soma=0;
			for(int l=0; l<this.k; l++) {
				if(assTitulos.get(i)[l] == assin[l]) {
					soma++;
				}
			}
			if ((1.0 - (double) soma/k) < 0.4) {
				String s1 = "";
				for(int l=0; l<Noticias.get(i).getTitulo().size();l++) {
					s1 +=Noticias.get(i).getTitulo().get(l) + " ";
				}
				
				String v = "' " + s1 + "' - " + Double.toString(1.0 - (double) soma/k);
				SimilarTitles.add(v);
			}
		}
		
		return SimilarTitles;
	}
	
	public void addNoticia (Noticia noticia) {
		Noticias.add(noticia);
		nNot++;
		assTitulos.add(assinatura(noticia.getTitulo(),sep1));
		assConteudos.add(assinatura(noticia.getConteudo(),sep2));
	}

	public int getK() {
		return k;
	}

	public ArrayList<Noticia> getNoticias() {
		return Noticias;
	}

	public int getnNot() {
		return nNot;
	}

	public ArrayList<int[]> getAssTitulos() {
		return assTitulos;
	}

	public ArrayList<int[]> getAssConteudos() {
		return assConteudos;
	}

	public int[] getA() {
		return a;
	}

	public int[] getB() {
		return b;
	}

	public int getSep1() {
		return sep1;
	}

	public void setSep1(int sep1) {
		this.sep1 = sep1;
	}

	public int getSep2() {
		return sep2;
	}

	public void setSep2(int sep2) {
		this.sep2 = sep2;
	}
	public double[][] getJ() {
		return J;
	}

	@Override
	public String toString() {
		return "JacardMinHash [k=" + k + ", Noticias=" + Noticias + ", nNot=" + nNot + ", assTitulos=" + assTitulos
				+ ", assConteudos=" + assConteudos + ", a=" + Arrays.toString(a) + ", b=" + Arrays.toString(b) + "]";
	}
	
}
