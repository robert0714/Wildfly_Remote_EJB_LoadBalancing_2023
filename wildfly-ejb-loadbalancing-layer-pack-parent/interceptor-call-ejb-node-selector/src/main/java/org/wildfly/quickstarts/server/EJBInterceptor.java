package org.wildfly.quickstarts.server;
 
import java.io.Serializable; 
import java.lang.reflect.Method;
import java.util.logging.Logger;
 
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
  
/**
 * https://opentelemetry.io/docs/reference/specification/compatibility/opentracing/<br/>
 * https://www.elastic.co/guide/en/apm/agent/java/current/public-api.html<br/>
 * https://www.elastic.co/guide/en/apm/guide/7.17/apm-distributed-tracing.html
 * https://www.elastic.co/guide/en/apm/agent/rum-js/master/custom-transactions.html
 * https://github.com/elastic/apm-agent-java
 * http://www.mastertheboss.com/jbossas/wildfly-8/how-to-debug-remotely-wildfly/
 * */
//@Interceptor
public class EJBInterceptor implements Serializable {
	private static final Logger log = Logger.getLogger(EJBInterceptor.class.getName());

	
    @AroundInvoke
    public Object wrap(InvocationContext ctx) throws Exception {
        
        Method method = ctx.getMethod();
        final  Class<?> ejbCalss = ctx.getMethod().getDeclaringClass();
        String className = ejbCalss.getCanonicalName();
        String methodName = method.getName();
        
        System.out.println("==========EJB Server side==============");
        
        System.out.println("className: "+className);
        System.out.println("methodName: "+methodName);
        
        return ctx.proceed(); 
    } 
}
