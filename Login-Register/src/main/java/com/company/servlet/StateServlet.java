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

@WebServlet("/StateServlet")
public class StateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter();
             Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM states ORDER BY name ASC");
             ResultSet rs = ps.executeQuery()) {

            out.println("<option value=''>Select State</option>");
            while (rs.next()) {
                int stateId = rs.getInt("id");
                String stateName = rs.getString("name");
                out.println("<option value='" + stateId + "'>" + stateName + "</option>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading states");
        }
    }
}