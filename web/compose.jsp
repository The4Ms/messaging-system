<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String to = (String)request.getAttribute("to");
    if(to == null) to = "";
    
    String body = (String)request.getAttribute("body");
    if(body == null) body = "";
    
    String subject = (String)request.getAttribute("subject");
    if(subject == null) subject = "";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Compose message</title>
    </head>
    <body>
        <a href="index.jsp">back to home page</a>
        
        <h1>Compose message</h1>
        <form action="sendMessage" method="POST">
            <input name="to" value="${to}" required/><br><br>
            <input name="subject" value="${subject}" required/><br><br>
            <textarea name="body" rows="20" required>${body}</textarea><br><br>
            <button type="submit">send</button>
        </form>
            <button type="button">save to drafts</button>
    </body>
</html>
