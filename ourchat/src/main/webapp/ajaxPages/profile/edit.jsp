<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="profilePublicBean" class="ourchat.ourchat.user.publicBeans.ProfilePublicBean" scope="request"/>
<jsp:setProperty name="profilePublicBean" property="userId" value="${param.userId}"/>

<c:choose>
    <c:when test="${profilePublicBean.profile ne null}">
        <input id="js-profile-name" value="${profilePublicBean.profile.name}"/>
        <button id="js-button-edit-profile">Edit profile</button>
        <script>
            $("#js-button-edit-profile").on("click", function () {
                let params = {};

                params.id = "${profilePublicBean.profile.id}";
                params.name = $("#js-profile-name").val();

                ajaxEdit("/profile", params);

            });
        </script>
    </c:when> 
    <c:otherwise>
        Profile does not exists
    </c:otherwise>
</c:choose>
