<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="profilePublicBean" class="ourchat.user.publicBeans.ProfilePublicBean" scope="request"/>
<jsp:setProperty name="profilePublicBean" property="userId" value="${param.userId}"/>

<c:choose>
    <c:when test="${profilePublicBean.profile eq null}">
        <input id="js-profile-name" value=""/>
        <button id="js-button-make-profile">Make profile</button>
        <script>
            $("#js-button-make-profile").on("click", function () {
                let params = {};

                params.userId = "${param.userId}";
                params.name = $("#js-profile-name").val();

                ajaxMake("/profile", params);

            });
        </script>
    </c:when> 
    <c:otherwise>
        Profile already exists
    </c:otherwise>
</c:choose>