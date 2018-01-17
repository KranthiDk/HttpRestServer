package com.test.rest.api.spec;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceConfiguration {
    Logger logger = Logger.getLogger("ResourceConfiguration");
	private String[] packageNames;
	private Map<String, Resource> resourceMap = new HashMap<String, Resource>();

	public Resource find(String requestPath) {
		return resourceMap.get(requestPath);
	}
		
	public void setPackagesToScan(String... packageNames) {
		this.packageNames = packageNames;
	}

	public void configure() throws Exception{
		logger.log(Level.INFO,"Scaning for Rest APIs.....");
		
			//String packageName = "com.test.rest.api.controller";
			ClassLoader classLoader = getClass().getClassLoader();
			// Change the package structure to directory structure
			for(String packageName:packageNames) {
			String packagePath = packageName.replace('.', '/');
			URL urls = classLoader.getResource(packagePath);

			// Get all the class files in the specified URL Path.
			File folder = new File(urls.getPath());
			File[] classes = folder.listFiles();

			int size = classes.length;
			List<Class<?>> classList = new ArrayList<Class<?>>();

			for (int i = 0; i < size; i++) {
				int index = classes[i].getName().indexOf(".");
				String className = classes[i].getName().substring(0, index);
				String classNamePath = packageName + "." + className;
				Class<?> repoClass = Class.forName(classNamePath);
				
				 final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(repoClass.getDeclaredMethods()));
			        for (final Method method : allMethods) {
			            if (method.isAnnotationPresent(RequestMapping.class)) {
			                RequestMapping annotInstance = method.getAnnotation(RequestMapping.class);
			                Resource resource = new Resource();
			                resource.setClassType(repoClass);
			                resource.setMethod(method);
			                resource.setHttpMethod(annotInstance.method());
			                resource.setRequestPath(annotInstance.path());
			                if(this.resourceMap.containsKey(annotInstance.path())) {
			                	throw new Exception("Found multiple resources with sample request path '"+annotInstance.path()+"'");
			                }
			                this.resourceMap.put(annotInstance.path(), resource);
			                logger.log(Level.INFO,"@@Found - "+resource);
			            }
			        }
			}
			
			
	}
			logger.log(Level.INFO,"Scaning for Rest APIs.....completed.");
	}
}
