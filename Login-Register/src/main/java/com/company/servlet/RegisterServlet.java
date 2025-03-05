package com.company.servlet;

import java.io.IOException;

import com.company.dao.UserDaoImpl;
import com.company.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


@WebServlet("/RegisterServlet")
@MultipartConfig(maxFileSize = 2 * 1024 * 1024)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String stateIdStr = request.getParameter("state");
        String cityIdStr = request.getParameter("city");
        Part imagePart = request.getPart("image");

        if (name == null || email == null || password == null || phone == null || dob == null || stateIdStr == null || cityIdStr == null) {
            response.sendRedirect("register.jsp?error=missing_fields");
            return;
        }

        int userType = 0;
        int stateId = 0;
        int cityId = 0;

        try {
            stateId = Integer.parseInt(stateIdStr);
            cityId = Integer.parseInt(cityIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("register.jsp?error=invalid_input");
            return;
        }

        // 🔹 Hashing password before storing it in the database
      //  String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Creating user object with hashed password
        User user = new User(0, name, email, password, phone, dob, userType, stateId, cityId, null);
        UserDaoImpl userDao = new UserDaoImpl();

        if (userDao.registerUser(user)) {
            response.sendRedirect("register.jsp?success=1");
        } else {
            response.sendRedirect("register.jsp?error=insert_failed");
        }
    }
}
