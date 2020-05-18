<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Info</title>
</head>
<body>
<p>${userData.mail}</p>
<p>Количество загруженных файлов: ${info.getCount()}</p>

<p>Видео в формате mp4, загруженные пользователем: </p>
<ul>
    <#if userData.mp4CountDocuments?has_content>
        <#list userData.mp4CountDocuments as mp4Document>
            <li><a href="/file/${mp4Document.id}">${mp4Document.originalName}</a></li>
        </#list>
    </#if>
</ul>
</body>
</html>