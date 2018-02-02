package com.milk.jump;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

class JumpJumpMain {
    private JPanel mainPanel;
    private JComboBox comboBoxMode;
    private JButton runButton;
    private JPanel screenPanel;
    private JButton scanButton;
    private JTextField adbTextField;
    private JTextField heightTextField;
    private JTextField widthTextField;
    private JSlider retioSlider;
    private JLabel ratioLabel;
    private JSlider intevalSlider;
    private JLabel intevalLbael;
    private JButton widthButton;
    private JButton heightButton;
    private JTextArea usageTextArea;
    private JTextArea textArea1;
    private JTextField imageStoreFile;
    private JButton fileScan;

    public JumpJumpMain() {
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainKt.run();
            }
        });

        comboBoxMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainKt.setPlayMode(comboBoxMode.getSelectedIndex());
            }
        });

        intevalSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = intevalSlider.getValue();
                intevalLbael.setText(String.valueOf(value));
                MainKt.setScreenshotInterval(value);
            }
        });

        retioSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                double value = retioSlider.getValue() / 100.0;

                ratioLabel.setText(String.valueOf(value));
                MainKt.setResizedDistancePressTimeRatio(value);
            }
        });

        scanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialog = new JFileChooser();
                dialog.setDialogTitle("choose adb");
                int result = dialog.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = dialog.getSelectedFile();
                    String fileName = file.getName();
                    if (fileName.contains("adb")) {
                        adbTextField.setText(file.getAbsolutePath());
                        AdbCaller.INSTANCE.setAdbPath(file.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog( null,"Please input adb path!");
                    }
                }
            }
        });

        fileScan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialog = new JFileChooser();
                dialog.setDialogTitle("choose screenShot image store file");
                int result = dialog.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = dialog.getSelectedFile();
                    String fileName = file.getName();
                    if (fileName.endsWith(".png")) {
                        imageStoreFile.setText(file.getAbsolutePath());
                        MainKt.setScreenshotPath(file.getAbsolutePath());
                        AdbCaller.INSTANCE.setScreenshotLocation(file.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog( null,"Please select png file!");
                    }
                }
            }
        });

        widthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainKt.setResizedScreenWidth(Integer.parseInt(widthTextField.getText()));
            }
        });

        heightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainKt.setResizedScreenHeight(Integer.parseInt(heightTextField.getText()));
            }
        });
    }

    public JPanel getMainPanel() {
        adbTextField.setText(MainKt.getAdbPath());
        imageStoreFile.setText(MainKt.getScreenshotPath());
        widthTextField.setText(String.valueOf(MainKt.getResizedScreenWidth()));
        heightTextField.setText(String.valueOf(MainKt.getResizedScreenHeight()));
        intevalSlider.setExtent(MainKt.getScreenshotInterval());
        intevalLbael.setText(String.valueOf(MainKt.getScreenshotInterval()));
        retioSlider.setExtent((int)(MainKt.getResizedDistancePressTimeRatio() * 100));
        ratioLabel.setText(String.valueOf(MainKt.getResizedDistancePressTimeRatio()));
        return mainPanel;
    }
}
