<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用IT服务管理系统</title>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
    
    <class name="${model.name}" table="${model.title}">
    	<#if model.properties?exists>
			<#list model.pagePanels as property>
				<#if property.xtype.name="form">
					<id name="${property.name}" type="${property.type}">
				<#else>
					<property name="${property.name}" type="${property.type}">
				</#if>
				
			</#list>
		</#if>
    </class>
</hibernate-mapping>
