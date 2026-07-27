package org.py.vedge;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Image {

    private static final HashMap<String,Image> images = new HashMap<>();

    private javafx.scene.image.Image image;
    private BufferedImage bufferedImage;
    private String path;

    public Image(String path) {
        this.path = path;
        try {
            InputStream stream = Resources.asInputStream(path);
            image = new javafx.scene.image.Image(stream);
            stream.close();
        } catch(Exception e) {
            image = null;
        }
    }

    public Image(javafx.scene.image.Image image) {
        this.image = image;
    }
    public Image(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Image(InputStream stream) {
        try {
            image = new javafx.scene.image.Image(stream);
        } catch(Exception ignored) {
            image = null;
        }
    }

    public void make(String path) {

    }

    public Image buffer() throws IOException {
        InputStream stream = Resources.asInputStream(path);
        bufferedImage = ImageIO.read(stream);
        stream.close();
        return this;
    }
    public Image buffer_safe() {
        try {
            return buffer();
        } catch(IOException e) {
            bufferedImage = null;
            ErrorHandler.silent(e);
        }
        return this;
    }

    public javafx.scene.image.Image getImage() {
        return image;
    }
    public BufferedImage getBuffered() {
        return bufferedImage;
    }

}
