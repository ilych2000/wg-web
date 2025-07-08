package ru.wg.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageUtils {

    private static final Logger log = Logger.getLogger(ImageUtils.class);

    public static byte[] converToGray(byte[] img) throws IOException {

        return converToByte(converToGray(converFromByte(img)), "BMP");

    }

    public static BufferedImage converToGray(BufferedImage img) throws IOException {
        if (img.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            return img;
        }

        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage ret =
                new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        op.filter(img, ret);
        return ret;
    }

    public static byte[] converToByte(BufferedImage img, String format) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ImageIO.write(img, format, ba);
        return ba.toByteArray();
    }

    public static BufferedImage converFromByte(byte[] imgBytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(imgBytes));
    }

    public static BufferedImage createResizedCopy(BufferedImage image, int scaledWidth,
            int scaledHeight) {

        if (image == null) {
            log.error("createResizedCopy image == null");
            return null;
        }

        if ((image.getWidth() < scaledWidth) && (image.getHeight() < scaledHeight)) {
            return image;
        }

        float fattX = ((float) scaledWidth / (float) image.getWidth());
        float fattY = ((float) scaledHeight / (float) image.getHeight());

        float modifier = ((fattX > fattY) ? fattY : fattX);
        scaledWidth = (int) (image.getWidth() * modifier);
        scaledHeight = (int) (image.getHeight() * modifier);

        BufferedImage scaledBI =
                new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = scaledBI.createGraphics();

        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaledBI;
    }

    public static Dimension resizeFile(File file, int maxWidth, int maxHeight) throws IOException {
        BufferedImage img = ImageIO.read(file);
        img = createResizedCopy(img, maxWidth, maxHeight);
        file.delete();
        ImageIO.write(img, "JPEG", file);

        return new Dimension(img.getWidth(), img.getHeight());
    }
}
