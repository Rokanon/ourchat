<%-- 
    Document   : index
    Created on : May 25, 2019, 3:16:02 PM
    Author     : dark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="resources/js/jquery-3.1.1.min.js"></script>
        <script src="resources/js/ajax.js"></script>
        <script src="resources/js/util.js"></script>
        <link rel="stylesheet" type="text/css" href="/resources/css/app.css">
        <title>Ourchat</title>
    </head>
    <body>
        <h1>Our chat!</h1>
        Chat freely with your friends and family. Its ours and its free.
        <div id="content"></div>
    </body>
    <script>
        $(document).ready(function () {
            $("#content").load("/ajaxPages/main.jsp", {}, function () {
                console.log("main content loaded");
            });
        });
    </script>
</html>
