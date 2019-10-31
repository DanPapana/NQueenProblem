import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

class MainFrame extends JFrame {

    private String[] sizeList = {"4", "5", "6", "8", "12"};
    private JComboBox sizeCombo = new JComboBox<>(sizeList);
    private ImageIcon queenImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Queen2.png")));
    private JPanel TheChessPanel = getSquares();
    private JPanel[][] squares;
    private int[] queen_array;
    private JPanel TheMainPanel = new JPanel();
    private JPanel TheButtonPanel = new JPanel();
    private JTextPane iteration_number = new JTextPane();
    private JTextPane start_temp = new JTextPane();
    private JTextPane cooling_factor = new JTextPane();
    private JTextPane beam_states_number = new JTextPane();
    private JTextPane beam_iteration_number = new JTextPane();

    MainFrame() {

        ///Making it bearable to the eyes
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        this.setTitle("The changing gardens of the world");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        final String HILL = "Hill climbing algorithm";
        final String ANNEALING = "Simulated Annealing";
        final String BEAM = "Local beam search";

        String[] algorithmList = {HILL, ANNEALING, BEAM};
        JComboBox algorithmCombo = new JComboBox<>(algorithmList);

        ///Combo Box for algorithms
        algorithmCombo.setPreferredSize(new Dimension(170, 20));
        sizeCombo.setPreferredSize(new Dimension(40, 20));

        JPanel AButtonPanel = new JPanel();
        AButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel algorithmPanel = new JPanel();
        algorithmPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        algorithmPanel.add(algorithmCombo);
        algorithmPanel.add(Box.createRigidArea(new Dimension(0, 130)));

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Dimension:"));
        sizePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sizePanel.add(sizeCombo);

        AButtonPanel.add(sizePanel);

        JPanel mainCardPanel = new JPanel();
        JPanel cardsPanel = new JPanel(new CardLayout());

        mainCardPanel.setLayout(new BoxLayout(mainCardPanel, BoxLayout.Y_AXIS));
        AButtonPanel.setLayout(new BoxLayout(AButtonPanel, BoxLayout.Y_AXIS));
        AButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel hillClimbingPanel = new JPanel();
        JPanel annealingPanel = new JPanel();
        JPanel beamPanel = new JPanel();
        JPanel geneticPanel = new JPanel();
        mainCardPanel.add(algorithmPanel);

        hillClimbingPanel.setLayout(new BoxLayout(hillClimbingPanel, BoxLayout.Y_AXIS));
        annealingPanel.setLayout(new BoxLayout(annealingPanel, BoxLayout.Y_AXIS));
        beamPanel.setLayout(new BoxLayout(beamPanel, BoxLayout.Y_AXIS));
        geneticPanel.setLayout(new BoxLayout(geneticPanel, BoxLayout.Y_AXIS));

        ///Hill Climbing Panel
        hillClimbingPanel.add(new JLabel(HILL));
        JPanel hillPanel = new JPanel();
        hillPanel.add(new JLabel("Number of iterations: "));
        iteration_number.setText("100");
        iteration_number.setPreferredSize(new Dimension(50, 20));
        hillPanel.add(iteration_number);
        hillClimbingPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        hillClimbingPanel.add(hillPanel, BorderLayout.WEST);
        hillClimbingPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        ///Simulated Annealing Panel
        annealingPanel.add(new JLabel(ANNEALING));
        annealingPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel coolingPanel = new JPanel();
        coolingPanel.add(new JLabel("Cooling factor (▲T): "));
        cooling_factor.setPreferredSize(new Dimension(60, 20));
        cooling_factor.setText("1");
        coolingPanel.add(cooling_factor);
        coolingPanel.setPreferredSize(new Dimension(100, 20));
        annealingPanel.add(coolingPanel);

        JPanel temperaturePanel = new JPanel();
        temperaturePanel.add(new JLabel("Starting temperature: "));
        start_temp.setPreferredSize(new Dimension(60, 20));
        start_temp.setText("10000");
        temperaturePanel.add(start_temp);
        temperaturePanel.setPreferredSize(new Dimension(100, 20));
        annealingPanel.add(temperaturePanel);
        annealingPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        ///Local Beam Search Panel
        beamPanel.add(new JLabel(BEAM));
        beamPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel beamIterationPanel = new JPanel();
        beamIterationPanel.add(new JLabel("Number of iterations: "));
        beam_iteration_number.setPreferredSize(new Dimension(70, 20));
        beam_iteration_number.setText("50");
        beamIterationPanel.add(beam_iteration_number);
        beamPanel.add(beamIterationPanel);

        JPanel beamStatesPanel = new JPanel();
        beamStatesPanel.add(new JLabel("Number of states: "));
        beam_states_number.setPreferredSize(new Dimension(80, 20));
        beam_states_number.setText("25");
        beamStatesPanel.add(beam_states_number);
        beamPanel.add(beamStatesPanel);
        beamPanel.add(Box.createRigidArea(new Dimension(0, 80)));

        geneticPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        ///Adding Description Panels to the Card Layout Panels
        cardsPanel.add(hillClimbingPanel, HILL);
        cardsPanel.add(annealingPanel, ANNEALING);
        cardsPanel.add(beamPanel, BEAM);

        mainCardPanel.add(cardsPanel);
        AButtonPanel.add(mainCardPanel);

        JButton algorithmButton = new JButton("RUN ALGORITHM");
        JPanel runPanel = new JPanel();
        runPanel.add(algorithmButton);
        algorithmButton.setPreferredSize(new Dimension(120, 30));
        AButtonPanel.add(runPanel);

        TheButtonPanel.add(AButtonPanel, BorderLayout.SOUTH);

        TheMainPanel.setLayout(new BoxLayout(TheMainPanel, BoxLayout.X_AXIS));
        TheChessPanel.setPreferredSize(new Dimension(500, 600));
        TheMainPanel.add(TheChessPanel, BorderLayout.WEST);
        TheMainPanel.add(TheButtonPanel, BorderLayout.EAST);

        algorithmCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout) (cardsPanel.getLayout());
                cl.show(cardsPanel, (String) e.getItem());
            }
        });

        sizeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sizeCombo = (JComboBox) event.getSource();

                TheMainPanel.remove(TheChessPanel);
                TheMainPanel.remove(TheButtonPanel);
                TheChessPanel = getSquares();
                TheChessPanel.setPreferredSize(new Dimension(500, 600));
                remove(TheMainPanel);
                TheMainPanel.add(TheChessPanel, BorderLayout.WEST);
                TheMainPanel.add(TheButtonPanel, BorderLayout.EAST);
                add(TheMainPanel);
                revalidate();
            }
        });

        algorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Algorithms algorithm = new Algorithms(queen_array, getBoardSize());

                if (Objects.equals(algorithmCombo.getSelectedItem(), HILL)) {

                    queen_array = algorithm.climbHill(Integer.parseInt(iteration_number.getText()));
                    changeQueens(queen_array);

                } else if (Objects.equals(algorithmCombo.getSelectedItem(), ANNEALING)) {

                    queen_array = algorithm.simulateAnnealing(Integer.parseInt(start_temp.getText()),
                            Float.parseFloat(cooling_factor.getText()));
                    changeQueens(queen_array);

                } else if (Objects.equals(algorithmCombo.getSelectedItem(), BEAM)) {

                    queen_array = algorithm.beam(Integer.parseInt(beam_iteration_number.getText()),
                            Integer.parseInt(beam_states_number.getText()));
                    changeQueens(queen_array);

                }
                revalidate();
            }
        });

        this.add(TheMainPanel);
        setVisible(true);
    }

    JLabel getQueen() {
        int size = getBoardSize();
        JLabel imageLabel = new JLabel();
        Image queenIcon = queenImage.getImage().getScaledInstance(500 / size, 500 / size, Image.SCALE_SMOOTH);
        ImageIcon queenImage = new ImageIcon(queenIcon);
        imageLabel.setIcon(queenImage);
        return imageLabel;
    }

    int getBoardSize() {
        return Integer.parseInt(Objects.requireNonNull(sizeCombo.getSelectedItem()).toString());
    }

    JPanel getSquares() {

        int size = getBoardSize();                                                        //gets the size of the board
        squares = new JPanel[size][size];                                                   //sets an array of JPanels

        JPanel chessSquares = new JPanel(new GridLayout(size, size));                                //makes the board

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel square = new JPanel();                                                    //initializes a square

                square.setBackground(new Color(136, 49, 14, 245));                     //colors the square
                if ((i + j) % 2 == 0) {
                    square.setBackground(Color.orange);
                }

                squares[i][j] = square;                       //adds the square to an array we use to modify our queens

                chessSquares.add(square);                                                //adds the square to the board
            }
        }

        queen_array = new int[size];
        for (int i = 0; i < size; i++) {
            int random_j = (int) (Math.random() * size);
            squares[i][random_j].add(getQueen());
            queen_array[i] = random_j;
        }
        return chessSquares;
    }

    void changeQueens(int[] new_array) {

        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                squares[i][j].removeAll();
            }
        }

        for (int i = 0; i < getBoardSize(); i++) {
            squares[i][new_array[i]].add(getQueen());
        }
        revalidate();
        repaint();
    }
}
