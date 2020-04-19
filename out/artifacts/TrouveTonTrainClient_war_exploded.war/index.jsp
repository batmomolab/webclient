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
  <title>Trouve ton train</title>
</head>
<body>
<form action="Resultat" method="post">
  Gare Départ:<input type="double" name="depart"/><br/>
  Gare Arrivée:<input type="double" name="arrivee"/><br/>
  Date de départ : <input type="date" name="datedepart"/><br/>
  Heure de départ : <input type="time" name="heuredepart"/><br/>
  Devise : <select name="devise">
  <option value="Euro">Euro</option>
  <option value="Dollar">Dollar</option>
  <option value="Yen">Yen</option>
</select>
  <input type="submit" value="Trouve mon train !"/>
</form>
</body>
</html>
