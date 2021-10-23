package Projeto10;

import java.util.ArrayList;

// 93321 Luis Pereira
// 93283 Pedro Amaral

public class bloomFilter {
	
	private int[] filtro; //vetor de filtro com 0s e 1s
	private int size; // tamanho do vetor do filtro
	private int nelements; // numero de elementos que estao no filtro
	private int k; // numero de funcoes de dispersao
	
	public bloomFilter(int size, int m) {
		this.filtro = new int[size];
		this.size = size;
		this.nelements = 0;
		this.k = (int)(((double)size/m)*0.693);
	}
	
	public void addElement(String element) {
		for(int i = 0; i<k; i++) {
			element = element.concat(Integer.toString(i));
			this.filtro[Class1.string2hash(element)%size] = 1;
		}
		nelements++;
	}
	
	public boolean ismember(String element) {
		for(int i = 0; i<k;i++) {
			element = element.concat(Integer.toString(i));
			if(this.filtro[Class1.string2hash(element)%size]==0) {
				return false;
			}
		}
		return true;
	}
	
	public void addElements(ArrayList<String> elements) {
		for (int i = 0; i<elements.size(); i++) {
			addElement(elements.get(i));
		}
	}
	
	public int[] getFiltro() {
		return filtro;
	}

	public int getSize() {
		return size;
	}

	public int getNelements() {
		return nelements;
	}

	public int getK() {
		return k;
	}

}
