package visao;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import modelo.Tabuleiro;

/**
 * A classe TelaInicial representa a tela principal do jogo Campo Minado. A partir desta tela,
 * o usuário pode escolher o nível do jogo ou personalizá-lo com valores próprios.
 */
public class TelaInicial extends JFrame {

  private JLabel lblTitulo;
  BotaoInicial[] btnsNiveis;
  BotaoInicial btnPersonalizado;
  private BotaoInicial btnSair;
  private static final String ERRO_ENTRADA = "Erro de Entrada";

  /**
   * Cria uma instância da classe TelaInicial.<br>
   * Este construtor cria os componentes da tela inicial, configura o layout,
   * os listeners dos botões e a janela.
   */
  public TelaInicial() {
    criarComponentes();
    configurarLayout();
    configurarListeners();
    configurarJanela();
  }

  public static void iniciar() {
    main(null);
  }

  private void criarComponentes() {
    lblTitulo = new JLabel("Campo Minado", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

    btnsNiveis = new BotaoInicial[] {
        new BotaoInicial("Fácil"),
        new BotaoInicial("Médio"),
        new BotaoInicial("Difícil")
    };

    btnPersonalizado = new BotaoInicial("Personalizado");
    btnSair = new BotaoInicial("Sair");
  }

  private void configurarLayout() {
    setLayout(new BorderLayout());
    add(lblTitulo, BorderLayout.NORTH);
    JPanel centroPanel = new JPanel(new GridLayout(5, 1, 10, 10));
    centroPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    Arrays.stream(btnsNiveis).forEach(centroPanel::add);
    centroPanel.add(btnPersonalizado);
    centroPanel.add(btnSair);
    add(centroPanel, BorderLayout.CENTER);
  }


  private void configurarListeners() {
    btnPersonalizado.addActionListener(e -> criarJogoPersonalizado());

    for (int i = 0; i < btnsNiveis.length; i++) {
      final int nivel = i;
      btnsNiveis[i].addActionListener(e -> criarJogoPorNivel(nivel));
    }

    btnSair.addActionListener(e -> System.exit(0));
  }

  void criarJogoPersonalizado() {
    JFrame frameJogo = new JFrame();
    frameJogo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    exibirTabuleiroPersonalizado(frameJogo);
  }

  void criarJogoPorNivel(int nivel) {
    JFrame frameJogo = new JFrame();
    frameJogo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    int[][] niveis = {{9, 9, 10}, {16, 16, 40}, {16, 30, 99}};

    if (nivel < 0 || nivel >= niveis.length) {
      throw new IllegalArgumentException("Nível inválido: " + nivel);
    }

    int linhas = niveis[nivel][0];
    int colunas = niveis[nivel][1];
    int bombas = niveis[nivel][2];

    criarTabuleiro(frameJogo, linhas, colunas, bombas);
  }

  private void criarTabuleiro(JFrame frame, int linhas, int colunas, int bombas) {
    Tabuleiro tabuleiro = new Tabuleiro(linhas, colunas, bombas);
    exibirTabuleiro(frame, tabuleiro, linhas, colunas);
  }

  int obterNumeroInteiro(String mensagem, int valorMinimo) {
    int valor = -1;
    boolean entradaValida = false;

    while (!entradaValida) {
      String input = JOptionPane.showInputDialog(null, mensagem);

      if (input == null) {
        break;
      }

      try {
        valor = Integer.parseInt(input);

        if (valor >= valorMinimo) {
          entradaValida = true;
        } else {
          JOptionPane.showMessageDialog(null,
              "Valor inválido. O valor mínimo é " + valorMinimo + ".",
              ERRO_ENTRADA, JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,
            "Por favor, informe um valor numérico válido.",
            ERRO_ENTRADA, JOptionPane.ERROR_MESSAGE);
      }
    }
    return valor;
  }

  int validarBombas(int linhas, int colunas) {
    boolean entradaValida = false;
    int maxBombas = (linhas * colunas) / 2;
    int numBombas = 0;

    while (!entradaValida) {
      numBombas = obterNumeroInteiro("Informe o número de bombas:", 1);
      if (numBombas <= maxBombas) {
        entradaValida = true;
      } else {
        JOptionPane.showMessageDialog(null,
            "Número de bombas inválido. O número máximo é " + maxBombas + ".",
            ERRO_ENTRADA, JOptionPane.ERROR_MESSAGE);
      }
    }
    return numBombas;
  }

  private void exibirTabuleiroPersonalizado(JFrame frame) {
    int linhas = obterNumeroInteiro("Informe o número de linhas:", 3);
    int colunas = obterNumeroInteiro("Informe o número de colunas:", 3);
    int bombas = validarBombas(linhas, colunas);

    Tabuleiro tabuleiro = new Tabuleiro(linhas, colunas, bombas);
    exibirTabuleiro(frame, tabuleiro, linhas, colunas);
  }


  private void exibirTabuleiro(JFrame frame, Tabuleiro tabuleiro, int linhas, int colunas) {
    int largura = Math.max((linhas * 50), 400);
    int altura = Math.max((colunas * 50), 400);
    frame.setSize(largura, altura);
    frame.setLocationRelativeTo(null);
    frame.add(new PainelTabuleiro(tabuleiro));
    frame.setVisible(true);
    dispose();
  }


  private void configurarJanela() {
    setTitle("Campo Minado");
    setSize(300, 200);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
  }

  /**
   * Inicia a aplicação.<br>
   * Este método é o ponto de entrada da aplicação e é responsável por criar
   * uma instância da classe TelaInicial e exibi-la.
   *
   * @param args os argumentos da linha de comando (não são usados nesta aplicação).
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      TelaInicial telaInicial = new TelaInicial();
      telaInicial.setVisible(true);
    });
  }
}
