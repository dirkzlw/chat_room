package com.chat.client.test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

class SeTest extends JPanel {

    public static Image bj;

    static {
        try {
            bj = ImageIO.read(new File("img/code/63NL.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setSize(200, 300);
        frame.setSize(200, 300);
        JTextArea textArea = new JTextArea();
        textArea.setSize(200, 300);
        panel.add(textArea);

        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bj, 0, 0, null);                             //通过画笔，画出图片。
    }

}
