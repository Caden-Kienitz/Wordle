import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame("New Window");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // centers the frame on the screen

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add some margin around the panel

        JButton button4 = new JButton("4 Columns");
        JButton button5 = new JButton("5 Columns");
        JButton button6 = new JButton("6 Columns");

        topPanel.add(button4);
        topPanel.add(button5);
        topPanel.add(button6);

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5, 5, 5)); // add some padding between the text fields
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add some margin around the panel

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add some margin around the panel
        JTextField textField = new JTextField(20);
        bottomPanel.add(textField);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setLayout(new GridLayout(5, 4, 5, 5));
                frame.pack();
                frame.revalidate();
            }
        });

        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setLayout(new GridLayout(5, 5, 5, 5));
                frame.pack();
                frame.revalidate();
            }
        });

        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setLayout(new GridLayout(5, 6, 5, 5));
                frame.pack();
                frame.revalidate();
            }
        });

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userText = textField.getText();
                System.out.println(userText);
                // The userText variable now contains the text entered by the user
                frame.dispose(); //closes the JFrame
            }
        });

        frame.setVisible(true);
    }
}
