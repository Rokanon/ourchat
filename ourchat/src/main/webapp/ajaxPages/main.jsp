<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<button class="js-main-tab-button" data-tab="profile">Profile</button>
<c:if test="${!profileSession.loggedIn}">
    <button class="js-main-tab-button" data-tab="login">Login</button>
</c:if>
<div id="js-main-content"></div>

<script>
    $(".js-main-tab-button").on("click", function () {
        $("#js-main-content").load("/ajaxPages/main/" + $(this).data("tab") + ".jsp", {});
    });
</script>