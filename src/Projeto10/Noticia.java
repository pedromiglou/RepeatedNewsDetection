package Projeto10;
//93321 Luis Pereira
//93283 Pedro Amaral

import java.util.ArrayList;
import java.util.Collections;

public class Noticia {
	
	private ArrayList<String> titulo = new ArrayList<String>();
	private ArrayList<String> conteudo = new ArrayList<String>();
	private String nomeficheiro;
	
	public Noticia(String titulo, String conteudo) {
		Collections.addAll(this.titulo,titulo.toLowerCase().split(" "));
		Collections.addAll(this.conteudo,conteudo.toLowerCase().split(" "));
	}

	public ArrayList<String> getTitulo() {
		return titulo;
	}
	public void setTitulo(ArrayList<String> titulo) {
		this.titulo = titulo;
	}
	public String getTituloString() {
		String title = "";
		for (int i=0;i<titulo.size(); i++) {
			title += titulo.get(i) + " ";
		}
		return title;
	}

	public ArrayList<String> getConteudo() {
		return conteudo;
	}
	public String getnomeficheiro() {
		return this.nomeficheiro;
	}
	public void setnomeficheiro(String s) {
		this.nomeficheiro=s;
	}

	@Override
	public String toString() {
		return "Noticia [titulo=" + titulo + "]";
	}
	
	

}
