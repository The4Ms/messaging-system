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
        <h1>Compose message</h1>
        <h1>Welcome <%= username %></h1>
        <a href="logout">logout</a>
        <a href="compose.jsp">compose</a>
        <a href="index.jsp?view=trash">trash</a>
        <a href="index.jsp?view=sent">sent</a>
        <a href="index.jsp?view=drafts">drafts</a>
        
        <form action="login" method="POST">
            <input name="username"/>
            <input name="password"/>
            <button type="submit">login</button>
        </form>
    </body>
</html>
