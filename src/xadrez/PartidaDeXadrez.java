package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
			tabuleiro = new Tabuleiro(8,8);
			iniciarPartida();
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][ tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++)
			for (int j = 0; j < tabuleiro.getColunas(); j ++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i, j);
			}
		return mat;
	}
	
	private void ColoqueNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDePeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void iniciarPartida() {
		ColoqueNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
	}
	
	
}
