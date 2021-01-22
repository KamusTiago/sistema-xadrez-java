package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();

		while (true) {
			try {
				UI.limparTela();
				UI.printTabuleiro(partida.getPecas());
				System.out.println();
				System.out.print("Digite a posiçãode origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				System.out.println();
				System.out.print("Digite a posição de destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
				PecaDeXadrez capturaDePeca = partida.executarMovimentoDeXadrez(origem, destino);
			}
			catch(XadrezExcecao e) {
				System.out.print(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.print(e.getMessage());
				sc.nextLine();
			}
		}
		
	}
		
}
