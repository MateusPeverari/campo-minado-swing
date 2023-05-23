package visao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.io.Serializable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import modelo.Tabuleiro;

/**
 * Esta classe representa o painel do tabuleiro do jogo. <br>
 * É responsável por exibir o tabuleiro com botões clicáveis e um cronômetro. <br>
 * Também registra um observador para o tabuleiro, que é notificado quando o jogo é encerrado.
 */
public class PainelTabuleiro extends JPanel implements Serializable {
  @Serial
  private static final long serialVersionUID = 1905122041950251207L;
  private final transient Tabuleiro tabuleiro;
  private Timer timer;
  private int tempo = 0;
  private static final String CRONOMETRO = "Tempo: 0";

  /**
   * Construtor da classe PainelTabuleiro.
   *
   * @param tabuleiro o tabuleiro do jogo da mina
   */
  public PainelTabuleiro(Tabuleiro tabuleiro) {
    this.tabuleiro = tabuleiro;

    setLayout(new BorderLayout());

    JMenuBar menuBar = new JMenuBar();
    add(menuBar, BorderLayout.NORTH);
    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

    JMenu timerMenu = new JMenu(CRONOMETRO);
    menuBar.add(timerMenu);

    criarTimer(timerMenu);

    JMenuItem reiniciarItem = new JMenuItem("Reiniciar");
    menuBar.add(reiniciarItem);
    gerarAcaoReiniciar(reiniciarItem, timerMenu);

    menuBar.add(Box.createHorizontalGlue());

    JMenuItem voltarItem = new JMenuItem("Menu Inicial");
    menuBar.add(voltarItem);
    gerarAcaoVoltar(voltarItem);

    menuBar.add(Box.createHorizontalGlue());

    JMenuItem sairItem = new JMenuItem("Fechar");
    menuBar.add(sairItem);
    gerarAcaoSair(sairItem);

    JPanel campoPanel = new JPanel(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
    tabuleiro.paraCadaCampo(c -> {
      boolean cor = (c.getLinha() % 2 != 0) == (c.getColuna() % 2 == 0);
      campoPanel.add(new BotaoCampo(c, cor));
    });


    add(campoPanel, BorderLayout.CENTER);

    tabuleiro.registrarObservador(e ->
        SwingUtilities.invokeLater(() -> {
          if (e.isGanhou()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Ganhou :) \n Tempo: " + tempo + " segundos");
          } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Perdeu :(");
          }

          timer.stop();
          tempo = 0;
          timerMenu.setText(CRONOMETRO);

          tabuleiro.reiniciar();
          timer.start();
        }));
  }

  private void criarTimer(JMenu timerMenu) {
    timerMenu.setEnabled(false);

    timer = new Timer(1000, e -> {
      tempo++;
      timerMenu.setText("Tempo: " + tempo);
    });

    timer.start();
  }

  private void gerarAcaoReiniciar(JMenuItem reiniciarItem, JMenu timerMenu) {
    reiniciarItem.addActionListener(e -> {
      timer.stop();
      tempo = 0;
      timerMenu.setText(CRONOMETRO);

      tabuleiro.reiniciar();
      timer.start();
    });
  }

  private void gerarAcaoVoltar(JMenuItem voltarItem) {
    voltarItem.addActionListener(e -> {
      JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PainelTabuleiro.this);
      frame.dispose();
      TelaInicial.iniciar();
    });
  }

  private void gerarAcaoSair(JMenuItem sairItem) {
    sairItem.addActionListener(e -> System.exit(0));
  }
}
