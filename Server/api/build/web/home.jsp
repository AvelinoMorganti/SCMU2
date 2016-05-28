
<%@page import="java.util.List"%>
<%@page import="classes.Account"%>
<%@page import="DAO.DAOAccount"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <%@include file="util/header.jsp" %>
        <h1>Welcome <% out.print(username);%>!</h1>
    </body>
</html>