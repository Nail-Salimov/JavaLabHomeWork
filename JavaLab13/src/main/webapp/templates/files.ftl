<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Documents</title>

    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <title>Document</title>
    <script>
        function sendFile() {

            let formData = new FormData();
            let files = ($('#file'))[0]['files'];
            [].forEach.call(files, function (file, i, files) {
                formData.append("file", file);
            });

            $.ajax({
                type: "POST",
                url: "http://localhost:8080/file",
                data: formData,
                processData: false,
                contentType: false
            })
                .done(function (response) {
                    alert(response)
                })
                .fail(function () {
                    alert('Error')
                });
        }
    </script>

</head>
<body>
<div>
    <input type="file" id="file" name="file" placeholder="Имя файла..."/>
    <button onclick="sendFile()">
        Upload
    </button>
    <input type="hidden" id="file_hidden">
    <div class="filename"></div>

    <table class="results"></table>
</div>

<div class="documents">
    <#if documents?has_content>
        <#list documents as document>
            <a href="/file/${document.getId()}">${document.getOriginalName()}</a>
        </#list>
    </#if>
</div>

</body>
</html>