<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
	<div class="location">
		<strong>你现在所在的位置是:</strong> <span>用户管理页面 >>> 用户添加页面</span>
	</div>
	<div class="providerAdd">
		<fm:form method="post" modelAttribute="user"
			enctype="multipart/form-data">
			<div>
				用户编码:
				<fm:input path="userCode" />
				<font color="red"></font>
				<fm:errors path="userCode" />
			</div>
			<div>
				用户名称:
				<fm:input path="userName" />
				<font color="red"></font>
				<fm:errors path="userName" />
			</div>
			<div>
				用户密码:
				<fm:input path="userPassword" />
				<font color="red"></font>
				<fm:errors path="userPassword" />
			</div>
			<div>
				用户性别:
				<fm:radiobutton path="gender" value="1" checked="checked" />
				男&nbsp&nbsp&nbsp&nbsp
				<fm:radiobutton path="gender" value="2" />
				女
			</div>
			<div>
				用户生日:
				<fm:input path="birthday" Class="Wdate" readonly="readonly"
					onclick="WdatePicker();" />
				<fm:errors path="birthday" />
			</div>
			<div>
				用户地址:
				<fm:input path="address" />
				<fm:errors path="address" />
			</div>
			<div>
				联系电话:
				<fm:input path="phone" />
				<fm:errors path="phone" />
			</div>
			<div>
				用户角色: <select id="userRole" name="userRole">
					<!-- 					<option value="0">请选择</option> 
					<c:if test="${roleLists!=null}">
						<c:forEach var="i" items="${roleLists}">
							<option value="${i.id}">${i.roleName}</option>
						</c:forEach>
					</c:if>-->
				</select>
			</div>
			<div>
				<input type="hidden" id="errorinfo" value="${uploadFileError}" />
				证件照 :<input type="file" name="files" id="files1" /> <font
					color="red"></font>
			</div>
			<div>
				<input type="hidden" id="errorinfo_wp" value="${uploadWpError}" />
				工作证照片:<input type="file" name="files" id="files2" /> <font
					color="red"></font>
			</div>
			<br />
			<input type="submit" value="保存" />
			<br />
			<input type="button" value="返回" id="back" name="back" />
		</fm:form>
	</div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
