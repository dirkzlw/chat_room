package com.chat.client.test;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class xxx extends JFrame {
    private JFileChooser chooser = new JFileChooser();
    private JTextPane textPane = new JTextPane();
    public xxx() {
        Container contentPane = getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu insertMenu = new JMenu("Insert");
        JMenuItem imageItem = new JMenuItem("image");
        insertMenu.add(imageItem);
        menuBar.add(insertMenu);
        setJMenuBar(menuBar);
        textPane.setFont(new Font("Serif", Font.ITALIC, 24));
        textPane.setText("xxxx\ncccc");
        contentPane.add(textPane, BorderLayout.CENTER);
        imageItem.addActionListener(e -> {
            int option =
                    chooser.showDialog(xxx.this,"Pick An Image");
            if(option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if(file != null) {
                    textPane.insertIcon(new ImageIcon(
                            file.getPath()));
                }
            }
        });
    }
    public static void main(String args[]) {
        GJApp3.launch(new xxx(),
                "Using JTextPane",300,300,450,300);
    }
}
class GJApp3 extends WindowAdapter {
    static private JPanel statusArea = new JPanel();
    static private JLabel status = new JLabel(" ");
    public static void launch(final JFrame f, String title,
                              final int x, final int y,
                              final int w, int h) {
        f.setTitle(title);
        f.setBounds(x,y,w,h);
        f.setVisible(true);
        statusArea.setBorder(BorderFactory.createEtchedBorder());
        statusArea.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        statusArea.add(status);
        status.setHorizontalAlignment(JLabel.LEFT);
        f.setDefaultCloseOperation(
                WindowConstants.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}