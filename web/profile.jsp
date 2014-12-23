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
        <h1>Profile</h1>
        <form action="updateProfile" method="POST">
            <div>Fullname <input name="fullname" required/></div>
            <div>Username ${username}
            <div>Password<input name="password" type="password" required/></div>
            <button type="submit">update</button>
        </form>
    </body>
</html>
