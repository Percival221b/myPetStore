package org.example.petstore.util;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CaptchaUtil {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 生成一个随机验证码
    public static String generateCaptchaCode(int length) {
        Random random = new Random();
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            captchaCode.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return captchaCode.toString();
    }

    // 生成验证码图片
    public static void generateCaptchaImage(String captchaCode, OutputStream out) throws IOException {
        int width = 160;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 设置背景颜色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 设置字体
        g.setFont(new Font("Arial", Font.PLAIN, 30));

        // 随机画一些干扰线
        Random rand = new Random();
        g.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int x2 = rand.nextInt(width);
            int y2 = rand.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // 写验证码
        g.setColor(Color.BLACK);
        g.drawString(captchaCode, 30, 30);

        // 释放资源
        g.dispose();

        // 输出图片
        ImageIO.write(image, "JPEG", out);
    }
}
