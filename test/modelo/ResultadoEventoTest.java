package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultadoEventoTest {

  @Test
  void testIsGanhou() {
    ResultadoEvento resultadoEvento = new ResultadoEvento(true);
    assertEquals(true, resultadoEvento.isGanhou());

    resultadoEvento = new ResultadoEvento(false);
    assertEquals(false, resultadoEvento.isGanhou());
  }

}
