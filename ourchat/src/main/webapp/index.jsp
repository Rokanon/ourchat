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
        <script src="resources/js/jquery-3.1.1.min.js"></script>
        <title>Ourchat</title>
    </head>
    <body>
        <h1>Our chat!</h1>
        Chat freely with your friends and family. Its ours and its free.
        <div>
            ${userPublicBean.user.toJson()}
        </div>

        <button id="js-button-make-profile">Make profile</button>

        <script>
            $("#js-button-make-profile").on("click", function () {
                let params = {};

                params.userId = "${userPublicBean.user.id}";

                $.ajax({
                    url: "/ourchat/make/profile",
                    data: params,
                    type: "get",
                    
                    beforeSend: function (xhr) {
                        console.log("before");
                    },
                    complete: function (jqXHR, textStatus) {
                        console.log("complete");
                    },
                    success: function (data, textStatus, jqXHR) {
                        console.log("success");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("error");
                    }
                })
            });
        </script>

    </body>
</html>
