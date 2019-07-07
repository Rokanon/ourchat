<%-- 
    Document   : login
    Created on : Jul 7, 2019, 10:08:13 PM
    Author     : dark
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<input name="mail" value=""/>
<input name="password" type="password" value=""/>
<button type="button" id="js-login-button">Log in</button>

<script>
    $("#js-login-button").on("click", function() {
        let params = {};
        params.mail = $("input[name='mail']").val();
        params.password = $("input[name='password']").val();
        ajaxView("/login", params);
    });
</script>

