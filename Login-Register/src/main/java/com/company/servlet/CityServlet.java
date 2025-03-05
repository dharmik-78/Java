package com.company.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.company.util.DbUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CityServlet")
public class CityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String stateId = request.getParameter("state");

        if (stateId == null || stateId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid State ID");
            return;
        }

        try (PrintWriter out = response.getWriter();
             Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM cities WHERE state_id = ?")) {
            ps.setInt(1, Integer.parseInt(stateId));
            ResultSet rs = ps.executeQuery();

            out.println("<option value=''>Select City</option>");
            while (rs.next()) {
                out.println("<option value='" + rs.getInt("id") + "'>" + rs.getString("name") + "</option>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading cities");
        }
    }
}