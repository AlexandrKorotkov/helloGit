<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
</head>
<body>

<header>
  <nav class="navbar navbar-expand-md navbar-dark"
       style="background-color: tomato">

    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/list"
             class="nav-link">Users</a></li>
    </ul>
  </nav>
</header>
<br>
<div class="container col-md-5">
  <div class="card">
    <div class="card-body">
      <c:if test="${user != null}">
      <form action="update" method="post">
        </c:if>
        <c:if test="${user == null}">
        <form action="insert" method="post">
          </c:if>

          <caption>
            <h2>
              <c:if test="${user != null}">
                Edit User
              </c:if>
              <c:if test="${user == null}">
                Add New User
              </c:if>
            </h2>
          </caption>

          <c:if test="${user != null}">
            <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
          </c:if>

          <fieldset class="form-group">
            <label>First Name</label> <input type="text"
                                            value="<c:out value='${user.firstName}' />" class="form-control"
                                            name="firstName" required="required">
          </fieldset>

          <fieldset class="form-group">
            <label>Last Name</label> <input type="text"
                                             value="<c:out value='${user.lastName}' />" class="form-control"
                                             name="lastName">
          </fieldset>

          <fieldset class="form-group">
            <label>Age</label> <input type="text"
                                               value="<c:out value='${user.age}' />" class="form-control"
                                               name="age">
          </fieldset>

          <button type="submit" class="btn btn-success">Save</button>
        </form>
    </div>
  </div>
</div>
</body>
</html>