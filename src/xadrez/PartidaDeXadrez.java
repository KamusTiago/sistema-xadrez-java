package xadrez;

import java.util.ArrayList;
import java.util.List;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	
	private List<PecaDeXadrez> pecasNoTabuleiro = new ArrayList<>();
	private List<PecaDeXadrez> pecasCapturadas = new ArrayList<>();
	
	public PartidaDeXadrez() {
			tabuleiro = new Tabuleiro(8,8);
			turno = 1;
			jogadorAtual = Cor.BRANCO;
			iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][ tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++)
			for (int j = 0; j < tabuleiro.getColunas(); j ++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i, j);
			}
		return mat;
	}
	
	public boolean[][]posiveisMovimentos(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoDeOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	public PecaDeXadrez executarMovimentoDeXadrez(PosicaoXadrez posicaoDeOrigem, PosicaoXadrez posicaoDeDestino) {
		Posicao origem = posicaoDeOrigem.toPosicao();
		Posicao destino = posicaoDeDestino.toPosicao();
		validarPosicaoDeOrigem(origem);
		validarPosicaoDeDestino(origem, destino);
		Peca capturaDePeca = realizarMovimento(origem, destino);
		proximoTurno();
		return (PecaDeXadrez)capturaDePeca;
	}
	
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturaDePeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDePeca(p, destino);
		if(capturaDePeca != null) {
			pecasNoTabuleiro.remove(capturaDePeca);
			capturaDePeca.add(capturaDePeca);
		}
		return capturaDePeca;
	}
	
	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.TemUmaPeca(posicao)) {
			throw new XadrezExcecao("N�o tem peca nessa posi��o");
		}
		if(jogadorAtual !=((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peca escolhida nao e sua!");
		}
		if(!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()){
			throw new XadrezExcecao("Nao existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possiveilMovimento(destino)) {
			throw new XadrezExcecao("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private void ColoqueNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDePeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
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
