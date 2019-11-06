<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

${profileSession.loggedIn}
<c:choose>
    <c:when test="${profileSession.loggedIn}">
        ${profileSession.profile.name}

        Send message: 
        <textarea name="message">
            
        </textarea>
        <button id="js-send">Send</button>
        <!--<button id="js-receive">Receive</button>-->

        <script>
            $("#js-send").on("click", function () {
                let params = {};
                let message = $("textarea[name='message']").val().trim();
                console.log("message: " + message);

                params.message = message;
                ajaxEdit("/send/message", params);
            });
//            $("#js-receive").on("click", function () {
//                let params = {};
//                
//                ajaxView("/receive/message", params);
//            });
        </script>

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

        Send message: 
        <textarea name="message">
            
        </textarea>
        <button id="js-send">Send</button>

        <script>
            $("#js-send").on("click", function () {
                let params = {};
                let message = $("textarea[name='message']").val().trim();
                console.log("message: " + message);

                params.message = message;
                ajaxEdit("/send/message", params);
            });
        </script>

    </c:otherwise>
</c:choose>


