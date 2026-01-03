package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.util.CaptchaUtil;

import java.io.IOException;
import java.io.OutputStream;


public class CaptchaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成验证码
        String captchaCode = CaptchaUtil.generateCaptchaCode(6);  // 6位验证码
        // 将验证码保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaCode);

        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 生成验证码图片并输出
        try (OutputStream out = response.getOutputStream()) {
            CaptchaUtil.generateCaptchaImage(captchaCode, out);
        }
    }
}
