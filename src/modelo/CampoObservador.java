package modelo;

/**
 * Interface responsável por definir o método que será
 * executado quando um evento ocorrer em um campo.
 */
@FunctionalInterface
public interface CampoObservador {
  void eventoOcorreu(Campo campo, CampoEvento evento);
}
