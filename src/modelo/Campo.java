package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um campo do jogo "Campo Minado".
 */
public class Campo {

  private final int linha;
  private final int coluna;

  private boolean aberto = false;
  private boolean minado = false;
  private boolean marcado = false;

  private List<Campo> vizinhos = new ArrayList<>();
  private List<CampoObservador> observadores = new ArrayList<>();

  public Campo(int linha, int coluna) {
    this.linha = linha;
    this.coluna = coluna;
  }

  public void registrarObservador(CampoObservador observador) {
    observadores.add(observador);
  }

  private void notificarObservadores(CampoEvento evento) {
    observadores
        .forEach(o -> o.eventoOcorreu(this, evento));
  }


  /**
   * Adiciona um campo vizinho ao campo atual, se for um vizinho válido.
   *
   * @param vizinho o campo vizinho a ser adicionado
   * @return true se o vizinho foi adicionado com sucesso, false caso contrário
   */
  public boolean adicionarVizinho(Campo vizinho) {
    boolean linhaDiferente = linha != vizinho.linha;
    boolean colunaDiferente = coluna != vizinho.coluna;
    boolean diagonal = linhaDiferente && colunaDiferente;

    int deltaLinha = Math.abs(linha - vizinho.linha);
    int deltaColuna = Math.abs(coluna - vizinho.coluna);
    int detalGeral = deltaColuna + deltaLinha;

    boolean adicionaVizinho = (detalGeral == 1 && !diagonal) || (detalGeral == 2 && diagonal);
    if (adicionaVizinho) {
      vizinhos.add(vizinho);
    }
    return adicionaVizinho;
  }

  /**
   * Alterna a marcação do campo, marcando-o se não estiver marcado ou
   * desmarcando-o se já estiver marcado. <br>
   * O campo só pode ser marcado se não estiver aberto.
   */
  public void alternarMarcacao() {
    if (!aberto) {
      marcado = !marcado;

      if (marcado) {
        notificarObservadores(CampoEvento.MARCAR);
      } else {
        notificarObservadores(CampoEvento.DESMARCAR);
      }
    }
  }

  /**
   * Abre o campo.
   *
   * @return true se o campo estiver com mina, false caso contrário.
   */
  public boolean abrir() {

    if (!aberto && !marcado) {
      if (minado) {
        notificarObservadores(CampoEvento.EXPLODIR);
        return true;
      }

      setAberto(true);

      if (vizinhancaSegura()) {
        vizinhos.forEach(Campo::abrir);
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean vizinhancaSegura() {
    return vizinhos.stream().noneMatch(v -> v.minado);
  }

  public void minar() {
    minado = true;
  }

  public boolean isMinado() {
    return minado;
  }

  public boolean isMarcado() {
    return marcado;
  }

  void setAberto(boolean aberto) {
    this.aberto = aberto;

    if (aberto) {
      notificarObservadores(CampoEvento.ABRIR);
    }
  }

  public boolean isAberto() {
    return aberto;
  }

  public int getLinha() {
    return linha;
  }

  public int getColuna() {
    return coluna;
  }

  boolean objetivoAlcancado() {
    boolean desvendado = !minado && aberto;
    boolean protegido = minado && marcado;
    return desvendado || protegido;
  }

  public int minasNaVizinhanca() {
    return (int) vizinhos.stream().filter(v -> v.minado).count();
  }

  void reiniciar() {
    aberto = false;
    minado = false;
    marcado = false;
    notificarObservadores(CampoEvento.REINICIAR);
  }
}
