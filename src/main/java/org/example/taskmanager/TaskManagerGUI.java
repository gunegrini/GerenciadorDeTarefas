package org.example.taskmanager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class TaskManagerGUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea taskField;
    private ArrayList<Task> tasks;
    public static final String FILE_NAME = "tasks.ser";

    public TaskManagerGUI() {
        tasks = loadTasks();
        frame = new JFrame("Gerenciador de Tarefas");
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

        // Campo de entrada e botões
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        taskField = new JTextArea(3, 25);  // Mudança de JTextField para JTextArea
        taskField.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane taskFieldScrollPane = new JScrollPane(taskField);  // Adiciona um JScrollPane ao JTextArea
        JButton addButton = new JButton("Adicionar Tarefa");
        JButton editButton = new JButton("Editar Tarefa");
        JButton completeButton = new JButton("Concluir Tarefa");
        JButton deleteButton = new JButton("Excluir Tarefa");
        JButton backButton = new JButton("Voltar");

        // Estilização dos botões
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = Color.WHITE;
        Color buttonTextColor = Color.BLACK;

        addButton.setFont(buttonFont);
        addButton.setBackground(buttonColor);
        addButton.setForeground(buttonTextColor);

        editButton.setFont(buttonFont);
        editButton.setBackground(buttonColor);
        editButton.setForeground(buttonTextColor);

        completeButton.setFont(buttonFont);
        completeButton.setBackground(buttonColor);
        completeButton.setForeground(buttonTextColor);

        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(buttonTextColor);

        backButton.setFont(buttonFont);
        backButton.setBackground(buttonColor);
        backButton.setForeground(buttonTextColor);

        inputPanel.add(taskFieldScrollPane);
        inputPanel.add(addButton);
        inputPanel.add(editButton);
        inputPanel.add(completeButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Adiciona action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
                saveTasks(); // Salva após adicionar
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTask();
                saveTasks(); // Salva após editar
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeTask();
                saveTasks(); // Salva após concluir
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
                saveTasks(); // Salva após excluir
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Menu();
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addTask() {
        String description = taskField.getText();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "A descrição da tarefa não pode estar vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Task task = new Task(description);
            tasks.add(task);
            tableModel.addRow(new Object[]{task.getDescription(), task.isCompleted()});
            taskField.setText("");
        }
    }

    private void editTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String description = JOptionPane.showInputDialog(frame, "Editar Descrição da Tarefa", tasks.get(selectedRow).getDescription());
            if (description != null && !description.isEmpty()) {
                Task task = tasks.get(selectedRow);
                task.setDescription(description);
                tableModel.setValueAt(description, selectedRow, 0);
            }
        }
    }

    private void completeTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Task task = tasks.get(selectedRow);
            task.setCompleted(true);
            tableModel.setValueAt(true, selectedRow, 1);
        }
    }

    private void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tasks.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }

    private void saveTasks() {
        System.out.println("Saving tasks to file...");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Task> loadTasks() {
        System.out.println("Loading tasks from file...");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ArrayList<Task> loadedTasks = (ArrayList<Task>) ois.readObject();
            System.out.println("Tasks loaded successfully.");
            return loadedTasks;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagerGUI());
    }
}
