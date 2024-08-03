package model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo")
public class ToDoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM todos";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<ToDo> todoList = new ArrayList<>();
            while (rs.next()) {
                ToDo todo = new ToDo();
                todo.setId(rs.getInt("id"));
                todo.setTask(rs.getString("task"));
                todo.setCompleted(rs.getBoolean("is_completed"));
                todoList.add(todo);
            }
            request.setAttribute("todos", todoList);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB error", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");
        if (method == null) {
            addTask(request, response);
        } else if (method.equalsIgnoreCase("put")) {
            updateTask(request, response);
        } else if (method.equalsIgnoreCase("delete")) {
            deleteTask(request, response);
        }
    }

    private void addTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String task = request.getParameter("task");
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO todos (task) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, task);
            stmt.executeUpdate();
            // Redirect to the doGet method to fetch and display the updated list of tasks
            response.sendRedirect("todo");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB error", e);
        }
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isCompleted = Boolean.parseBoolean(request.getParameter("is_completed"));
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE todos SET is_completed = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, isCompleted);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            response.sendRedirect("todo");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB error", e);
        }
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            throw new ServletException("ID parameter is missing or empty");
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid ID format", e);
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM todos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            response.sendRedirect("todo");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB error", e);
        }
    }
}
