<%-- 
    Document   : Inbox
    Created on : Dec 20, 2014, 9:53:21 AM
    Author     : Mohamed Kamal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% String username = (String)session.getAttribute("username"); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Inbox goes here</h1>
        <h1>Welcome <%= username %></h1>
        <a href="logout"></a>
    </body>
</html>
