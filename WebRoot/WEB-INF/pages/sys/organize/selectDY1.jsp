<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-list-top">
			<div class="tw-search-bar">
				<form name="pendlist" id="pendlist"
					action="${ctx}/organize_sureDY.do" method="post">

					<button class="tw-btn-primary" onclick="sure();">
						<i class="tw-icon-pencil"></i> 确定
					</button>
				</form>
			</div>
		</div>
		<div class="tw-list-wrap">
			<form id="thisForm" method="POST" name="thisForm"
				action="${ctx }/organize_sureDY.do">
				<table class="tw-fixtable" layoutH="140">
					<thead>
						<tr>
							<th >姓名</th>
							<th >多音字</th>
							<th >
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${list}" varStatus="status">
							<tr>
								<td align="center" >${item.name}</td>
								<td >
									<table>
										<c:forEach var="map" items="${item.map}">
											<tr>
												<td>${map.key }</td>
												<td>
													<table>
														
														<c:forEach var="str" items="${map.value }">
															<tr>
																<td><input type="radio" name="${item.id }${map.key }"
																	value="${str}"></td>
																<td>${str}</td>
															</tr>
														</c:forEach>
													</table>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="tw-list-ft" id="pagingWrap"></div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	function sure(){
		document.getElementById('thisForm').submit();
	}

</script>
</html>