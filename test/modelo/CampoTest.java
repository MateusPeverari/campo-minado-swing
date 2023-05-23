package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampoTest {

  private Campo campo;
  private Campo vizinho1;
  private Campo vizinho2;

  @BeforeEach
  void setUp() {
    campo = new Campo(1, 1);
    vizinho1 = new Campo(1, 2);
    vizinho2 = new Campo(2, 2);

    campo.adicionarVizinho(vizinho1);
    campo.adicionarVizinho(vizinho2);
  }

  @Test
  void testAdicionarVizinho() {
    // GIVEN
    boolean resultado1 = campo.adicionarVizinho(vizinho1);
    boolean resultado2 = campo.adicionarVizinho(vizinho2);

    // GIVEN
    assertTrue(resultado1);
    assertTrue(resultado2);
  }

  @Test
  void testAdicionarVizinhoDistancia2() {
    // GIVEN
    boolean resultado1 = campo.adicionarVizinho(vizinho2);
    boolean resultado2 = campo.adicionarVizinho(new Campo(1, 3));

    // THEN
    assertTrue(resultado1);
    assertFalse(resultado2);
  }

  @Test
  void testAlternarMarcacao() {
    assertFalse(campo.isMarcado());
    campo.alternarMarcacao();

    assertTrue(campo.isMarcado());
    campo.alternarMarcacao();

    assertFalse(campo.isMarcado());
  }

  @Test
  void testAlternarMarcacaoAberto() {
    // GIVEN
    campo.setAberto(true);

    // WHEN
    campo.alternarMarcacao();

    // THEN
    assertTrue(campo.isAberto());
  }

  @Test
  void testAbrirNaoMinadoNaoMarcado() {
    assertTrue(campo.abrir());
    assertTrue(campo.isAberto());
  }

  @Test
  void testAbrirNaoMinadoMarcado() {
    // GIVEN
    campo.alternarMarcacao();

    // THEN
    assertFalse(campo.abrir());
    assertFalse(campo.isAberto());
  }

  @Test
  void testAbrirMinadoMarcado() {
    // GIVEN
    campo.alternarMarcacao();

    // WHEN
    campo.minar();

    // THEN
    assertFalse(campo.abrir());
    assertFalse(campo.isAberto());
  }

  @Test
  void testAbrirComVizinhos() {
    // GIVEN
    campo.abrir();
    vizinho1.minar();
    vizinho2.abrir();

    // WHEN
    boolean resultAberto = campo.isAberto();
    boolean resultVizinho1Minado = vizinho1.isMinado();
    boolean resultVizinho2Aberto = vizinho2.isAberto();

    // THEN
    assertTrue(resultAberto);
    assertTrue(resultVizinho1Minado);
    assertTrue(resultVizinho2Aberto);
  }


  @Test
  void testVizinhacaSegura() {
    // GIVEN
    vizinho1.minar();
    vizinho2.minar();

    // WHEN
    boolean result = campo.vizinhancaSegura();

    // THEN
    assertFalse(result);
  }

  @Test
  void testVizinhacaSeguraComVizinhosSeguros() {
    // WHEN
    boolean result = campo.vizinhancaSegura();

    // THEN
    assertTrue(result);
  }

  @Test
  void testVizinhacaSeguraComVizinhosMinadosMasMarcados() {
    // GIVEN
    vizinho1.minar();
    vizinho1.alternarMarcacao();
    vizinho2.minar();
    vizinho2.alternarMarcacao();

    // WHEN
    boolean result = campo.vizinhancaSegura();

    // THEN
    assertFalse(result);
  }

  @Test
  void testVizinhacaSeguraComVizinhosMinadosMasNaoMarcados() {
    // GIVEN
    vizinho1.minar();
    vizinho2.minar();

    // WHEN
    boolean result = campo.vizinhancaSegura();

    // THEN
    assertFalse(result);
  }

  @Test
  void testObjetivoAlcancadoDesvendado() {
    // GIVEN
    campo.abrir();

    // WHEN
    boolean result = campo.objetivoAlcancado();

    // THEN
    assertTrue(result);
  }

  @Test
  void testObjetivoAlcancadoProtegido() {
    // GIVEN
    campo.minar();

    // WHEN
    campo.alternarMarcacao();

    // THEN
    assertTrue(campo.objetivoAlcancado());
  }

  @Test
  void testObjetivoNaoAlcancado() {
    assertFalse(campo.objetivoAlcancado());
  }

  @Test
  void testMinasNaVizinhanca() {
    // GIVEN
    vizinho1.minar();
    vizinho2.minar();

    // THEN
    assertEquals(2, campo.minasNaVizinhanca());
  }

}
