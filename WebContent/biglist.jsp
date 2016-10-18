<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, anime.*"
    %>
<%@taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/header.js"></script>
<link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.blue_grey-cyan.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Noto+Sans' type='text/css'>
<link rel="stylesheet" href="css/header.css" type="text/css">

<style type="text/css">

	body {font-family: 'Noto Sans', sans-serif;}
	
	a:link {color: inherit; text-decoration: none;}
	a.index{color: white; text-decoration: none;}
	
	.mdl-card {
		margin: 20px auto;
	}
	
	.mdl-card__media {
		background-color: black;
	}
	
	#view {
      position: fixed;
      display: block;
      right: 0;
      bottom: 0;
      margin-right: 40px;
      margin-bottom: 40px;
      z-index: 900;
    }
	
</style>

<%
	String title = request.getParameter("title");
	String url = request.getParameter("url");
	List<Item> items = Anigod.lists(url);
	request.setAttribute("items", items);
%>

<title><%=title%></title>
</head>
<body>
	<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-drawer">
		<header class="mdl-layout__header">
			<div class="mdl-layout__header-row">
				<a href="index.jsp" class="index">
					<span class="mdl-layout__title">Noitamina</span>
				</a>
			<!-- Displayed on Computer and Tablet -->
			<!-- Search -->
			<div class="mdh-expandable-search mdl-cell--hide-phone">
	        	<i class="material-icons">search</i>
	        	<form action="search.jsp">
	          		<input type="text" name="keyword" placeholder="Search" size="1">
				</form>
			</div>
			<!-- Displayed on mobile -->
			<div class="mdl-layout-spacer mdl-cell--hide-tablet mdl-cell--hide-desktop"></div>
				<!-- Search button -->
				<button class="mdh-toggle-search mdl-button mdl-js-button mdl-button--icon mdl-cell--hide-tablet mdl-cell--hide-desktop">
					<i class="material-icons">search</i>
				</button>
	      	</div>
		</header>
		
		<div class="mdl-layout__drawer">
			<span class="mdl-layout__title">Noitamina</span>
			<nav class="mdl-navigation">
	        	<a class="mdl-navigation__link" href="all.jsp">All</a>
	    	</nav>
		</div>
		
		<main class="mdl-layout__content">
			<c:forEach items="${items}" var="item">
				<div class="mdl-card mdl-shadow--2dp">
					<div class="mdl-card__title">
	  					<h2 class="mdl-card__title-text">${item.title}</h2>
	 				</div>
					<div class="mdl-card__media">
					<video controls preload="none" poster="${item.src}" width="100%" height="100%">
						<source src="bigvideo.jsp?url=${item.url}" type="video/mp4">
					</video>
					</div>
				</div>
			</c:forEach>
		</main>
	</div>

	<a href="list.jsp?title=<%=title%>&url=<%=url%>" id="view" class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored">
		<i class="material-icons">view_list</i>
	</a>

</body>
</html>