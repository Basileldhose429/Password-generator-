import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/** Swing graphical interface for the Password Generator. */
public class PasswordGeneratorGUI extends JFrame {

    private final PasswordGenerator generator = new PasswordGenerator();

    private final JSlider lengthSlider = new JSlider(PasswordGenerator.MIN_LENGTH, 64, 16);
    private final JLabel lengthLabel = new JLabel("Length: 16");
    private final JCheckBox upperBox   = new JCheckBox("Uppercase (A-Z)", true);
    private final JCheckBox lowerBox   = new JCheckBox("Lowercase (a-z)", true);
    private final JCheckBox numberBox  = new JCheckBox("Numbers (0-9)", true);
    private final JCheckBox specialBox = new JCheckBox("Special (!@#$...)", true);
    private final JTextField output = new JTextField();
    private final JLabel strengthLabel = new JLabel("Strength: -");

    public PasswordGeneratorGUI() {
        super("Password Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel options = new JPanel(new GridLayout(0, 1, 4, 4));
        options.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        options.add(lengthLabel);
        options.add(lengthSlider);
        options.add(upperBox);
        options.add(lowerBox);
        options.add(numberBox);
        options.add(specialBox);

        lengthSlider.addChangeListener(e -> lengthLabel.setText("Length: " + lengthSlider.getValue()));

        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        output.setHorizontalAlignment(JTextField.CENTER);

        JButton generateBtn = new JButton("Generate");
        JButton copyBtn = new JButton("Copy");
        generateBtn.addActionListener(e -> generate());
        copyBtn.addActionListener(e -> {
            if (!output.getText().isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                       .setContents(new StringSelection(output.getText()), null);
                strengthLabel.setText(strengthLabel.getText() + "  (copied)");
            }
        });

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(generateBtn);
        buttons.add(copyBtn);

        JPanel bottom = new JPanel(new GridLayout(0, 1, 4, 8));
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        bottom.add(output);
        bottom.add(strengthLabel);
        bottom.add(buttons);

        add(options, BorderLayout.NORTH);
        add(bottom, BorderLayout.CENTER);

        setSize(380, 430);
        setLocationRelativeTo(null);
    }

    private void generate() {
        try {
            String pw = generator.generate(lengthSlider.getValue(), upperBox.isSelected(),
                    lowerBox.isSelected(), numberBox.isSelected(), specialBox.isSelected());
            output.setText(pw);
            strengthLabel.setText("Strength: " + PasswordGenerator.rateStrength(pw));
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI().setVisible(true));
    }
}
