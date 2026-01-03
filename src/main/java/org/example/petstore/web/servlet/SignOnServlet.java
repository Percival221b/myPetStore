package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Product;
import org.example.petstore.service.AccountService;
import org.example.petstore.service.CatalogService;

import java.io.IOException;
import java.util.List;

public class SignOnServlet extends HttpServlet {

    private static final String TOsignOnForm = "/WEB-INF/jsp/account/signonForm.jsp";
    private String username;
    private String password;
    private String signOnMsg;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username = req.getParameter("username");
        this.password = req.getParameter("password");
        String enteredCaptcha = req.getParameter("captcha");  // 获取用户输入的验证码

        // 从 session 中获取正确的验证码
        HttpSession session = req.getSession();
        String correctCaptcha = (String) session.getAttribute("captcha");

        // 校验验证码
        if (enteredCaptcha == null || !enteredCaptcha.equalsIgnoreCase(correctCaptcha)) {
            this.signOnMsg = "验证码错误。";
            req.setAttribute("signOnMsg", this.signOnMsg);
            req.getRequestDispatcher(TOsignOnForm).forward(req, resp);
            return;
        }

        // 校验用户输入正确性
        if (!validate()) {
            req.setAttribute("signOnMsg", this.signOnMsg);
            req.getRequestDispatcher(TOsignOnForm).forward(req, resp);
        } else {
            AccountService accountService = new AccountService();
            Account loginAccount = accountService.getAccount(username, password);
            if (loginAccount == null) {
                this.signOnMsg = "用户名或密码错误。";
                req.setAttribute("signOnMsg", this.signOnMsg);
                req.getRequestDispatcher(TOsignOnForm).forward(req, resp);
            } else {
                // 设置密码为 null 安全存储
                loginAccount.setPassword(null);
                session.setAttribute("loginAccount", loginAccount);

                // 如果用户启用了 MyList
                if (loginAccount.isListOption()) {
                    CatalogService catalogService = new CatalogService();
                    List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
                    session.setAttribute("myList", myList);
                }

                // 使用绝对路径进行重定向
                resp.sendRedirect(req.getContextPath() + "/mainForm");
            }
        }
    }

    private boolean validate() {
        if (this.username == null || this.username.equals("")) {
            this.signOnMsg = "用户名不能为空。";
            return false;
        }
        if (this.password == null || this.password.equals("")) {
            this.signOnMsg = "密码不能为空。";
            return false;
        }
        return true;
    }
}
