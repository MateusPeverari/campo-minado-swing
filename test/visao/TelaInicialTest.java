package visao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TelaInicialTest {

  private TelaInicial telaInicial;

  @BeforeEach
  void setUp() {
    telaInicial = new TelaInicial();
  }

  @Test
  void criarJogoFacil() {
    telaInicial.criarJogoPorNivel(0);
    assertEquals(200, telaInicial.getHeight());
    assertEquals(300, telaInicial.getWidth());
  }

  @Test
  void criarJogoNivelInvalido() {
    assertThrows(IllegalArgumentException.class, () -> {
      telaInicial.criarJogoPorNivel(3);
    });
  }
}

