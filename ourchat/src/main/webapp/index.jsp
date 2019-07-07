<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="profileSession" class="app.ourchat.profile.beans.ProfileSessionHandler" scope="session"/>
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
        
        <div id="js-error-message"></div>
        
        <c:choose>
            <c:when test="${profileSession.loggedIn}">
                <button id="js-logout-button">Logout</button>
                <script>
                    $("#js-logout-button").on("click", function () {
                        ajaxFunction("/logout", {}, "get");
                    });
                </script>
            </c:when>
        </c:choose>
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
