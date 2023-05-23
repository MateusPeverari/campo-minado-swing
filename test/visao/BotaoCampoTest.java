package visao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import modelo.Campo;
import modelo.CampoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BotaoCampoTest {

  private Campo campo;
  private BotaoCampo botaoCampo;

  @BeforeEach
  public void inicializar() {
    campo = new Campo(0, 0);
    botaoCampo = new BotaoCampo(campo, true);

  }

  @Test
  void testBotaoCampoPadrao() {
    assertEquals(new Color(163, 208, 87), botaoCampo.getBackground());
    assertEquals("", botaoCampo.getText());
    assertFalse(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampoAberto() {
    // WHEN
    campo.abrir();

    // THEN
    assertEquals(new Color(216, 216, 216), botaoCampo.getBackground());
    assertEquals("", botaoCampo.getText());
    assertTrue(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampoMinado() {
    // GIVEN
    campo.minar();

    // WHEN
    botaoCampo.eventoOcorreu(campo, CampoEvento.EXPLODIR);

    // THEN
    assertEquals(new Color(189, 66, 68), botaoCampo.getBackground());
    assertEquals("X", botaoCampo.getText());
  }

  @Test
  void testBotaoCampoMarcado() {
    // WHEN
    campo.alternarMarcacao();

    // THEN
    assertEquals(new Color(8, 179, 247), botaoCampo.getBackground());
    assertEquals("M", botaoCampo.getText());
    assertFalse(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampoDesmarcado() {
    // GIVEN
    campo.alternarMarcacao();

    // WHEN
    campo.alternarMarcacao();

    // THEN
    assertEquals(new Color(163, 208, 87), botaoCampo.getBackground());
    assertEquals("", botaoCampo.getText());
    assertFalse(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampo1Vizinho() {
    // GIVEN
    var campo1 = new Campo(1, 0);
    campo1.minar();
    campo.adicionarVizinho(campo1);

    // WHEN
    botaoCampo.eventoOcorreu(campo, CampoEvento.ABRIR);

    // THEN
    assertEquals("1", botaoCampo.getText());
    assertEquals(new Color(0, 100, 0), botaoCampo.getForeground());
    assertEquals(new Color(216, 216, 216), botaoCampo.getBackground());
    assertTrue(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampo2Vizinhos() {
    // GIVEN
    var campo1 = new Campo(1, 0);
    campo1.minar();
    campo.adicionarVizinho(campo1);
    var campo2 = new Campo(0, 1);
    campo2.minar();
    campo.adicionarVizinho(campo2);

    // WHEN
    botaoCampo.eventoOcorreu(campo, CampoEvento.ABRIR);

    // THEN
    assertEquals("2", botaoCampo.getText());
    assertEquals(Color.BLUE, botaoCampo.getForeground());
    assertEquals(new Color(216, 216, 216), botaoCampo.getBackground());
    assertTrue(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampo3Vizinhos() {
    // GIVEN
    var campo1 = new Campo(1, 0);
    campo1.minar();
    campo.adicionarVizinho(campo1);

    var campo2 = new Campo(0, 1);
    campo2.minar();
    campo.adicionarVizinho(campo2);

    var campo3 = new Campo(1, 1);
    campo3.minar();
    campo.adicionarVizinho(campo3);

    // WHEN
    botaoCampo.eventoOcorreu(campo, CampoEvento.ABRIR);

    // THEN
    assertEquals("3", botaoCampo.getText());
    assertEquals(Color.YELLOW, botaoCampo.getForeground());
    assertEquals(new Color(216, 216, 216), botaoCampo.getBackground());
    assertTrue(botaoCampo.getBorder().isBorderOpaque());
  }

  @Test
  void testBotaoCampo4Vizinhos() {
    // GIVEN
    var campo1 = new Campo(1, 0);
    campo1.minar();
    campo.adicionarVizinho(campo1);

    var campo2 = new Campo(0, 1);
    campo2.minar();
    campo.adicionarVizinho(campo2);

    var campo3 = new Campo(1, 1);
    campo3.minar();
    campo.adicionarVizinho(campo3);

    var campo4 = new Campo(0, 1);
    campo4.minar();
    campo.adicionarVizinho(campo4);

    // WHEN
    botaoCampo.eventoOcorreu(campo, CampoEvento.ABRIR);

    // THEN
    assertEquals("4", botaoCampo.getText());
    assertEquals(Color.RED, botaoCampo.getForeground());
    assertEquals(new Color(216, 216, 216), botaoCampo.getBackground());
    assertTrue(botaoCampo.getBorder().isBorderOpaque());
  }

}