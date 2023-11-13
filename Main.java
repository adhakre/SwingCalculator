import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);

        String[] buttonLabels = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", ".", "=", "/",
                "C", "AC" // Add Clear and All Clear buttons
        };

        JButton[] buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            panel.add(buttons[i]);
        }

        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();
                    switch (command) {
                        case "C": // Clear the last entry
                            String currentText = textField.getText();
                            if (!currentText.isEmpty()) {
                                textField.setText(currentText.substring(0, currentText.length() - 1));
                            }
                            break;
                        case "AC": // Clear all
                            textField.setText("");
                            break;
                        case "=":
                            String expression = textField.getText();
                            String result = evaluateExpression(expression);
                            textField.setText(result);
                            break;
                        default:
                            textField.setText(textField.getText() + command);
                            break;
                    }
                }
            });
        }

        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static String evaluateExpression(String expression) {
        try {
            // Check if the expression contains an operator
            if (!expression.matches(".*[+\\-*/].*")) {
                return "Error: Incomplete expression";
            }

            // Split by operator (without removing the operator)
            String[] parts = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])");

            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);

            double result = 0.0;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        return "Error: Division by zero";
                    }
                    break;
                default:
                    return "Error: Invalid operator";
            }

            return Double.toString(result);
        } catch (Exception e) {
            return "Error: Invalid expression";
        }
    }
}
