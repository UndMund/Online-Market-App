<!DOC TYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Profile</title>
</head>
<body>
<div align="center">
    <h1>User Profile</h1>
    <table>
        <tr>
            <td>Name</td>
            <td>${name}</td>
        </tr>
        <tr>
            <td>Age</td>
            <td>${age}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${email}</td>
        </tr>
        <tr>
            <td>Login</td>
            <td>${login}</td>
        </tr>
        <tr>
            <td>Password</td>
            <td>${password}</td>
        </tr>
    </table>
    <p></p>
    <form action="<%= request.getContextPath()%>/profile" method="post">
    <table>
        <tr>
            <td>New password</td>
           <td><input type="text" name="password" required></td>
            <td><input type="submit" name="submit"></td>
        </tr>
    </table>
    </form>
    <form action="<%= request.getContextPath()%>/" method="get">
        <table>
            <tr>
                <td>Log out</td>
                <td><input type="submit" name="submit"></td>
            </tr>
        </table>

    </form>
</div>
</body>
</html>