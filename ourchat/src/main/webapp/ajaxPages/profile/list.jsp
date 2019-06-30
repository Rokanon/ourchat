<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="profile" tagdir="/WEB-INF/tags/profile/" %>

<jsp:useBean id="profileList" class="ourchat.ourchat.user.publicBeans.ProfileList" scope="request"/>

<c:forEach items="${profileList.list}" var="profile">
    <profile:profile profile="${profile}"/>
</c:forEach>
