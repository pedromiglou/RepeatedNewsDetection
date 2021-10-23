package Projeto10;
//93321 Luis Pereira
//93283 Pedro Amaral

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Class1 {
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int opcao=-1;
		
		//Carregar as notícias
		System.out.println("Loading News");
		ArrayList<Noticia> Noticias = loadNoticias();
		
		//Carregar o Bloom Filter
		System.out.println("Loading Bloom Filter");
		bloomFilter filtro = new bloomFilter(10*Noticias.size(),Noticias.size());
		for (int i=0; i<Noticias.size();i++) {
			filtro.addElement(Noticias.get(i).getTituloString());
		}
		
		//Carregar a MinHash
		System.out.println("Loading MinHash");
		JacardMinHash JAC = new JacardMinHash(Noticias, 100,1,8);
		
		
		//Menu
		while (opcao!=0) {
			System.out.println("Opções:");
			System.out.println("0-Sair");
			System.out.println("1-Imprimir títulos repetidos");
			System.out.println("2-Imprimir títulos similares");
			System.out.println("3-Imprimir títulos de notícias com conteúdos semelhantes");
			System.out.println("4-Verificar se um determinado título já existe");
			System.out.println("5-Verificar se existem titulos semelhantes a um determinado título");
			System.out.println("6-Adicionar Notícia");
			System.out.print("Opção? ");
			opcao = sc.nextInt();
			sc.nextLine();
			
			switch (opcao) {
			case 1:
				getTitulosRepetidos(Noticias);
				break;
			case 2:
				ArrayList<String> similarTitles = JAC.getSimilar('t');
				for (int i = 0; i<similarTitles.size(); i++) {
					System.out.println(similarTitles.get(i));
				}
				break;
			case 3:
				ArrayList<String> similarContent = JAC.getSimilar('c');
				for (int i = 0; i<similarContent.size(); i++) {
					System.out.println(similarContent.get(i));
				}
				break;
			case 4:
				System.out.print("Titulo: ");
				String s4 = sc.nextLine();
				if (filtro.ismember(s4.toLowerCase()+" ")) {
					System.out.println("Pertence");
				} else {
					System.out.println("Não Pertence");
				}
				break;
			case 5:
				System.out.print("Titulo: ");
				String s5 = sc.nextLine();
				ArrayList<String> str5 = new ArrayList<>();
				Collections.addAll(str5,s5.toLowerCase().split(" "));
				ArrayList<String> similar5;
				similar5 = JAC.checkIfSimilarTitle(str5);
				for (int i=0; i<similar5.size(); i++) {
					System.out.println(similar5.get(i));
				}
				break;
			case 6:
				System.out.print("Título: ");
				String t6 = sc.nextLine();
				
				System.out.print("Conteúdo: ");
				String c6 = sc.nextLine();
				
				Noticia n6 = new Noticia(t6, c6);
				Noticias.add(n6);
				JAC.addNoticia(n6);
				filtro.addElement(n6.getTituloString());
				
				break;
			}
		}
		
		sc.close();
		
	}

	private static void getTitulosRepetidos(ArrayList<Noticia> Noticias) {
		bloomFilter filtro = new bloomFilter(10*Noticias.size(),Noticias.size());
		ArrayList<String> repetidos = new ArrayList<>();
		for (int i=0; i<Noticias.size();i++) {
			if (filtro.ismember(Noticias.get(i).getTituloString())) {
				repetidos.add(Noticias.get(i).getTituloString());
			} else {
				filtro.addElement(Noticias.get(i).getTituloString());
			}
		}
		for (int i=0; i<repetidos.size(); i++) {
			System.out.println(repetidos.get(i)+";");
		}
	}

	public static int string2hash(String s) {
		int[] D = new int[s.length()];
		for(int i = 0; i<s.length();i++) {
			int d = s.charAt(i);
			D[i] = d;
		}
		long hash = 5381;
		for(int k = 0; k<s.length();k++) {
			hash = (hash*33^k + D[k])%((long)(Math.pow(2, 31)-1));
		}
		return (int)hash;
	}
	public static Noticia readFile(String path) throws IOException{
		File file = new File(path);
		String titulo;
		String conteudo = "";
		String line;
		BufferedReader fr = new BufferedReader(new FileReader(file));
		if ((titulo = fr.readLine())!=null) {
			while((line = fr.readLine()) != null) {
				if (line != "") {
					conteudo += line;
				}
			}
		}
		Noticia noticia = new Noticia(titulo, conteudo);
		fr.close();
		return noticia;
		
	}
	public static ArrayList<Noticia> loadNoticias() throws IOException{
		ArrayList<Noticia> Noticias = new ArrayList<>();
		for (int i=1; i<=400; i++) {
			Noticias.add(readFile(String.format("src/Noticias/tech/%03d.txt", i)));
			Noticias.get(i-1).setnomeficheiro("(t..." + Integer.toString(i)+")");
		}
		for (int i=1; i<=400; i++) {
			Noticias.add(readFile(String.format("src/Noticias/sport/%03d.txt", i)));
			Noticias.get(i-1+400).setnomeficheiro("s..." + Integer.toString(i));
		}
		for (int i=1; i<=400; i++) {
			Noticias.add(readFile(String.format("src/Noticias/politics/%03d.txt", i)));
			Noticias.get(i-1+800).setnomeficheiro("p..." + Integer.toString(i));
		}
		for (int i=1; i<=400; i++) {
			Noticias.add(readFile(String.format("src/Noticias/business/%03d.txt", i)));
			Noticias.get(i-1+1200).setnomeficheiro("b..." + Integer.toString(i));
		}
		for (int i=1; i<=386; i++) {
			Noticias.add(readFile(String.format("src/Noticias/entertainment/%03d.txt", i)));
			Noticias.get(i-1+1600).setnomeficheiro("e..." + Integer.toString(i));
		}
		return Noticias;
	}
}

