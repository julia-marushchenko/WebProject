<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>

</head>
<body text=#00bfff bgcolor=black>
	<div align="center">
		<h2>Welcome</h2>
		<hr align="center" width="245" size="1" color="#00bfff" />
		<p> <c:if test="${not empty errorMessage}">
		${errorMessage}
		</c:if></p>
		
		<p></p>
		<form action="<c:url value='/login'/>" method="post">
			<table border="0">
				<tr>
					<td><label> <strong>Login</strong></label></td>
					<td><input name="login" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Password</strong></label></td>
					<td><input name="password" type="password" /></td>
				</tr>
			</table>
			<p></p>
			<input type="submit" value="Login" style="width: 120Px; height: 22Px">
		</form>
		<p></p>
		<form action="<c:url value='/registration'/>" method="get">
			<input type="submit" value="Register"
				style="width: 120Px; height: 22Px">
		</form>
	</div>
</body>
</html>