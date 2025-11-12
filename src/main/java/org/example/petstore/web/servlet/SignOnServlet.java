package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.domain.Product;
import org.example.petstore.service.AccountService;
import org.example.petstore.service.CatalogService;

import java.io.IOException;
import java.util.List;


public class SignOnServlet extends HttpServlet {

    private static final String TOsignOnForm = "/WEB-INF/account/signonForm.jsp";

    private String username;
    private String password;

    private String signOnMsg;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username=req.getParameter("username");
        this.password=req.getParameter("password");

        //校验用户输入正确性
        if(!validate()){
            req.setAttribute("signOnMsg",this.signOnMsg);
            req.getRequestDispatcher(TOsignOnForm).forward(req,resp);
        }
        else{
            AccountService accountService=new AccountService();
            Account loginAccount=accountService.getAccount(username,password);
            if(loginAccount==null){
                this.signOnMsg="用户名或密码错误。";
                req.getRequestDispatcher(TOsignOnForm).forward(req,resp);
            }
            else{
                loginAccount.setPassword(null);
                HttpSession session=req.getSession();
                session.setAttribute("loginAccount",loginAccount);

                if(loginAccount.isListOption()){
                    CatalogService catalogService=new CatalogService();
                    List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
                    session.setAttribute("myList",myList);
                }
                resp.sendRedirect("mainForm");
            }
        }
    }

    private boolean validate(){
        if(this.username==null ||this.username.equals("")){
            this.signOnMsg="用户名不能为空。";
            return false;
        }
        if(this.password==null ||this.password.equals("")){
            this.signOnMsg="密码不能为空。";
            return false;
        }
        return true;
    }
}

