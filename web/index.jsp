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
        <h1>Inbox</h1>
        <h1>Welcome <%= username %></h1>
        <a href="logout">logout</a>
        <a href="compose.jsp">compose</a>
        <a href="trash.jsp">trash</a>
        <a href="sent.jsp">sent</a>
        <a href="draft.jsp">draft</a>
        
        <div>
            sample mails list
            <table>
                <tr><td>Sample Sender, Sample Sender</td><td><a href="viewMessage.jsp?=">Sample Subject</a></td></tr>
                <tr><td>Sample Sender, Sample Sender</td><td><a href="viewMessage.jsp?=">Sample Subject</a></td></tr>
                <tr><td>Sample Sender, Sample Sender</td><td><a href="viewMessage.jsp?=">Sample Subject</a></td></tr>
            </table>
        </div>
    </body>
</html>
