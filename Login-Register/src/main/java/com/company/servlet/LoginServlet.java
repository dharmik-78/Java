package com.company.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.company.dao.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Map<String, Integer> failedAttempts = new HashMap<>();
    private static final Map<String, Long> blockedUsers = new HashMap<>();

    private static final int MAX_ATTEMPTS = 3;
    private static final long BLOCK_TIME = 60 * 1000;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDaoImpl userDao = new UserDaoImpl();
        HttpSession session = request.getSession();

        if (blockedUsers.containsKey(email)) {
            long blockTime = blockedUsers.get(email);
            long currentTime = System.currentTimeMillis();

            if (currentTime - blockTime < BLOCK_TIME) {
                response.sendRedirect("login.jsp?error=blocked");
                return;
            } else {
                blockedUsers.remove(email);
                failedAttempts.remove(email);
            }
        }

        if (userDao.isValidUser(email, password)) {
            session.setAttribute("userEmail", email);
            failedAttempts.remove(email);
            response.sendRedirect("welcome.jsp?success=1");
        } else {
            failedAttempts.put(email, failedAttempts.getOrDefault(email, 0) + 1);

            if (failedAttempts.get(email) >= MAX_ATTEMPTS) {
                blockedUsers.put(email, System.currentTimeMillis());
                response.sendRedirect("login.jsp?error=blocked");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        }
    }
}
