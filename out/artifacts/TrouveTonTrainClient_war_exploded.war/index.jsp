<%--
  Created by IntelliJ IDEA.
  User: FRJBLAB
  Date: 16/04/2020
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Calcul de coordonées</title>
</head>
<body>
<form action="servlet1" method="post">
  Latitude A:<input type="double" name="LatitudeA"/><br/>
  Longitude A:<input type="double" name="LongitudeA"/><br/>
  Latitude B:<input type="double" name="LatitudeB"/><br/>
  Longitude B:<input type="double" name="LongitudeB"/><br/>
  Devise : <select name="devise">
  <option value="Euro">Euro</option>
  <option value="Dollar">Dollar</option>
  <option value="Yen">Yen</option>
</select>
  <input type="submit" value="Trouve mon train !"/>
</form>
</body>
</html>
