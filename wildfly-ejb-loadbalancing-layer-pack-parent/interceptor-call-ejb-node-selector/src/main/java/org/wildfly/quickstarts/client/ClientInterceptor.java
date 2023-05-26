package org.wildfly.quickstarts.client;

import java.lang.reflect.Method;
import java.net.URI;

import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import java.util.logging.Logger;

public class ClientInterceptor implements EJBClientInterceptor {
    private static final Logger log = Logger.getLogger(EJBClientInterceptor.class.getName());

    protected void showHiddenInfo(EJBClientInvocationContext ctx ) {
        URI destination = ctx.getDestination();
        Object[] params = ctx.getParameters();
        if (params != null && params.length > 0) {
        	for (Object obj : params) {
        		System.out.println("params: " + obj);
        	}
			
		}
		if (destination != null) {
			System.out.println("destination: " + destination.getHost() + ":" + destination.getPort());
		}
        Method method = ctx.getInvokedMethod();
        final  Class<?> ejbCalss = method.getDeclaringClass();
        String className = ejbCalss.getCanonicalName();
        String methodName = method.getName(); 
        
        System.out.println("className: "+className);
        System.out.println("methodName: "+methodName);
    }
    @Override
    public void handleInvocation(EJBClientInvocationContext ctx) throws Exception {
    	System.out.println("==========EJB Client Side handleInvocation ==============");
    	showHiddenInfo(ctx);
        ctx.sendRequest();
    }
    @Override
    public Object handleInvocationResult(EJBClientInvocationContext ctx) throws Exception {
        System.out.println("==========EJB Client Side handleInvocationResult ==============");
        showHiddenInfo(ctx);
    	
        return ctx.getResult();
    }
}
