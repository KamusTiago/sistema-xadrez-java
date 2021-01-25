package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class programa {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		List<PecaDeXadrez> capturada = new ArrayList<>();

		while (!partida.getCheckMate()) {
			try {
				UI.limparTela();
				UI.printPartida(partida,capturada);
				System.out.println();
				System.out.print("Digite a posiçãode origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] possiveisMovimentos = partida.posiveisMovimentos(origem);
				UI.limparTela();
				UI.printTabuleiro(partida.getPecas(),possiveisMovimentos);
				
				System.out.println();
				System.out.print("Digite a posição de destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
			
				PecaDeXadrez capturaDePeca = partida.executarMovimentoDeXadrez(origem, destino);
				if(capturaDePeca != null) {
					capturada.add(capturaDePeca);
				}
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
		UI.limparTela();
		UI.printPartida(partida, capturada);
	}
		
}
