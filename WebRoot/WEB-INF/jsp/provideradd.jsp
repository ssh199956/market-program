<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
	<div class="location">
		<strong>你现在所在的位置是:</strong> <span>供应商管理页面 >>> 供应商添加页面</span>
	</div>
	<div class="providerAdd">
		<fm:form method="post" modelAttribute="provider">
			<table>
				<tr>
					<th>供应商编码:</th>
					<td><fm:input path="proCode" /> <fm:errors path="proCode" />
					</td>
				</tr>
				<tr>
					<th>供应商名称:</th>
					<td><fm:input path="proName" /> <fm:errors path="proName" />
					</td>
				</tr>
				<tr>
					<th>联系人:</th>
					<td><fm:input path="proContact" /> <fm:errors
							path="proContact" /></td>
				</tr>
				<tr>
					<th>联系电话:</th>
					<td><fm:input path="proPhone" /> <fm:errors path="proPhone" />
					</td>
				</tr>
				<tr>
					<th>联系地址:</th>
					<td><fm:input path="proAddress" /> <fm:errors
							path="proAddress" /></td>
				</tr>
				<tr>
					<th>传真:</th>
					<td><fm:input path="proFax" /> <fm:errors path="proFax" /></td>
				</tr>
				<tr>
					<th>描述:</th>
					<td><fm:input path="proDesc" /> <fm:errors path="proDesc" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="保存" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" id="back" name="back"
						value="返回"></td>
				</tr>
			</table>
		</fm:form>
		<!-- 		<form id="providerForm" name="providerForm" method="post" -->
		<!-- 			action="${pageContext.request.contextPath }/provider/addProviderSave.html"> -->
		<!-- 			<input type="hidden" name="method" value="add"> -->
		<!--div的class 为error是验证错误，ok是验证成功-->
		<!-- 			<div class=""> -->
		<!-- 				<label for="proCode">供应商编码：</label> <input type="text" -->
		<!-- 					name="proCode" id="proCode" value=""> -->
		<!-- 放置提示信息 -->
		<!-- 				<font color="red"></font> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proName">供应商名称：</label> <input type="text" -->
		<!-- 					name="proName" id="proName" value=""> <font color="red"></font> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proContact">联系人：</label> <input type="text" -->
		<!-- 					name="proContact" id="proContact" value=""> <font -->
		<!-- 					color="red"></font> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proPhone">联系电话：</label> <input type="text" -->
		<!-- 					name="proPhone" id="proPhone" value=""> <font color="red"></font> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proAddress">联系地址：</label> <input type="text" -->
		<!-- 					name="proAddress" id="proAddress" value=""> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proFax">传真：</label> <input type="text" name="proFax" -->
		<!-- 					id="proFax" value=""> -->
		<!-- 			</div> -->
		<!-- 			<div> -->
		<!-- 				<label for="proDesc">描述：</label> <input type="text" name="proDesc" -->
		<!-- 					id="proDesc" value=""> -->
		<!-- 			</div> -->
		<!-- 			<div class="providerAddBtn"> -->
		<!-- 				<input type="button" name="add" id="add" value="保存"> <input -->
		<!-- 					type="button" id="back" name="back" value="返回"> -->
		<!-- 			</div> -->
		<!-- 		</form> -->
	</div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/js/provideradd.js"></script>
