package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Classe responsável por representar um Tabuleiro do jogo "Campo Minado". <br>
 * Possui métodos para gerar os campos, sortear as minas, associar vizinhos, abrir e
 * marcar campos, verificar se o objetivo foi alcançado e reiniciar o jogo. <br>
 * Também implementa a interface CampoObservador para receber notificações de eventos
 * ocorridos nos seus campos.
 */
public class Tabuleiro implements CampoObservador {

  private final int linhas;
  private final int colunas;
  private final int minas;
  private final List<Campo> campos = new ArrayList<>();
  private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
  private final Random random = new Random();

  /**
   * Construtor da classe Tabuleiro.
   *
   * @param linhas  Número de linhas do tabuleiro.
   * @param colunas Número de colunas do tabuleiro.
   * @param minas   Número de minas no tabuleiro.
   */
  public Tabuleiro(int linhas, int colunas, int minas) {
    this.linhas = linhas;
    this.colunas = colunas;
    this.minas = minas;

    gerarCampos();
    associarVizinhos();
    sortearMinas();
  }

  public void paraCadaCampo(Consumer<Campo> funcao) {
    campos.forEach(funcao);
  }

  public void registrarObservador(Consumer<ResultadoEvento> observador) {
    observadores.add(observador);
  }

  private void notificarObservadores(boolean resultado) {
    observadores.forEach(o -> o.accept(new ResultadoEvento(resultado)));
  }

  /**
   * Abre um campo no tabuleiro.
   *
   * @param linha  Linha do campo a ser aberto.
   * @param coluna Coluna do campo a ser aberto.
   */
  public void abrir(int linha, int coluna) {
    campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
        .findFirst()
        .ifPresent(Campo::abrir);
  }

  /**
   * Alterna a marcação de um campo no tabuleiro.
   *
   * @param linha  Linha do campo a ser marcado/desmarcado.
   * @param coluna Coluna do campo a ser marcado/desmarcado.
   */
  public void alternarMarcacao(int linha, int coluna) {
    campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
        .findFirst()
        .ifPresent(Campo::alternarMarcacao);
  }

  private void gerarCampos() {
    for (int linha = 0; linha < linhas; linha++) {
      for (int coluna = 0; coluna < colunas; coluna++) {
        Campo campo = new Campo(linha, coluna);
        campo.registrarObservador(this);
        campos.add(campo);
      }
    }
  }

  private void associarVizinhos() {
    for (Campo c1 : campos) {
      for (Campo c2 : campos) {
        c1.adicionarVizinho(c2);
      }
    }
  }

  private void sortearMinas() {
    long minasArmadas = 0;
    Predicate<Campo> minado = Campo::isMinado;

    do {
      int aleatorio = random.nextInt(campos.size());
      campos.get(aleatorio).minar();
      minasArmadas = campos.stream().filter(minado).count();
    } while (minasArmadas < minas);
  }

  public boolean objetivoAlcancado() {
    return campos.stream().allMatch(Campo::objetivoAlcancado);
  }

  public void reiniciar() {
    campos.forEach(Campo::reiniciar);
    sortearMinas();
  }

  public int getLinhas() {
    return linhas;
  }

  public int getColunas() {
    return colunas;
  }

  public List<Campo> getCampos() {
    return campos;
  }

  @Override
  public void eventoOcorreu(Campo campo, CampoEvento evento) {
    if (evento == CampoEvento.EXPLODIR) {
      mostrarMinas();
      notificarObservadores(false);
    } else if (objetivoAlcancado()) {
      notificarObservadores(true);
    }
  }

  private void mostrarMinas() {
    campos.stream()
        .filter(Campo::isMinado)
        .filter(c -> !c.isMarcado())
        .forEach(c -> c.setAberto(true));
  }
}
