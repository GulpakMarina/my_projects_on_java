<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <html>
  <head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title>Recipe Book</title>
  </head>
  <body  margin-top="10%">
  <h2 align="center">Cuisines</h2>
  <table align="center" border="1" cellpadding="7" cellspacing="0">
  <tr>
  <th>&nbsp;</th>
  <th>TITLE</th>
  <th>EDIT</th>
  <th>DELETE</th>
  </tr>
  <c:forEach items="${cuisines}" var="cuisine" >
    <tr>
    <td>&nbsp;</td>
    <td>${cuisine.title}</td>
    <td><label onclick="edit(${cuisine.id},'${cuisine.title}');" >Edit</label>
    </td>
    <td> <label onclick="del(${cuisine.id},'${cuisine.title}');" >Delete</label>
    </td>
    </tr>
  </c:forEach>
  </table><br>
  <div align="center">
  <label  id="titleNew" >ADD NEW</label><br>
  </div>
  <div id="iT" align="center" margin="5%" >
  <input type="text" name="cuisineTitle" form="form" id="inputTitle" onclick="disAppearError();" placeholder="title"><br>
  <label id="error" color="#BA2B2B">${error}</label></br>
  </div>
  <div align="center">
  <input type="text" name="cuisineId" form="form" id="inputId" hidden >
  <input type="text" name="type" form="form" value="add" id="type" hidden >
  <input type="submit" form="form"  name="submit" value="submit"><br>
  </div>
  <form align="center" name="form"  id="form" action="${pageContext.request.contextPath}/cuisine" method="post">
  </form>
  <script type="text/javascript">
  function disAppearError(){
  document.getElementById("error").innerHTML=" ";
  }
  function edit(id,title){
  document.getElementById("titleNew").innerHTML="EDIT";
  document.getElementById("inputId").value=id;
  document.getElementById("inputTitle").value=title;
  document.getElementById("type").value="edit";
  }
  function del(id,title){
  document.getElementById("titleNew").innerHTML="DELETE";
  document.getElementById("inputId").value=id;
  var divIT=document.getElementById("iT");
  divIT.innerHTML="<label>"+title+"</label><br>";
  document.getElementById("type").value="delete";
  }
  </script>
  </body>
</html>
