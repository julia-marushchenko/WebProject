<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Page</title>
</head>
<body text=#00bfff bgcolor=black>
	<form action="<c:url value='/logout'/>" method="get" style="text-align: right">
		<input type="submit" value="Logout" style="width: 120Px; height: 22Px">
	</form>
	<div align="center">
		<table>
			<tr>
				<td
					style="height: 400px; vertical-align: middle; width: 300px; text-align: center;">
					<h2>
						Welcome
						<%=session.getAttribute("name")%>
						<%=session.getAttribute("lastName")%>!
					</h2>
					<hr align="center" width="300" size="1" color="#00bfff" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html>