package com.test.rest.api.spec;
import java.lang.reflect.Method;

public class Resource {
	private String requestPath;
	private String httpMethod;
	private Class classType;
	private Method method;
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public Class getClassType() {
		return classType;
	}
	public void setClassType(Class classType) {
		this.classType = classType;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return "Resource [requestPath=" + requestPath + ", httpMethod=" + httpMethod + ", classType=" + classType
				+ ", method=" + method + "]";
	}
	
	
}
