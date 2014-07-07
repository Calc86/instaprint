package com.gui;

import com.instaprint.Config;
import com.instaprint.Controller;
import com.printer.IrfanViewPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

/**
 * Created by calc on 04.07.14.
 *
 */
public class MainWindow implements ImageContainer {
    private static final int GAP = 10;
    private static final int TIMEOUT = 5000;
    private JPanel controlPanel;
    private JPanel imagesPanel;
    private JScrollPane imagesScroll;
    private JFrame frame;

    private Config config = null;

    private static Controller controller;
    private ImageContainer ic;

    /*
    +---------------------------+
    | settings start pause stop | - TOP-FlowLayout
    +---------------------------+
    |image image image image    | - Center-FlowLayout
     */

    public void createFrame(){
        ic = this;
        frame = new JFrame("instaprint");
        //JFrame.EXIT_ON_CLOSE
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        //create
        createControlPanel();
        createImagesPanel();

        background.add(BorderLayout.NORTH, controlPanel);
        //background.add(BorderLayout.CENTER, imagesPanel);
        background.add(BorderLayout.CENTER, imagesScroll);

        frame.getContentPane().add(background);

        //frame.setBounds(50, 50, 300, 300);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.pack();
        frame.setVisible(true);

        config = Config.unserialize();
        if(config == null){
            JOptionPane.showMessageDialog(frame, "Can't restore config from file", "Config load error", JOptionPane.ERROR_MESSAGE);
            config = new Config();
        }
    }

    private void showError(String message){
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createControlPanel(){
        controlPanel = new JPanel();

        final JButton startButton = new JButton("start");
        final JButton stopButton = new JButton("stop");
        stopButton.setEnabled(false);
        final JButton pauseButton = new JButton("pause");
        pauseButton.setEnabled(false);
        final JButton settingsButton = new JButton("settings");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(config.getMainTag().equals("")){
                    showError("Main tag not defined");
                    return;
                }
                if(config.getAccessToken().equals("")){
                    showError("Access token ton defined");
                    return;
                }
                File f = new File(config.getIrfanViewPath());
                if(!f.exists()){
                    showError("IrfanView not found");
                    return;
                }

                startButton.setEnabled(false);
                settingsButton.setEnabled(false);
                //Config config = new Config();
                long time = (new Date().getTime())/ 1000 - 20*60;
                System.out.println("start time:" + time);
                controller = new Controller(config, new IrfanViewPrinter(config.getIrfanViewPath()), TIMEOUT, time, ic);
                controller.start();
                stopButton.setEnabled(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                controller.stop();
                controller = null;
                startButton.setEnabled(true);
                settingsButton.setEnabled(true);
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigDialog dialog = new ConfigDialog(frame, config);

                dialog.create();
            }
        });

        controlPanel.add(settingsButton);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
    }

    private void createImagesPanel(){
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new WrapLayout());
        imagesScroll = new JScrollPane(imagesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        //imagesScroll.add(imagesPanel);
    }

    public void addImage(String imageUrl){
        ImageComponent ic = new ImageComponent(imageUrl);
        imagesPanel.add(ic);
        imagesPanel.revalidate();
    }
}
