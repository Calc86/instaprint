package com.gui;

import com.instaprint.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by calc on 04.07.14.
 *
 */
public class ConfigDialog extends JDialog{
    private static final int GAP = 10;
    private static final int COLUMNS = 31;

    private final Config config;
    private JTextField token;
    private JTextField irfan;
    private JTextField mainTag;
    private JTextField secondTag;

    public ConfigDialog(Frame owner, Config config) {
        super(owner, "Config", true);

        if(config == null)
            config = new Config();
        this.config = config;
    }

    public void create(){
        GridLayout layout = new GridLayout(4, 2, 8, 8);

        BorderLayout borderLayout = new BorderLayout(GAP, GAP);

        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        panel.setLayout(borderLayout);

        panel.add(createLabels(), BorderLayout.WEST);
        panel.add(createTextFields(), BorderLayout.CENTER);
        panel.add(createButtons(), BorderLayout.SOUTH);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    private JPanel createTextFields(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        token = new JTextField(COLUMNS);
        token.setText(config.getAccessToken());

        irfan = new JTextField(COLUMNS);
        irfan.setText(config.getIrfanViewPath());
        irfan.setEnabled(false);

        mainTag = new JTextField(COLUMNS);
        mainTag.setText(config.getMainTag());

        secondTag = new JTextField(COLUMNS);
        secondTag.setText(config.getSecondTag());

        panel.add(token);
        panel.add(irfan);
        panel.add(mainTag);
        panel.add(secondTag);

        return panel;
    }

    private JPanel createLabels(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel token = new JLabel("Access token*:");
        JLabel irfan = new JLabel("Path to i_view32.exe*:");
        JLabel mainTag = new JLabel("Main tag*:");
        JLabel secondTag = new JLabel("Control tag:");

        panel.add(token);
        panel.add(irfan);
        panel.add(mainTag);
        panel.add(secondTag);

        return panel;
    }

    private JPanel createButtons(){
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, GAP, 0));
        JButton buttonSave = new JButton("Save");
        JButton buttonClose = new JButton("Close");

        buttonsPanel.add(buttonSave);
        buttonsPanel.add(buttonClose);

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setAccessToken(token.getText());
                config.setIrfanViewPath(irfan.getText());
                config.setMainTag(mainTag.getText());
                config.setSecondTag(secondTag.getText());

                if(!Config.serialize(config))
                    System.out.println("Не удалось сохранить конфиг");
                dispose();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        return buttonsPanel;
    }
}
