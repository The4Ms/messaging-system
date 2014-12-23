<%-- 
    Document   : Inbox
    Created on : Dec 20, 2014, 9:53:21 AM
    Author     : Mohamed Kamal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ia.project.mmm.model.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% String username = (String)session.getAttribute("username"); 

   String title = (String)request.getAttribute("title");
   Message[] messages = (Message[])request.getAttribute("messages");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to messaging system</title>
    </head>
    <body>
        <h1><%= title %></h1>
        <h1>Welcome <%= username %></h1>
        <a href="profile.jsp">profile</a>
        <a href="logout">logout</a>
        <a href="compose.jsp">compose</a>
        <a href="index.jsp?view=inbox">inbox</a>
        <a href="index.jsp?view=trash">trash</a>
        <a href="index.jsp?view=sent">sent</a>
        <a href="index.jsp?view=drafts">drafts</a>
        
        <br><br>
        
        <div>
            <div>
                <form action="index.jsp" method="GET">
                    <input hidden name="view" value="${title}" />
                    filter:<br>
                    from: <input name="filter.from" value="${param['filter.from']}" /><br>
                    to: <input name="filter.to" /><br>
                    subject: <input name="filter.subject" /><br>
                    between this date: <input type="date" name="filter.startDate" /><br>
                    and this date: <input type="date" name="filter.endDate" /><br>
                    <button type="submit">filter</button>
                    
                    <br><br>
                </form>
            </div>
            
            mails:
            <table>
                <c:forEach var="komalo" items="${messages}">
                    <tr>
                        <td>
                            ${komalo.sender.username}
                        </td>
                        <td>
                            <c:forEach var="rec" items="${komalo.receivers}">            
                                    ${rec.username},
                            </c:forEach>
                        </td>
                        <td>
                            <a href="viewMessage?id=${komalo.id}">${komalo.subject}</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
