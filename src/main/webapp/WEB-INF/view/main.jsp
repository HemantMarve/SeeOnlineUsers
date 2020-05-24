<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ page isELIgnored="false" %>
  <%@ page import="java.util.*,com.vigus.entity.UserEntity" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
.button {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
.circle-icon {
  fill: currentColor;
  height: 50%;
  left: 50%;
  position: absolute;
  stroke: currentColor;
  stroke-width: 0;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 50%;
}
#h{transition-timing-function: ease-out;}
.dot{
transition-timing-function: ease-in-out;
  height: 25px;
  width: 25px;
  background-color:#456BD9;
  border-radius: 50%;
  display: inline-block;
  color: #fff;
  position: relative;
}
.dot .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: green;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  position: absolute;
  z-index: 1;
  margin-left: 5px;
  margin-top: 40px;
}

.dot:hover .tooltiptext {
  visibility: visible;
}
.dot:hover
{
transition:1s;
   height: 35px;
  width: 35px;
   background-color:green;
}
</style>
</head>
<body>
<%if(request.getSession()==null) 
{
	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
	requestDispatcher.forward(request, response);	
}%>
<%int i=0; %>
<c:forEach var="entry" items="${sessionScope.active}">
<c:set var="u" scope="page" value="${entry.value}"/>
  <div class="dot" id="h"><svg class="circle-icon" viewBox="0 0 24 24" width="24" height="24">
    <line x1="2" x2="22" y1="5"  y2="5"  stroke-width="3" stroke-linecap="round"/>
    <line x1="2" x2="22" y1="12" y2="12" stroke-width="3" stroke-linecap="round"/>
    <line x1="2" x2="22" y1="19" y2="19" stroke-width="3" stroke-linecap="round"/>
  </svg><span class="tooltiptext">Name:<c:out value="${u.user}"/><br>Age:<c:out value="${u.age}"/><br>Time:<c:out value="${u.time}"/></span></div>
  <%i++; %>
 </c:forEach>  <br>
 <h2 style="color:green;">Online Users:<%=i %>(Referesh To see new Online users)</h2>  <br>                                                                        
      <a href="invalidate.htm" class="button">Logout</a><br>
      <a href="pastactive.htm" class="button">SeePastVisiters</a><br>
      

</body>
</html> 
