package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean check;
	private boolean checkMate;

	private List<PecaDeXadrez> pecasNoTabuleiro = new ArrayList<>();
	private List<PecaDeXadrez> pecasCapturadas = new ArrayList<>();

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++)
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		return mat;
	}

	public boolean[][] posiveisMovimentos(PosicaoXadrez posicaoOrigem) {
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

		if (testeCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturaDePeca);
			throw new XadrezExcecao("Voce nao pode se colocar em cheque");
		}

		check = (testeCheque(oponente(jogadorAtual))) ? true : false;

		if (testeChequeMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		proximoTurno();
		return (PecaDeXadrez) capturaDePeca;
	}

	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturaDePeca = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDePeca(p, destino);
		if (capturaDePeca != null) {
			pecasNoTabuleiro.remove(capturaDePeca);
			capturaDePeca.add(capturaDePeca);
		}
		return capturaDePeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturaDePeca) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.lugarDePeca(p, origem);

		if (capturaDePeca != null) {
			tabuleiro.lugarDePeca(capturaDePeca, destino);
			pecasCapturadas.remove(capturaDePeca);
			pecasNoTabuleiro.add((PecaDeXadrez) capturaDePeca);
		}
	}

	private void validarPosicaoDeOrigem(Posicao posicao) {
		if (!tabuleiro.TemUmaPeca(posicao)) {
			throw new XadrezExcecao("Não tem peca nessa posição");
		}
		if (jogadorAtual != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peca escolhida nao e sua!");
		}
		if (!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Nao existe movimentos possiveis para a peca escolhida");
		}
	}

	private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possiveilMovimento(destino)) {
			throw new XadrezExcecao("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? cor.PRETO : cor.BRANCO;
	}

	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor" + cor + "no tabulero");
	}

	private boolean testeCheque(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasAdversarias = pecasNoTabuleiro.stream()
				.filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasAdversarias) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeChequeMate(Cor cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getLinhas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaDeXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca capturaDePeca = realizarMovimento(origem, destino);
						boolean testeCheck = testeCheque(cor);
						desfazerMovimento(origem, destino, capturaDePeca);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	private void ColoqueNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarDePeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void iniciarPartida() {
		ColoqueNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColoqueNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));

		ColoqueNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
		ColoqueNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));

	}

}
