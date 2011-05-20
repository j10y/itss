<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
  <class name="${entity.className}" table="${entity.tableName}">
  	<#if entity.properties?exists>
		<#list entity.properties as property>
		<#if property.primary>
      <id name="${property.name}" type="${property.type}">
			  <generator class="native"/>
      </id>
		<#else>
			<#if property.type=="java.lang.String">
			<property name="${property.name}" type="${property.type}">
			  <column name="${property.field?upper_case}" <#if property.length?exists>length="${property.length}"</#if>></column>
			</property>
			
			<#elseif property.type=="java.util.Set">
			<set name="${property.name}" table="${property.refTable}" cascade="none">
        	  <key column="${property.refPColumn}"/>
        	  <many-to-many class="${property.foreignClass}" column="${property.refVColunn}"/>     
        	</set>
			
			<#elseif property.type=="java.util.Date">
			<property name="${property.name}" type="date">
			  <column name="${property.field?upper_case}"></column>
			</property>
			<#elseif property.type=="java.lang.Long" || property.type=="java.lang.Integer" || property.type=="java.lang.Short"|| property.type=="java.lang.Double">
			<property name="${property.name}" type="${property.type}">
			  <column name="${property.field?upper_case}" <#if property.length?exists>precision="${property.length}"</#if>></column>
			</property>
			<#else>
			<many-to-one name="${property.name}" class="${property.type}">
		      <column name="${property.field?upper_case}" />
		    </many-to-one>
			</#if>
		</#if>
		</#list>
	</#if>
	
  </class>
</hibernate-mapping>
