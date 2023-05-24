package com.multimedia;

import com.multimedia.algorithms.CompressionAlgorithm;
import com.multimedia.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    private static JFrame frame;

    private static JFileChooser fileChooser;

    private static JRadioButton compressButton;
    private static JRadioButton decompressButton;

    private static final JRadioButton huffmanButton = new JRadioButton("Huffman", true);
    private static final JRadioButton lzwButton = new JRadioButton("LZW");
    private static final JRadioButton arithmaticButton = new JRadioButton("Arithmetic");

    private static final JLabel messageLabel = new JLabel();

    private static CompressionAlgorithm algorithm;

    public static void submit() {
        messageLabel.setText("Processing");
        messageLabel.setForeground(Color.RED);
        File inputFile = fileChooser.getSelectedFile();
        if (inputFile == null)
            return;
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (lzwButton.isSelected())
                    algorithm = new LZW(inputFile);
                else if (huffmanButton.isSelected())
                    algorithm = new Huffman(inputFile);

                if (compressButton.isSelected())
                    algorithm.compress();
                else
                    algorithm.decompress();

                messageLabel.setText("Done!");
                messageLabel.setForeground(Color.GREEN);
            }
        }).start();

    }

    private static void handleFileChooser() {
        JPanel innerFrame = new JPanel();
        innerFrame.setBounds(0, 100, 600, 400);
        innerFrame.setBackground(Color.getHSBColor(0.1f, .0f, .9f));
        frame.add(innerFrame);
        JLabel label = new JLabel();
        label.setText("Select the input file: ");
        label.setFont(new Font("sans-serif", Font.BOLD, 14));
        label.setBounds(20, 0, 200, 40);
        innerFrame.add(label);

        fileChooser = new JFileChooser();
        fileChooser.setBounds(50, 50, 450, 250);
        innerFrame.add(fileChooser);

        compressButton = new JRadioButton("Compress", null, true);
        decompressButton = new JRadioButton("Decompress");
        ButtonGroup group = new ButtonGroup();
        group.add(compressButton);
        group.add(decompressButton);
        compressButton.setBounds(20, 320, 120, 30);
        decompressButton.setBounds(20, 360, 120, 30);
        innerFrame.add(compressButton);
        innerFrame.add(decompressButton);
        innerFrame.setLayout(null);
    }

    private static void windowHandler(JFrame f) {
        f.setBounds(200, 100, 100, 400);
        f.setSize(900, 600);// 400 width and 500 height
        JLabel label = new JLabel();
        label.setText("Multimedia Project!");
        label.setBounds(20, 20, 600, 50);
        Font font = new Font("Arial", 3, 46);
        label.setFont(font);
        f.add(label);
    }

    private static void handleAlgorithms() {
        JPanel innerFrame = new JPanel();
        innerFrame.setBounds(600, 0, 400, 600);
        innerFrame.setBackground(Color.DARK_GRAY);
        JLabel label = new JLabel();
        label.setForeground(Color.LIGHT_GRAY);
        label.setText("Algorithms");
        label.setFont(new Font("Consolas", Font.ITALIC, 23));
        label.setBounds(10, 50, 400, 120);

        ButtonGroup gp = new ButtonGroup();
        gp.add(huffmanButton);
        gp.add(arithmaticButton);
        gp.add(lzwButton);

        huffmanButton.setBounds(0, 0, 400, 30);
        arithmaticButton.setBounds(0, 0, 400, 30);
        lzwButton.setBounds(0, 0, 400, 30);

        huffmanButton.setForeground(Color.LIGHT_GRAY);
        arithmaticButton.setForeground(Color.LIGHT_GRAY);
        lzwButton.setForeground(Color.LIGHT_GRAY);

        lzwButton.setBackground(null);
        arithmaticButton.setBackground(null);
        huffmanButton.setBackground(null);

        innerFrame.add(label);
        frame.add(innerFrame);
        innerFrame.add(huffmanButton);
        innerFrame.add(arithmaticButton);
        innerFrame.add(lzwButton);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submit();
                fileChooser.updateUI();
            }
        });
        innerFrame.add(submitButton);
        BoxLayout layout = new BoxLayout(innerFrame, BoxLayout.Y_AXIS);
        innerFrame.setLayout(layout);

    }

    public static void main(String[] args) throws IOException {
        frame = new JFrame("Multimedia Project");// creating instance of JFrame
        windowHandler(frame);
        handleFileChooser();
        handleAlgorithms();
        Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 36);
        messageLabel.setFont(font);
        messageLabel.setForeground(Color.GREEN);
        messageLabel.setBounds(250, 420, 300, 200);
        messageLabel.setText("Nothing Yet!");
        frame.add(messageLabel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);// using no layout managers
        frame.setVisible(true);// making the frame visible
    }
}
