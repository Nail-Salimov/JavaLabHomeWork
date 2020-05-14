<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile</title>
</head>
<body>
<p><a href="/store"><@spring.message "profile.page.store"/></a></p>
<p><a href="/my_orders"><@spring.message "profile.page.myOrders"/></a></p>
<p><@spring.message "profile.page.name"/>: ${userData.name}</p>
<p><@spring.message "profile.page.email"/>: ${userData.mail}</p>
<p><@spring.message "profile.page.role"/>: ${userData.role}</p>
<p><@spring.message "profile.page.address"/>:
    <#if userData.address?has_content>
        ${userData.address}
        <#else>
        не задан
    </#if>
</p>


<a href="/profile/edit"><@spring.message "profile.page.edit"/></a>
</body>
</html>