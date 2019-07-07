<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

${profileSession.loggedIn}
<c:choose>
    <c:when test="${profileSession.loggedIn}">
        ${profileSession.profile.name}
    </c:when>
    <c:otherwise>
        <button class="js-profile-tab-button" data-tab="create">Create</button>
        <button class="js-profile-tab-button" data-tab="list">List</button>
        <div id="js-profile-content"></div>


        <script>
            $(".js-profile-tab-button").on("click", function () {
        //        if (exists($(this).data("id")))
                $("#js-profile-content").load("/ajaxPages/profile/" + $(this).data("tab") + ".jsp", {});
            });
        </script>
    </c:otherwise>
</c:choose>


