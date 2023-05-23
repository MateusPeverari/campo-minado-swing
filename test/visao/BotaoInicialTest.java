package visao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class BotaoInicialTest {
  private BotaoInicial botao;

  @BeforeEach
  public void setup() {
    botao = new BotaoInicial("Teste");
  }

  @Test
  void testConstructor() {
    assertEquals("Teste", botao.getText());
    assertFalse(botao.isContentAreaFilled());
    assertEquals(Color.WHITE, botao.getForeground());
    assertNotNull(botao.getCursor());
    assertNotNull(botao.getBorder());
    assertEquals(Color.decode("#020054"), botao.getCor1());
    assertEquals(Color.decode("#001aa0"), botao.getCor2());
    assertEquals(1f, botao.getSizeSpeed());
  }

  @Test
  void testSetColor1() {
    botao.setCor1(Color.RED);
    assertEquals(Color.RED, botao.getCor1());
  }

  @Test
  void testSetColor2() {
    botao.setCor2(Color.GREEN);
    assertEquals(Color.GREEN, botao.getCor2());
  }

  @Test
  void testSetSizeSpeed() {
    botao.setSizeSpeed(0.5f);
    assertEquals(0.5f, botao.getSizeSpeed());
  }

  @Test
  void testPaintComponent() {
    JButton botao = new BotaoInicial("Teste");
    botao.setSize(100, 50);
    botao.paintImmediately(0, 0, botao.getWidth(), botao.getHeight());

    assertEquals(100, botao.getWidth());
    assertEquals(50, botao.getHeight());
  }

  @Test
  void testCreateStyle() {
    Graphics2D g2 = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
    BotaoInicial botao = new BotaoInicial("Teste");

    botao.createStyle(g2);

    Composite composite = g2.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    assertEquals(AlphaComposite.SRC_ATOP, ((AlphaComposite) composite).getRule());

    Paint paint = g2.getPaint();
    assertTrue(paint instanceof GradientPaint);
    GradientPaint gradientPaint = (GradientPaint) paint;
    assertEquals(new Color(255, 255, 255), gradientPaint.getColor1());
  }
}