<%-- 
    Document   : test
    Created on : Jun 27, 2019, 12:00:33 AM
    Author     : dark
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="userPublicBean" class="ourchat.ourchat.user.publicBeans.UserPublicBean" scope="request"/>

<c:choose>
    <c:when test="${userPublicBean.profile eq null}">
        <input id="js-profile-name" value=""/>
        <button id="js-button-make-profile">Make profile</button>
        <script>
            $("#js-button-make-profile").on("click", function () {
                let params = {};

                params.userId = "${userPublicBean.user.id}";
                params.name = $("#js-profile-name").val();

                ajaxMake("/profile", params);

            });
        </script>
    </c:when> 
    <c:otherwise>
        <input id="js-profile-name" value="${userPublicBean.profile.name}"/>
        <button id="js-button-edit-profile">Edit profile</button>
        <script>
            $("#js-button-edit-profile").on("click", function () {
                let params = {};

                params.id = "${userPublicBean.profile.id}";
                params.name = $("#js-profile-name").val();

                ajaxEdit("/profile", params);

            });
        </script>
    </c:otherwise>
</c:choose> 
