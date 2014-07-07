package com.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by calc on 04.07.14.
 */
public class ImageComponent extends JPanel {
    private BufferedImage image;

    public ImageComponent(String imageUrl) {
        super();

        URL url;
        try {
            url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        try {
            image = ImageIO.read(url);
            System.out.println("width: " + image.getWidth() + ", height: " + image.getHeight());
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            //setSize(image.getWidth(), image.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
    }
}
