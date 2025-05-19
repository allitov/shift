package ru.shift.client;

import ru.shift.common.ChatMessage;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ChatClientUI extends JFrame {

    private final JTextArea chatArea = new JTextArea(20, 40);
    private final JTextField inputField = new JTextField();
    private final DefaultListModel<String> usersModel = new DefaultListModel<>();
    private ChatClient client;

    public ChatClientUI() {
        super("Chat Client");

        /* левая часть ‑ сообщения */
        chatArea.setEditable(false);
        ((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane chatScroll = new JScrollPane(chatArea);

        /* правая часть ‑ список пользователей */
        JList<String> usersList = new JList<>(usersModel);
        JScrollPane usersScroll = new JScrollPane(usersList);
        usersScroll.setPreferredSize(new Dimension(120, 0));

        /* нижняя часть ‑ поле ввода + кнопка */
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        JButton sendBtn = new JButton("Отправить");
        bottom.add(sendBtn, BorderLayout.EAST);

        /* основной layout */
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, chatScroll, usersScroll);
        split.setResizeWeight(0.8);

        getContentPane().add(split, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void start() {
        /* диалог данных подключения */
        JTextField hostField = new JTextField("localhost");
        JTextField portField = new JTextField("9000");
        JTextField nameField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Хост:"));
        panel.add(hostField);
        panel.add(new JLabel("Порт:"));
        panel.add(portField);
        panel.add(new JLabel("Имя:"));
        panel.add(nameField);

        int res = JOptionPane.showConfirmDialog(
                this, panel, "Подключение к чату",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (res != JOptionPane.OK_OPTION) {
            System.exit(0);
        }

        client = new ChatClient(this::processMessage);
        boolean ok = client.connect(
                hostField.getText(),
                Integer.parseInt(portField.getText()),
                nameField.getText().trim()
        );
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Не удалось подключиться", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        setTitle("Chat - " + nameField.getText());
        setVisible(true);
        inputField.requestFocusInWindow();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                client.close();
            }
        });
    }

    private void processMessage(ChatMessage msg) {
        SwingUtilities.invokeLater(() -> {
            switch (msg.getType()) {
                case TEXT -> chatArea.append(
                        "[" + msg.getTimestamp() + "] " + msg.getSender() + ": " + msg.getContent() + "\n");
                case JOIN, LEAVE -> chatArea.append("* " + msg.getContent() + "\n");
                case ERROR -> JOptionPane.showMessageDialog(this, msg.getContent(), "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                case USER_LIST -> updateUserList(msg.getContent());
            }
        });
    }

    private void updateUserList(String userData) {
        List<String> users = Arrays.stream(userData.split(","))
                .sorted()
                .toList();
        usersModel.clear();
        users.forEach(usersModel::addElement);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;
        client.sendText(text);
        inputField.setText("");
    }
}