package application;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();

		while (true) {
			UI.printTabuleiro(partida.getPecas());
			System.out.println();
			System.out.print("Digite a posiçãode origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Digite a posição de destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
			PecaDeXadrez capturaDePeca = partida.executarMovimentoDeXadrez(origem, destino);
		}
		
	}
		
}
