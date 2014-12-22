<%-- 
    Document   : messageSendingStatus
    Created on : Dec 22, 2014, 6:36:28 PM
    Author     : Mohamed Kamal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String[] failedUsers = (String[])request.getAttribute("failedUsers"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="index.jsp">back to home page</a>
        <% if(failedUsers.length == 0){ 
            out.print("Message Sent successfully");
           }
            else { 
                out.print("The following users did not receive the message");
                
                for(int i=0; i<failedUsers.length; i++){
                    out.print(failedUsers[0] + ",");
                }
           } %>
            
    </body>
</html>
