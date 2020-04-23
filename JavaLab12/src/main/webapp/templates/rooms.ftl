<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Rooms</title>
</head>
<body>

<p>New Room</p>
<form action="/rooms" method="post">
    <button type="submit">Create</button>
</form>

<#if rooms?has_content>
    <#list rooms as room>
        <p><a href="/room/${room.id}">Комната: ${room.id}</a></p>
    </#list>
</#if>

</body>
</html>