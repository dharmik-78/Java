package com.company.servlet;

import java.io.IOException;
import com.company.dao.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");

        UserDaoImpl userDao = new UserDaoImpl();

        if (userDao.emailExists(email)) {
            // Update the password in the database
            userDao.updatePassword(email, newPassword);

            // Redirect with success message
            response.sendRedirect("forgot_password.jsp?success=1");
        } else {
            // Redirect with error message
            response.sendRedirect("forgot_password.jsp?error=email_not_found");
        }
    }
}
