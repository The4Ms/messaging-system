<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<% String username = (String)session.getAttribute("username"); 

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
            <input name="to" required/>
            <input name="subject" required/>
            <input name="body" required/>
            <button type="submit">send</button>
        </form>
            <button type="button">save to drafts</button>
    </body>
</html>
