<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form name="loginform" action="login" method="POST">
            <input type="text" name="username" id="username" maxlength="35" size=10 value="" placeholder="username" required="" title="Digite seu login"/>
            <input type="password" name="password" id="password"  maxlength="35" size=10 value="" required="" title="Digite sua senha"/>
            <input type="hidden" value=redirect_url/>
            <input type="submit" name="Login" id="Login" value="Login" title="Entrar"/>
            <%

                String msg = request.getParameter("msg");
                if (msg != null) {
                    out.print("<br/>" + msg);
                }

            %>
        </form>
    </body>
</html>
