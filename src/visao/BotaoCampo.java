package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

/**
 * Representa um botão que pode ser clicado para interagir com um campo do jogo Campo Minado. <br>
 * O botão pode exibir diferentes estilos conforme o estado do campo
 * (aberto, marcado, explodido, etc.).<br>
 * Implementa a interface CampoObservador para receber mudanças no estado do campo.<br>
 * Implementa a interface MouseListener para lidar com eventos do mouse
 * (clique, pressionamento, etc.).
 */
public class BotaoCampo extends JButton
    implements CampoObservador, MouseListener {

  private final Color bgPadrao = new Color(163, 208, 87);
  private final Color bgPadrao2 = new Color(140, 176, 78);
  private final Color bgAberto = new Color(216, 216, 216);
  private final Color bgMarcar = new Color(8, 179, 247);
  private final Color bgExplodir = new Color(189, 66, 68);
  private final Color textoVerde = new Color(0, 100, 0);

  private final transient Campo campo;
  private final Color bgAtual;

  /**
   * Cria um BotaoCampo associado a um campo do jogo Campo Minado.
   * Configura a aparência padrão do botão e registra o botão como um observador do campo.
   *
   * @param campo O campo do jogo Campo Minado associado a este botão.
   */
  public BotaoCampo(Campo campo, boolean alternarCor) {
    this.campo = campo;
    this.bgAtual = alternarCor ? bgPadrao : bgPadrao2;
    setBackground(bgAtual);
    setOpaque(true);
    setBorder(BorderFactory.createEmptyBorder());

    addMouseListener(this);
    campo.registrarObservador(this);
  }

  @Override
  public void eventoOcorreu(Campo campo, CampoEvento evento) {
    switch (evento) {
      case ABRIR -> aplicarEstiloAbrir();
      case MARCAR -> aplicarEstiloMarcar();
      case EXPLODIR -> aplicarEstiloExplodir();
      default -> aplicarEstiloPadrao();
    }

    SwingUtilities.invokeLater(() -> {
      repaint();
      validate();
    });
  }

  private void aplicarEstiloPadrao() {
    setBackground(bgAtual);
    setBorder(BorderFactory.createEmptyBorder());
    setText("");
  }

  private void aplicarEstiloExplodir() {
    setBackground(bgExplodir);
    setForeground(Color.WHITE);
    setText("X");
  }

  private void aplicarEstiloMarcar() {
    setBackground(bgMarcar);
    setForeground(Color.BLACK);
    setText("M");
  }

  private void aplicarEstiloAbrir() {

    setBorder(BorderFactory.createLineBorder(Color.GRAY));

    if (campo.isMinado()) {
      setBackground(bgExplodir);
      return;
    }

    setBackground(bgAberto);

    switch (campo.minasNaVizinhanca()) {
      case 1 -> setForeground(textoVerde);
      case 2 -> setForeground(Color.BLUE);
      case 3 -> setForeground(Color.YELLOW);
      case 4, 5, 6 -> setForeground(Color.RED);
      default -> setForeground(Color.PINK);
    }

    String valor = !campo.vizinhancaSegura()
        ?
        campo.minasNaVizinhanca() + "" : "";
    setText(valor);
  }

  // Interface dos eventos do Mouse
  @Override
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == 1) {
      campo.abrir();
    } else {
      campo.alternarMarcacao();
    }
  }

  public void mouseEntered(MouseEvent e) {
    // Interface dos eventos do Mouse
  }

  public void mouseClicked(MouseEvent e) {
    // Interface dos eventos do Mouse
  }

  public void mouseExited(MouseEvent e) {
    // Interface dos eventos do Mouse
  }

  public void mouseReleased(MouseEvent e) {
    // Interface dos eventos do Mouse
  }
}
