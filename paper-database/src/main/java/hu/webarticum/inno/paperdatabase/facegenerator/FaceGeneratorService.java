package hu.webarticum.inno.paperdatabase.facegenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Singleton;

@Singleton
public class FaceGeneratorService {

    private static final int IMAGE_WIDTH = 350;
    private static final int IMAGE_HEIGHT = 500;

    private static final int THUMB_WIDTH = 100;
    private static final int THUMB_HEIGHT = (IMAGE_HEIGHT * THUMB_WIDTH) / IMAGE_WIDTH;
    
    public CompletableFuture<BufferedImage> renderFace() {
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        g2d.setColor(Color.GRAY);
        int headSize = (IMAGE_WIDTH * 2) / 3;
        int headX1 = (IMAGE_WIDTH - headSize) / 2;
        int headY1 = IMAGE_HEIGHT - ((headSize * 5) / 3);
        g2d.fillOval(headX1, headY1, headSize, headSize);
        int bodySizeX = (IMAGE_WIDTH * 9) / 10;
        int bodySizeY = (IMAGE_WIDTH * 4) / 5;
        int bodyX1 = (IMAGE_WIDTH - bodySizeX) / 2;
        int bodyY1 = IMAGE_HEIGHT - (bodySizeY / 2);
        g2d.fillOval(bodyX1, bodyY1, bodySizeX, bodySizeY);
        return CompletableFuture.completedFuture(image);
    }

    public CompletableFuture<BufferedImage> renderFaceThumb() {
        return renderFace().thenApply(b -> {
            BufferedImage resizedImage = new BufferedImage(THUMB_WIDTH, THUMB_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) resizedImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(b, 0, 0, THUMB_WIDTH, THUMB_HEIGHT, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            return resizedImage;
        });
    }
    
}
