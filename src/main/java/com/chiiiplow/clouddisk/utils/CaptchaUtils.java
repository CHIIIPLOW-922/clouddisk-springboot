package com.chiiiplow.clouddisk.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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

    public static String generateCaptchaImage(String captchaText) throws IOException {
        int width = 115;
        int height = 35;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        Random random = new Random();

        // 设置背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 设置字体
        graphics.setFont(new Font("Fixedsys", Font.BOLD, 30));

        // 绘制扭曲的文本
        for (int i = 0; i < captchaText.length(); i++) {
            graphics.setColor(getRandomColor());
            char c = captchaText.charAt(i);
            int x = 10 + i * 20;
            int y = 25 + random.nextInt(6) - 3;
            graphics.translate(x, y);
            double theta = (random.nextDouble() - 0.5) * 0.2;
            graphics.rotate(theta);
            graphics.drawString(String.valueOf(c), 0, 0);
            graphics.rotate(-theta);
            graphics.translate(-x, -y);
        }

        // 添加干扰线
        for (int i = 0; i < 8; i++) {
            graphics.setColor(getRandomColor());
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }

        // 添加噪点
        for (int i = 0; i < width * height * 0.02; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomColor().getRGB();
            bufferedImage.setRGB(x, y, rgb);
        }

        graphics.dispose();
        byte[] imageBytes = null;
        // 将 BufferedImage 转换为字节数组
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
        }
        // Write the image to the response output stream

        // 将字节数组编码为 Base64 字符串
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // 构建 data:image 格式字符串
        String dataImage = "data:image/png;base64," + base64Image;
        return dataImage;
    }

    private static Color getRandomColor() {
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = 0.7f;
        float brightness = 0.9f;
        return Color.getHSBColor(hue, saturation, brightness);
    }
}