<%-- 
    Document   : index
    Created on : May 25, 2019, 3:16:02 PM
    Author     : dark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="userPublicBean" class="ourchat.ourchat.user.publicBeans.UserPublicBean" scope="request"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ourchat</title>
    </head>
    <body>
        <h1>Our chat!</h1>
        Chat freely with your friends and family. Its ours and its free.
        <div>
            ${userPublicBean.user.toJson()}
        </div>
    </body>
</html>
