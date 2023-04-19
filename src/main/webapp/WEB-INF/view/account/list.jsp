<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="col-sm-8">
	<h2>나의 계좌 목록</h2>
	<h5>어서오세요 환영합니다</h5>
	<div class="bg-light p-md-5 h-75 align-items-center justify-content-center">
		<c:choose>
			<c:when test="${accountList != null}">
				<table class="table">
					<thead>
						<tr>
							<th>계좌번호</th>
							<th>잔액</th>
							<th>계좌생성일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accountList}">
							<tr>
								<td>${account.number}</td>
								<td>${account.balance}원</td>
								<td>${account.createdAt}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>소유한 계좌가 없습니다. 계좌생성 페이지에서 계좌를 개설해주세요.</p>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>