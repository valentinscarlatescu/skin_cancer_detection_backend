package com.scdbackend.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void main(String[] args) {
        File folder = new File("src/main/resources/img/productold");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String inputImagePath = listOfFiles[i].getPath();
                System.out.println(inputImagePath);

                try {
                    // resize to a fixed width (not proportional)
                    int scaledWidth = 142;
                    int scaledHeight = 108;
                    String outputImagePath = "src/main/resources/img/productnew/" + listOfFiles[i].getName();
                    ImageResizer.resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);

                } catch (IOException ex) {
                    System.out.println("Error resizing the image.");
                    ex.printStackTrace();
                }
            }
        }


    }

}
