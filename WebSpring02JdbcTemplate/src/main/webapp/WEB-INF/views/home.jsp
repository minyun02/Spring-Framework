<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>홈</h1>
<!--
 
	파일업로드 설정
1. pom.xml에 프레임워크 추가

		 fileupload
		<dependency>
		    <groupId>commons-fileupload</groupId>
		    <artifactId>commons-fileupload</artifactId>
		    <version>1.4</version>
		</dependency>
		
		<- commons io
		<dependency>
		    <groupId>commons-io</groupId>
		    <artifactId>commons-io</artifactId>
		    <version>2.8.0</version>
		</dependency>
		
2. root-context.xml에 multipartResolver객체 만들기
	 <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	 	<property name="maxUploadSize" value="-1"></property>
	 	<property name="defaultEncoding" value="UTF-8"></property>
	 </bean>
	 
3. root-context.xml을 web.xml에 파일명을 추가한다.
	<param-value>/WEB-INF/spring/appServlet/servlet-context.xml,/WEB-INF/spring.root-context.xml</param-value>	
 -->
</body>
</html>
