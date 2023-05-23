package visao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import modelo.Tabuleiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PainelTabuleiroTest {

  private PainelTabuleiro painelTabuleiro;
  private Tabuleiro tabuleiro;

  @BeforeEach
  void setUp() {
    this.tabuleiro = new Tabuleiro(10, 10, 10);
    this.painelTabuleiro = new PainelTabuleiro(tabuleiro);
  }

  @Test
  void testConstrutor() {
    assertNotNull(painelTabuleiro);
  }

}