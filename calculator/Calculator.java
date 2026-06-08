import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// CSC426 calculator. Plain Swing GUI that handles the basic four operators
// plus power, modulo and integer division. Built and tested on JDK 21.
public class Calculator extends JFrame {

    private double accumulator = 0;        // current running value
    private String pendingOp = "";          // operator we are waiting to apply
    private boolean startNewNumber = true;  // should the next digit replace the display
    private boolean lastWasEquals = false;

    private final JTextField display = new JTextField("0");

    // light office palette
    private static final Color FRAME_BG   = new Color(0xEE, 0xF1, 0xF5);
    private static final Color SURFACE     = Color.WHITE;
    private static final Color BORDER      = new Color(0xD3, 0xDA, 0xE2);
    private static final Color TEXT        = new Color(0x1F, 0x2A, 0x37);
    private static final Color MUTED       = new Color(0x60, 0x6B, 0x78);
    private static final Color OP_BG       = new Color(0xE7, 0xF0, 0xFA);
    private static final Color OP_TEXT     = new Color(0x1F, 0x5F, 0xA6);
    private static final Color FUNC_BG     = new Color(0xF1, 0xF3, 0xF6);
    private static final Color EQ_BG       = new Color(0x1F, 0x5F, 0xA6);
    private static final Color CLEAR_TEXT  = new Color(0xC0, 0x39, 0x2B);

    public Calculator() {
        setTitle("CSC426 Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(360, 480));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(FRAME_BG);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.add(buildDisplay(), BorderLayout.NORTH);
        root.add(buildKeypad(), BorderLayout.CENTER);

        setContentPane(root);
        attachKeyboardSupport(root);
    }

    private JComponent buildDisplay() {
        display.setEditable(false);
        display.setFocusable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(SURFACE);
        display.setForeground(TEXT);
        display.setFont(new Font("Consolas", Font.BOLD, 36));
        display.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(18, 16, 18, 16)));
        return display;
    }

    private JComponent buildKeypad() {
        JPanel grid = new JPanel(new GridLayout(6, 4, 8, 8));
        grid.setBackground(FRAME_BG);

        // top row: clear, backspace and the two less common operators
        grid.add(makeButton("C", FUNC_BG, CLEAR_TEXT));
        grid.add(makeButton("<-", FUNC_BG, MUTED));
        grid.add(makeButton("%", OP_BG, OP_TEXT));
        grid.add(makeButton("\\", OP_BG, OP_TEXT));

        grid.add(makeButton("7", SURFACE, TEXT));
        grid.add(makeButton("8", SURFACE, TEXT));
        grid.add(makeButton("9", SURFACE, TEXT));
        grid.add(makeButton("/", OP_BG, OP_TEXT));

        grid.add(makeButton("4", SURFACE, TEXT));
        grid.add(makeButton("5", SURFACE, TEXT));
        grid.add(makeButton("6", SURFACE, TEXT));
        grid.add(makeButton("*", OP_BG, OP_TEXT));

        grid.add(makeButton("1", SURFACE, TEXT));
        grid.add(makeButton("2", SURFACE, TEXT));
        grid.add(makeButton("3", SURFACE, TEXT));
        grid.add(makeButton("-", OP_BG, OP_TEXT));

        grid.add(makeButton("^", OP_BG, OP_TEXT));
        grid.add(makeButton("0", SURFACE, TEXT));
        grid.add(makeButton(".", SURFACE, TEXT));
        grid.add(makeButton("+", OP_BG, OP_TEXT));

        // equals sits on its own row
        grid.add(makeButton("=", EQ_BG, Color.RED));
        grid.add(spacer()); grid.add(spacer()); grid.add(spacer());

        return grid;
    }

    private JLabel spacer() {
        JLabel l = new JLabel();
        l.setOpaque(false);
        return l;
    }

    private JButton makeButton(String label, Color bg, Color fg) {
        JButton b = new JButton(label);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(BORDER, 1, true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.addActionListener(e -> handleInput(label));
        return b;
    }

    // routes a button press or a keystroke to the right action
    private void handleInput(String token) {
        switch (token) {
            case "C"  -> clearAll();
            case "<-" -> backspace();
            case "."  -> appendDot();
            case "=", "Enter" -> applyEquals();
            case "+", "-", "*", "/", "\\", "^", "%" -> chooseOperator(token);
            default   -> appendDigit(token);
        }
    }

    private void clearAll() {
        accumulator = 0;
        pendingOp = "";
        startNewNumber = true;
        lastWasEquals = false;
        display.setText("0");
    }

    private void appendDigit(String d) {
        if (startNewNumber || lastWasEquals || display.getText().equals("0")) {
            display.setText(d);
            startNewNumber = false;
            lastWasEquals = false;
        } else {
            display.setText(display.getText() + d);
        }
    }

    private void appendDot() {
        if (startNewNumber || lastWasEquals) {
            display.setText("0.");
            startNewNumber = false;
            lastWasEquals = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void backspace() {
        if (startNewNumber || lastWasEquals) return;
        String text = display.getText();
        if (text.length() <= 1) {
            display.setText("0");
            startNewNumber = true;
        } else {
            display.setText(text.substring(0, text.length() - 1));
        }
    }

    private void chooseOperator(String op) {
        // if something is already pending, finish that first so
        // 2 + 3 + 4 keeps adding up instead of losing the middle step
        if (!pendingOp.isEmpty() && !startNewNumber) {
            applyEquals();
        } else {
            accumulator = readDisplay();
        }
        pendingOp = op;
        startNewNumber = true;
        lastWasEquals = false;
    }

    private void applyEquals() {
        if (pendingOp.isEmpty()) {
            lastWasEquals = true;
            return;
        }
        double current = readDisplay();
        try {
            accumulator = compute(accumulator, current, pendingOp);
            display.setText(format(accumulator));
        } catch (ArithmeticException ex) {
            display.setText("Error: " + ex.getMessage());
            accumulator = 0;
        }
        pendingOp = "";
        startNewNumber = true;
        lastWasEquals = true;
    }

    private double compute(double a, double b, String op) {
        return switch (op) {
            case "+"  -> a + b;
            case "-"  -> a - b;
            case "*"  -> a * b;
            case "/"  -> {
                if (b == 0) throw new ArithmeticException("divide by zero");
                yield a / b;
            }
            case "\\" -> {
                if (b == 0) throw new ArithmeticException("divide by zero");
                yield Math.floor(a / b);
            }
            case "^"  -> Math.pow(a, b);
            case "%"  -> {
                if (b == 0) throw new ArithmeticException("mod by zero");
                yield a % b;
            }
            default   -> b;
        };
    }

    private double readDisplay() {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    // drop the trailing .0 on whole numbers so the display stays tidy
    private String format(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) return "Error";
        if (value == Math.rint(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    private void attachKeyboardSupport(JComponent root) {
        root.setFocusable(true);
        root.requestFocusInWindow();
        root.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                int code = e.getKeyCode();
                if (Character.isDigit(c)) handleInput(String.valueOf(c));
                else if ("+-*/\\^%".indexOf(c) >= 0) handleInput(String.valueOf(c));
                else if (c == '.') handleInput(".");
                else if (code == KeyEvent.VK_ENTER || c == '=') handleInput("=");
                else if (code == KeyEvent.VK_BACK_SPACE) handleInput("<-");
                else if (code == KeyEvent.VK_DELETE || code == KeyEvent.VK_ESCAPE) handleInput("C");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }
            new Calculator().setVisible(true);
        });
    }
}
