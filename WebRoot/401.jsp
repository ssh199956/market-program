<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

</head>

<body>
	<div>
		<h2>
			对不起,您没有访问权限,请返回到<a href="${pageContext.request.contextPath }">首页</a>
		</h2>
	</div>
	<div>
		<img src="${pageContext.request.contextPath }/statics/images/jp.png">
	</div>
</body>
</html>
