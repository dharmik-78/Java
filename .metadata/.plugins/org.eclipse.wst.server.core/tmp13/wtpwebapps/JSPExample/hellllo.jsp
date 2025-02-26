<%@ page import="java.util.*" %>
<html>
<body>
    <%!
        Date theDate = new Date();
        Date getDate() {
            System.out.println("In getDate() method");
            return theDate;
        }
    %>
    Hello! The time is now <%= getDate() %>
</body>
</html>