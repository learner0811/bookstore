<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav aria-label="breadcrumb">
	<ol class="breadcrumb">
		<c:forEach items="${listBreadCum}" var="i" varStatus="loop">
			<c:choose>
				<c:when test="${i.current == true }">
					<li class="breadcrumb-item active" aria-current="page">${i.name}</li>
				</c:when>
				<c:otherwise>
					<li class="breadcrumb-item"><a href="${i.url}">${i.name }</a></li>
				</c:otherwise>
			</c:choose>			
		</c:forEach>		
	</ol>
</nav>