<%-- 
    Document   : viewMessage
    Created on : Dec 23, 2014, 5:07:42 PM
    Author     : Mohamed Kamal
--%>

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
        
        <p>
            Receivers:
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
