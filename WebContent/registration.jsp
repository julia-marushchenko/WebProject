<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration page</title>
</head>
<body text=#00bfff bgcolor=black>
	<div align="center">
		<h2>Registration</h2>
		<hr align="center" width="300" size="1" color="#00bfff" />

		<p>
			<c:if test="${not empty errorMessage}">
		${errorMessage}
		</c:if>
		</p>

		<p></p>
		<p></p>
		<form action="<c:url value='/registration'/>" method="post">
			<table border="0">
				<tr>
					<td><label> <strong>Login</strong></label></td>
					<td><input name="login" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Password</strong></label></td>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<td><label> <strong>Confirm password</strong></label></td>
					<td><input name="confirmPassword" type="password" /></td>
				</tr>
				<tr>
					<td><label> <strong>First name</strong></label></td>
					<td><input name="firstName" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Last name</strong></label></td>
					<td><input name="lastName" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Age</strong></label></td>
					<td><input name="age" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Country</strong></label></td>
					<td><input name="country" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Street</strong></label></td>
					<td><input name="street" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Zip code</strong></label></td>
					<td><input name="zipCode" type="text" /></td>
				</tr>
				<tr>
					<td><label> <strong>Music types</strong></label></td>
					<td><select name="musicTypes" size="5" multiple="multiple"
						style="width: 173Px">
							<c:forEach items="${musicTypes}" var="musicType">
								<option value="${musicType.id}">${musicType.typeName}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			<p></p>
			<input type="submit" value="Register"
				style="width: 120Px; height: 22Px"> <input type="reset"
				value="Clean" style="width: 120Px; height: 22Px">
		</form>
	</div>
</body>
</html>