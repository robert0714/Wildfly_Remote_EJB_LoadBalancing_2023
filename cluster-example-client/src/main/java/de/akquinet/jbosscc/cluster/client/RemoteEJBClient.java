package de.akquinet.jbosscc.cluster.client;

import de.akquinet.jbosscc.cluster.RemoteStateful; 
import de.akquinet.jbosscc.cluster.RemoteStateless;   
import org.jboss.ejb.client.legacy.JBossEJBProperties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Hashtable;
import java.util.Optional;
import java.util.Properties;

public class RemoteEJBClient
{ 
    private final Context context; 
    
    private String host =Optional.ofNullable(System.getenv("REMOTE_HOST"))
			.orElse(Optional.ofNullable(System.getProperty("REMOTE_HOST")).orElse("localhost"));
    private String port =Optional.ofNullable(System.getenv("REMOTE_PORT"))
			.orElse(Optional.ofNullable(System.getProperty("REMOTE_PORT")).orElse("8080"));
    private String rmoteEjbHttpEnable =Optional.ofNullable(System.getenv("REMOTE_EJB_HTTP_ENABLE"))
			.orElse(Optional.ofNullable(System.getProperty("REMOTE_EJB_HTTP_ENABLE")).orElse("false"));
    
    public RemoteEJBClient() throws NamingException
    {
        context = initializeJNDIContext(null);
         
    }
    public RemoteEJBClient(final String[] data) throws NamingException
    {
        context = initializeJNDIContext(data);
         
    }
    
    private Context initializeJNDIContext(final String[] data) throws NamingException
    { 
         if(Boolean.valueOf(rmoteEjbHttpEnable)) {
        	 
        	 return httpSettins();
         }else if (data == null || data.length == 0){
        	 return remoteSettins();
         }else  {
        	//  return nodeSelectorLegacySettins(data);
            return nodeSelectorSettins(data);
             
         } 
    } 

	private Context httpSettins() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		/**
		 * Using Server-Side http protocal with WildFly, we need use  <layer>jaxrs</layer>
		 * */
		jndiProperties.put(Context.PROVIDER_URL, "http://" + host + ":" + port + "/wildfly-services");
		 
		return new InitialContext(jndiProperties);
	}
    private Context remoteSettins() throws NamingException {
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "remote+http://" + host +":" + port);
		return new InitialContext(jndiProperties);
	}
    private Context nodeSelectorSettins(final String[] data) throws NamingException{
    	//Reference: https://access.redhat.com/solutions/3486221
    	//https://access.redhat.com/solutions/2972861
    	Properties env   = composeNodeSelectorProps(data);
        return new InitialContext(env);
    }
    private Context nodeSelectorLegacySettins(final String[] data) throws NamingException{
    	//Reference: https://stackoverflow.com/questions/65237488/migration-to-wildfly-21-from-jboss-as7-2
    	Properties env = composeNodeSelectorProps(data);
        
        JBossEJBProperties ejbProperties = JBossEJBProperties.fromProperties("ejb-client.properties", env);
        JBossEJBProperties.getContextManager().setGlobalDefault(ejbProperties);
        
        env = new Properties();
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//      env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory"); 
        return new InitialContext(env);
    }
    private Properties composeNodeSelectorProps(final String[] data) {
    	Properties env = new Properties(); 
        env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         
        //see org.jboss.ejb.client.legacy.JBossEJBProperties
        env.put("endpoint.name", "client-endpoint");
        env.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        
        env.put("invocation.timeout", "9000");
        env.put("reconnect.tasks.timeout", "2000"); 
        env.put("deployment.node.selector", RoundRobinDeploymentNodeSelector.class.getCanonicalName());
  
        
        final StringBuilder connections = new StringBuilder();
        final  String[] hostsAndPorts  ;
		if (data != null && data.length > 0) {
			hostsAndPorts = data;
		} else {
			hostsAndPorts = new String[] { host + ":" + port };
		}
		 
        int sCount = 0;
        for (String host : hostsAndPorts) { 
            env.put("remote.connection.s" + sCount + ".host", host.split(":")[0]);
            env.put("remote.connection.s" + sCount + ".port", host.split(":")[1]);
            env.put("remote.connection.s" + sCount + ".connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS",
                "false");
//            env.put("remote.connection.s" + sCount + ".connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS",
//                "JBOSS-LOCAL-USER");
//            env.put("remote.connection.s" + sCount + ".connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT",
//                "false");
//            env.put("remote.connection.s" + sCount + ".connect.timeout", "2000");
            
            env.put("remote.connection.s" + sCount + ".clusternode.selector", RoundRobinClusterNodeSelector.class.getCanonicalName());
            
            // fill connections list
            if (connections.length() > 0) {
                connections.append(",");
            }
            connections.append("s" + sCount);
            // prepare for next server
            sCount++;
        }
        System.out.println("---------------------------------");
        System.out.println("hostsAndPorts size: "+hostsAndPorts.length);
        // connections-list
        env.put("remote.connections", connections.toString());
        env.put("jboss.naming.client.ejb.context", true);
        return env ;
    }
    
    public RemoteStateless lookupRemoteStatelessBean() throws NamingException
    {
        final String appName = "";
        final String moduleName = "cluster";  // module-name in ejb-jar.xml
        final String distinctName = "";  // jboss:distinct-name in jboss-ejb3.xml
        final String implBean="ClusteredStatelessBean";

        // ejb:/cluster//ClusteredStatelessBean!de.akquinet.jbosscc.cluster.RemoteStateless
        final String jndiName = "ejb:" + appName + '/' + moduleName + '/' + distinctName + '/' +
        		implBean + '!' +
                RemoteStateless.class.getName(); 
        return (RemoteStateless) context.lookup(jndiName); 
    }

    public RemoteStateful lookupRemoteStatefulBean() throws NamingException
    {
        final String appName = "";
        final String moduleName = "cluster";  // module-name in ejb-jar.xml
        final String distinctName = "";  // jboss:distinct-name in jboss-ejb3.xml
        final String implBean="ClusteredStatefulBean";

        // ejb:/cluster//ClusteredStatefulBean!de.akquinet.jbosscc.cluster.RemoteStateful?stateful
        final String jndiName = "ejb:" + appName + '/' + moduleName + '/' + distinctName + '/' +
        		implBean + '!' +
                RemoteStateful.class.getName() + "?stateful"; 
        return (RemoteStateful) context.lookup(jndiName);
    }
}
