<%-- 
    Document   : viewMessage
    Created on : Dec 23, 2014, 5:07:42 PM
    Author     : Mohamed Kamal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ia.project.mmm.model.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% String username = (String)session.getAttribute("username"); 
   Message message = (Message)request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="index.jsp">back to home page</a>
        
        <div>
            <a href="doMessage?type=archive&id=${message.id}">trash</a>
            <a href="doMessage?type=delete&id=${message.id}">delete for ever</a>
            <a href="doMessage?type=reply&id=${message.id}">reply</a>
            <a href="doMessage?type=forward&id=${message.id}">forward</a>
        </div>

        <p>
            From: ${message.sender.username}
        </p>
        <p>
            To:
            <c:forEach var="receiver" items="${message.receivers}">
                 ${receiver.username},
            </c:forEach>
        </p>
        <p>
            Subject: ${message.subject}
        </p>
        
        <p>
            Body:<br>
            <%= message.getBody().replace("\n", "<br>") %>
        </p>
    </body>
</html>
