<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Edit Profile</title>
</head>
<body>
<nav>
    <a href="/store"><@spring.message "profile.page.store"/></a>
</nav>
<p><@spring.message "edit.page.editProfile"/></p>
<form action="/profile/edit" method="post">
    <p><@spring.message "profile.page.name"/>: <input name="name" type="text" value="${userData.name}"></p>
    <#if userData.address?has_content>
        <p><@spring.message "profile.page.address"/>: <input name="address" type="text" value="${userData.address}"></p>
    <#else>
        <p><@spring.message "profile.page.address"/>: <input name="address" type="text"></p>
    </#if>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit"><@spring.message "edit.page.save"/></button>
</form>
</body>
</html>