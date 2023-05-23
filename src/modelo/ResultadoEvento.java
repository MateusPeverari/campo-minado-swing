package modelo;

/**
 * Classe responsável por armazenar o resultado de um evento.
 */
public class ResultadoEvento {

  private final boolean ganhou;

  public ResultadoEvento(boolean ganhou) {
    this.ganhou = ganhou;
  }

  public boolean isGanhou() {
    return ganhou;
  }
}
