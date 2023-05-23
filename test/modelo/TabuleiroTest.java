package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuleiroTest {

  private Tabuleiro tabuleiro;

  @BeforeEach
  void inicializarTabuleiro() {
    tabuleiro = new Tabuleiro(5, 5, 5);
  }

  @Test
  void testGerarCampos() {
    List<Campo> campos = tabuleiro.getCampos();
    assertEquals(25, campos.size());
  }

  @Test
  void testSortearMinas() {
    List<Campo> campos = tabuleiro.getCampos();
    long minas = campos.stream().filter(Campo::isMinado).count();
    assertEquals(5, minas);
  }

  @Test
  void testAlternarMarcacao() {
    Campo campo = tabuleiro.getCampos().get(0);
    assertFalse(campo.isMarcado());
    tabuleiro.alternarMarcacao(campo.getLinha(), campo.getColuna());
    assertTrue(campo.isMarcado());
    tabuleiro.alternarMarcacao(campo.getLinha(), campo.getColuna());
    assertFalse(campo.isMarcado());
  }

  @Test
  void testAbrirCampo() {
    Campo campo = tabuleiro.getCampos().get(0);
    assertFalse(campo.isAberto());
    tabuleiro.abrir(campo.getLinha(), campo.getColuna());
    assertTrue(campo.isAberto());
  }

  @Test
  void testObjetivoAlcancado() {
    List<Campo> campos = tabuleiro.getCampos();

    campos.stream()
        .filter(c -> !c.isMinado())
        .forEach(c -> c.setAberto(true));
    campos.stream()
        .filter(Campo::isMinado)
        .forEach(Campo::alternarMarcacao);

    assertTrue(tabuleiro.objetivoAlcancado());
  }

  @Test
  void testReiniciar() {
    List<Campo> campos = tabuleiro.getCampos();

    campos.stream()
        .filter(c -> !c.isMinado())
        .forEach(c -> c.setAberto(true));
    campos.stream()
        .filter(Campo::isMinado)
        .forEach(Campo::alternarMarcacao);

    assertTrue(tabuleiro.objetivoAlcancado());
    tabuleiro.reiniciar();
    assertFalse(tabuleiro.objetivoAlcancado());
    long minas = campos.stream().filter(Campo::isMinado).count();
    assertEquals(5, minas);
  }

  @Test
  void testMostrarMinas() {
    List<Campo> campos = tabuleiro.getCampos();

    Campo campoMinado = campos.stream()
        .filter(Campo::isMinado)
        .findFirst()
        .get();

    campoMinado.abrir();

    var minadosCount = campos.stream()
        .filter(Campo::isMinado)
        .count();

    var minadoAbertoCount = campos.stream()
        .filter(Campo::isMinado)
        .filter(Campo::isAberto)
        .count();

    assertEquals(minadosCount, minadoAbertoCount);
  }

}