<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Moderator page</title>
</head>
<body text=#00bfff bgcolor=black>
	<form action="<c:url value='/logout'/>" method="get" style="text-align: right">
		<input type="submit" value="Logout" style="width: 120Px; height: 22Px">
	</form>
	<div align="center">
		<form action="<c:url value='/moderatorPage'/>" method="get">
			<table BORDER=5 ALIGN=CENTER>
				<tr>
					<th>Login</th>
					<th>Password</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Age</th>
					<th>Country</th>
					<th>Street</th>
					<th>Zip Code</th>
					<th>Role</th>
					<th>Music Types</th>
				</tr>

				<c:forEach items="${setUsers}" var="user">
					<tr>
						<td><c:out value="${user.login}" /></td>
						<td><c:out value="${user.password}" /></td>
						<td><c:out value="${user.firstName}" /></td>
						<td><c:out value="${user.lastName}" /></td>
						<td><c:out value="${user.age}" /></td>

						<td><c:out value="${user.address.country}" /></td>
						<td><c:out value="${user.address.street}" /></td>
						<td><c:out value="${user.address.zipCode}" /></td>
						<td><c:out value="${user.role.roleName}" /></td>
						<td><c:forEach items="${user.musicTypes}" var="type">
								<c:out value="${type.typeName}" />
							</c:forEach></td>
					</tr>
				</c:forEach>

			</table>
		</form>
	</div>
</body>
</html>