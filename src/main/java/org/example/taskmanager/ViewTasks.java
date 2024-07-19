package org.example.taskmanager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ViewTasks {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Task> tasks;

    public ViewTasks() {
        tasks = loadTasks();
        frame = new JFrame("Tarefas Salvas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Configuração da Tabela
        tableModel = new DefaultTableModel(new String[]{"Descrição", "Concluída"}, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Preenche a tabela com tarefas carregadas
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getDescription(), task.isCompleted()});
        }

        // Botão Voltar
        JButton backButton = new JButton("Voltar");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 40));  // Define o tamanho preferido do botão
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
                frame.dispose();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private ArrayList<Task> loadTasks() {
        System.out.println("Loading tasks from file...");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TaskManagerGUI.FILE_NAME))) {
            ArrayList<Task> loadedTasks = (ArrayList<Task>) ois.readObject();
            System.out.println("Tasks loaded successfully.");
            return loadedTasks;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
