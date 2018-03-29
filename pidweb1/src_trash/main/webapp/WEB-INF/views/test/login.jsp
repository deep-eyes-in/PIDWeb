<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="src.main.resources" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <title>JSP/JSTL i18n demo</title>
    </head>
    <body>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="nl" ${language == 'nl' ? 'selected' : ''}>Nederlands</option>
                <option value="fr" ${language == 'fr' ? 'selected' : ''}>Français</option>
            </select>
        </form>
        
        <s:message code="cours.creer"/>
        <fmt:message key="cours.creer" />
        
        
        <form method="post">
            <label for="username"><fmt:message key="cours.creer" />:</label>
            <input type="text" id="username" name="username">
            <br>
            <label for="password"><fmt:message key="login.label.password" />:</label>
            <input type="password" id="password" name="password">
            <br>
            <fmt:message key="login.button.submit" var="buttonValue" />
            <input type="submit" name="submit" value="${buttonValue}">
        </form>
        
        <c:out value="${cours.creer}" />
        
        
    </body>
</html>