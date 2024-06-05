package com.chiiiplow.clouddisk.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtils {

    public static String generateCaptchaText(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captchaText.toString();
    }

    public static BufferedImage generateCaptchaImage(String captchaText) {
        int width = 130;
        int height = 40;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setFont(new Font("Arial", Font.LAYOUT_NO_LIMIT_CONTEXT, 30));
        graphics.setColor(Color.getHSBColor(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));

        graphics.drawString(captchaText, 10, 35);

        // 添加干扰线
        Random random = new Random();
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }

        graphics.dispose();
        return bufferedImage;
    }
}