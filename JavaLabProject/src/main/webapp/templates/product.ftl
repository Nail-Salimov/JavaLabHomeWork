<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${productData.name}</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>

    <script>
        function buy() {
            let count = $("#count").val();
            let data = {"id": ${productData.id}, "count": count};

            $.ajax({

                type: 'POST',
                url: 'http://localhost:8080/api/buy',
                contentType: "application/json",

                headers: {
                    <#if userData?has_content>
                    Authorization: "${userData.token}"
                    </#if>
                },

                data: JSON.stringify(data),
                dataType: 'json',

            })
                .done(function (response) {
                    $(".form").remove();
                    $("#result").empty().text(response.description);
                    let currentCount = ${productData.count} -count;
                    $("#currentCount").empty().text("Количество: " + currentCount);
                })
                .fail(function () {
                    alert('Error')
                });
        }

    </script>

    <script>
        function f() {

            let maxCost = #{productData.maxCost};
            let minCost =  #{productData.minCost};
            let decrease =  #{productData.decrease};


            var ms = new Date();
            const currentTime = Math.ceil(ms.getTime() / 1000);
            let productTime = #{productData.time};

            let timer = currentTime - productTime;
            let hour = Math.ceil(timer / (60 * 60)) - 1;

            if (maxCost - (hour * decrease) >= minCost) {
                $("#cost").text(maxCost - (hour * decrease));

                t();
            } else {

                $("#cost").empty().text(minCost);
                $("#timer").empty().text("Товар достиг наименьшего значения");

            }

            function t() {
                timer = timer % (60 * 60);

                let minute = Math.ceil(timer / 60);
                minute = 60 - minute;
                let sec = timer % 60;
                sec = 60 - sec;

                let time = $('.seconds');
                time.text(sec)
                let min = $('.minute');
                min.text(minute);

                intervalId = setInterval(timerDecrement, 1000);


                function timerDecrement() {
                    let newTime = time.text() - 1;
                    let newMin = min.text();
                    time.text(newTime);

                    if (newTime === 0) {
                        time.text(59);


                        if (newMin == 0) {
                            hour++;
                            if(checkCost()){
                                min.text(59);
                            }else{
                                clearInterval(intervalId);
                            }
                        } else {
                            min.text(min.text() - 1);
                        }
                    }
                }

                function checkCost(){
                    if (maxCost - (hour * decrease) >= minCost) {
                        $("#cost").text(maxCost - (hour * decrease));
                        return true;
                    } else {
                        $("#timer").empty().text("Товар достиг наименьшего значения");
                        $("#cost").text(minCost);
                        return false;
                    }
                }
            }
        }
    </script>
</head>
<body onload="f()">
<nav>
    <a href="/store">Store</a>
    <#if userData?has_content>
        <a href="/profile">Профиль</a>
    <#else>
        <a href="/signIn">Авторизуйтесь</a>
    </#if>
</nav>

<div>
    <#list productData.images as image>
        <img src="/image/${image.imageName}" width="200" height="200"/>
    </#list>
</div>
<div>
    <p>Название: ${productData.name}</p>
    <p>Описание: ${productData.description}</p>
    <p><span>Цена:</span><span id="cost"> ${productData.maxCost}</span></p>
    <p>Наименьшая цена: ${productData.minCost}</p>
    <p id="currentCount">Количество: ${productData.count}</p>
    <div id="timer">
        <span>Цена уменьшится на ${productData.getDecrease()} через: </span>
        <span class="minute">10</span><span>:</span><span class="seconds">20</span>
    </div>
</div>

<div><p id="result"></p></div>
<div class="form">
    <#if userData?has_content>
        <#if userData.address?has_content>

            <input id="count" name="count" type="number"placeholder="количество:"/>
            <button onclick="buy()">Купить</button>

        <#else>
            <p>Чтобы приобрести товар, введите адрес доставки в <a href="/profile">профиле</a></p>
        </#if>

    <#else >
        <p>Чтобы приобрести товар, <a href="/signIn">авторизуйтесь</a></p>
    </#if>
</div>
</body>
</html>