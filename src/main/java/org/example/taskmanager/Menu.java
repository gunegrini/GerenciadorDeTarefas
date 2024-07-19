package org.example.taskmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JFrame frame;

    public Menu() {
        frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel titleLabel = new JLabel("Gerenciador DE Tarefas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("BadaBoom BB", Font.BOLD, 50));
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        JButton createTaskButton = new JButton("Criar novas tarefas");
        createTaskButton.setFont(new Font("Arial", Font.BOLD, 16));
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskManagerGUI();
                frame.dispose();
            }
        });
        panel.add(createTaskButton, gbc);

        gbc.gridy++;
        JButton viewTasksButton = new JButton("Ver tarefas salvas");
        viewTasksButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewTasks();
                frame.dispose();
            }
        });
        panel.add(viewTasksButton, gbc);

        gbc.gridy++;
        JTextArea versionText = new JTextArea("Version: 1.0");
        versionText.setFont(new Font("Arial", Font.PLAIN, 14));
        versionText.setEditable(false);
        versionText.setBackground(panel.getBackground()); // Faz com que o fundo do JTextArea seja igual ao fundo do painel
        panel.add(versionText, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
