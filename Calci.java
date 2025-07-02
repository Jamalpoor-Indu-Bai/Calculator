
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class Calci extends JFrame implements ActionListener {
    JTextField display;
    double result = 0, memory = 0;
    String operator = "";
    boolean isNewOperation = true;

    public Calci() {
        setLayout(new BorderLayout());

        setCustomIcon();

        // Top panel setup
        JPanel topPanel = new JPanel(new BorderLayout());

        // Calculator Display
        display = new JTextField("0");
        display.setPreferredSize(new Dimension(0, 100));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        topPanel.add(display, BorderLayout.CENTER);

        // Memory Function Buttons Panel
        JPanel memoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] memoryButtons = {"MC", "MR", "M+", "M-", "MS"};
        for (String memButton : memoryButtons) {
            JButton button = new JButton(memButton);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            memoryPanel.add(button);
        }
        topPanel.add(memoryPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Panel for calculator buttons
        JPanel down = new JPanel();
        down.setLayout(new GridLayout(6, 4, 3, 3));

        String[][] buttons = {
                {"%", "Percentage"}, {"CE", "Clear Entry"}, {"C", "Clear All"}, {"x", "Delete Last Digit"},
                {"1/x", "Reciprocal"}, {"x^2", "Square"}, {"√x", "Square Root"}, {"/", "Divide"},
                {"7", "Number 7"}, {"8", "Number 8"}, {"9", "Number 9"}, {"*", "Multiply"},
                {"4", "Number 4"}, {"5", "Number 5"}, {"6", "Number 6"}, {"-", "Subtract"},
                {"1", "Number 1"}, {"2", "Number 2"}, {"3", "Number 3"}, {"+", "Add"},
                {"+/-", "Negate"}, {"0", "Number 0"}, {".", "Decimal Point"}, {"=", "Equals"}
        };

        for (String[] buttonData : buttons) {
            JButton button = new JButton(buttonData[0]);
            button.setToolTipText(buttonData[1]);
            button.setFont(new Font("Arial", Font.BOLD, 18));

            // Color coding
            if ("+-*/%".contains(buttonData[0])) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
            } else if (buttonData[0].equals("=")) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
            } else if (buttonData[0].equals("C") || buttonData[0].equals("CE") || buttonData[0].equals("x")) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }

            button.addActionListener(this);
            down.add(button);
        }

        add(down, BorderLayout.CENTER);

        // Frame settings
        setSize(400, 600);
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setCustomIcon() {
        try {
            java.net.URL imageURL = getClass().getResource("images.jpeg");
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                Image image = icon.getImage();
                setIconImage(image);
            } else {
                System.out.println("Icon image not found");
            }
        } catch (Exception e) {
            System.out.println("Failed to set icon: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789".contains(command) || command.equals(".")) {
            if (isNewOperation) {
                display.setText(command.equals(".") ? "0." : command);
                isNewOperation = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            result = 0;
            operator = "";
            isNewOperation = true;
        } else if (command.equals("CE")) {
            display.setText("0");
        } else if (command.equals("x")) {
            String currentText = display.getText();
            if (currentText.length() > 1) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                display.setText("0");
            }
        } else if (command.equals("MC")) {
            memory = 0;
        } else if (command.equals("MR")) {
            display.setText(String.valueOf(memory));
        } else if (command.equals("M+")) {
            memory += Double.parseDouble(display.getText());
        } else if (command.equals("M-")) {
            memory -= Double.parseDouble(display.getText());
        } else if (command.equals("MS")) {
            memory = Double.parseDouble(display.getText());
        } else if ("+-*/%".contains(command)) {
            if (!operator.isEmpty() && !isNewOperation) {
                calculateIntermediateResult();
            } else {
                result = Double.parseDouble(display.getText());
            }
            operator = command;
            isNewOperation = true;
        } else if (command.equals("1/x")) {
            double value = Double.parseDouble(display.getText());
            display.setText(value != 0 ? String.valueOf(1 / value) : "Error");
        } else if (command.equals("x^2")) {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(value * value));
        } else if (command.equals("√x")) {
            double value = Double.parseDouble(display.getText());
            display.setText(value >= 0 ? String.valueOf(Math.sqrt(value)) : "Error");
        } else if (command.equals("+/-")) {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(-value));
        } else if (command.equals("=")) {
            calculateIntermediateResult();
            operator = "";
            isNewOperation = true;
        }
    }

    private void calculateIntermediateResult() {
        double currentValue = Double.parseDouble(display.getText());
        switch (operator) {
            case "+": result += currentValue; break;
            case "-": result -= currentValue; break;
            case "*": result *= currentValue; break;
            case "/":
                if (currentValue != 0) result /= currentValue;
                else {
                    display.setText("Error");
                    result = 0;
                    isNewOperation = true;
                    return;
                }
                break;
            case "%":
                if (currentValue != 0) result %= currentValue;
                else {
                    display.setText("Error");
                    result = 0;
                    isNewOperation = true;
                    return;
                }
                break;
        }
        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        new Calci();
    }
}
