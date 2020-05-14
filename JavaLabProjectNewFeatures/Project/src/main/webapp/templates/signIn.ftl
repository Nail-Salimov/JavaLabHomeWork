<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html>
<head>
    <title>Login</title>
    <style>
        * {
            box-sizing: border-box
        }

        /* Add padding to containers */
        .container {
            padding: 16px;
        }

        /* Full-width input fields */
        input[type=text], input[type=password] {
            width: 100%;
            padding: 15px;
            margin: 5px 0 22px 0;
            display: inline-block;
            border: none;
            background: #f1f1f1;
        }

        input[type=text]:focus, input[type=password]:focus {
            background-color: #ddd;
            outline: none;
        }

        /* Overwrite default styles of hr */
        hr {
            border: 1px solid #f1f1f1;
            margin-bottom: 25px;
        }

        /* Set a style for the submit/register button */
        .registerbtn {
            background-color: #4CAF50;
            color: white;
            padding: 16px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
            opacity: 0.9;
        }

        .registerbtn:hover {
            opacity: 1;
        }

        /* Add a blue text color to links */
        a {
            color: dodgerblue;
        }

        /* Set a grey background color and center the text of the "sign in" section */
        .signin {
            background-color: #f1f1f1;
            text-align: center;
        }
    </style>
</head>
<body>


<form class="registration_form" method="post" action="/signIn">
    <div class="container">
        <h1><@spring.message "signIn.page.label"/></h1>
        <p><@spring.message "signIn.page.text"/></p>
        <hr>

        <label for="email"><b><@spring.message "signIn.page.email"/></b></label>
        <div class="status"></div>

        <input id="name" type="text" placeholder="<@spring.message "signIn.page.enter.email"/>" name="email" required>

        <label for="psw"><b><@spring.message "signIn.page.password"/></b></label>
        <input type="password" placeholder="<@spring.message "signIn.page.enter.password"/>" name="password" required>

        <hr>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <label>
            <input type="checkbox" name="remember-me"><@spring.message "signIn.page.rememberMe"/>
        </label>

        <button type="submit" class="registerbtn"><@spring.message "signIn.page.button"/></button>
    </div>
</form>

</body>
</html>