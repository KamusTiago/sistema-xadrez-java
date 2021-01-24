package xadrez;

import tabuleiro.Peca;
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
	
	public PecaDeXadrez executarMovimentoDeXadrez(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDeDestino.toPosicao();
		validarPosicaoDeOrigem(origem);
		Peca capturaDePeca = realizarMovimento(origem, destino);
		return (PecaDeXadrez)capturaDePeca;
	}
	
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturaDePeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDePeca(p, destino);
		return capturaDePeca;
	}
	
	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.TemUmaPeca(posicao)) {
			throw new XadrezExcecao("Não tem peca nessa posição");
		}
		if(!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()){
			throw new XadrezExcecao("Nao existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void ColoqueNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDePeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void iniciarPartida() {
		ColoqueNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		ColoqueNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}
	
	
}
