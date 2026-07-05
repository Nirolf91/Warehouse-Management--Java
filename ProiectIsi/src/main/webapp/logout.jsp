<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Invalidate the session and redirect to login page
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
