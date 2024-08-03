<%@ page import="java.util.List" %>
<%@ page import="model.ToDo" %>

<html>
<head>
    <title>ToDo List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        h1 {
            color: #333;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"] {
            padding: 10px;
            border: 2px solid #ddd;
            border-radius: 4px;
            width: 200px;
            margin-right: 10px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        form input[type="submit"] {
            margin: 0 5px;
            padding: 5px 10px;
        }
    </style>
</head>
<body>
    <div>
        <h1>ToDo List</h1>
        <form action="todo" method="post">
            <input type="text" name="task" placeholder="New task" required/>
            <input type="submit" value="Add Task"/>
        </form>
        <table border="1">
            <thead>
                <tr>
                    <th>Task</th>
                    <th>Completed</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<ToDo> todos = (List<ToDo>) request.getAttribute("todos");
                    if (todos != null) {
                        for (ToDo todo : todos) {
                %>
                <tr>
                    <td><%= todo.getTask() %></td>
                    <td><%= todo.isCompleted() ? "Yes" : "No" %></td>
                    <td>
                        <form action="todo" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= todo.getId() %>" />
                            <input type="hidden" name="_method" value="put" />
                            <input type="hidden" name="is_completed" value="<%= !todo.isCompleted() %>" />
                            <input type="submit" value="<%= todo.isCompleted() ? "Undo" : "Complete" %>" />
                        </form>
                        <form action="todo" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= todo.getId() %>" />
                            <input type="hidden" name="_method" value="delete" />
                            <input type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="3">No todos available</td></tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
