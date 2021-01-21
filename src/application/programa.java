package application;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PartidaDeXadrez;

public class programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	PartidaDeXadrez partida = new PartidaDeXadrez();
	
	UI.printTabuleiro(partida.getPecas());
		
	}

}
