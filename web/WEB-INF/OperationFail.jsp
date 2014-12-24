<%-- 
    Document   : OperationFail.jsp
    Created on : Dec 23, 2014, 6:43:16 PM
    Author     : Mohamed Kamal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>BigM</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
  </head>

  <body>
    
    <div class="container-fluid">
      <div class="col-md-4 col-md-offset-4" style="margin-top:50px">

          <div class="alert alert-danger">
            <h3>The requested operation failed</h3>
            <p>
                What went wrong: ${errorMsg}
            </p>
            <a href="javascript:history.back()">Go back</a>
          </div>

      </div>
    </div>
  </body>
</html>
