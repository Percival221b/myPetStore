package org.example.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.petstore.domain.Account;
import org.example.petstore.service.AccountService;

import java.io.IOException;

public class CreateAccountServlet extends HttpServlet {
    private AccountService accountService = new AccountService();

    private static final String TO_SIGNON_FORM = "/WEB-INF/jsp/account/signonForm.jsp";
    private static final String NEW_ACCOUNT_FORM = "/WEB-INF/jsp/account/newAccountForm.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");

        String languagePreference = request.getParameter("languagePreference");
        String favouriteCategoryId = request.getParameter("favouriteCategoryId");
        boolean listOption = request.getParameter("listOption") != null;
        boolean bannerOption = request.getParameter("bannerOption") != null;

        if (username == null || password == null || !password.equals(repeatedPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match or username is empty.");
            request.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(request, response);
            return;
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhone(phone);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        account.setLanguagePreference(languagePreference);
        account.setFavouriteCategoryId(favouriteCategoryId);
        account.setListOption(listOption);
        account.setBannerOption(bannerOption);
        try {
            accountService.insertAccount(account);

            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            request.getRequestDispatcher(TO_SIGNON_FORM).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to create account: " + e.getMessage());
            request.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/account/newAccountForm.jsp").forward(request, response);
    }
}
