package de.akquinet.jbosscc.cluster.client.rs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped; 
import javax.naming.NamingException;

import de.akquinet.jbosscc.cluster.RemoteStateful;
import de.akquinet.jbosscc.cluster.RemoteStateless;
import de.akquinet.jbosscc.cluster.client.RemoteEJBClient;

@ApplicationScoped
public class RemoteEJBClientImpl   implements RemoteEJBClientInterface{	 
	
	private RemoteEJBClient client ;
	
	public String[] getHostsAndPortsFromJavaOpts() {
		String template = "rmote.ejb.hostsAndPorts[%d]";
		List<String> result = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String propsName = String.format(template, i);
//			String host =System.getProperty("rmote.ejb.hostsAndPorts[0]");
			String value = System.getProperty(propsName);
			if (value != null && value.length() > 0) {
				result.add(value);
			}
		}
		return result.toArray(new String[] {});

	}
	
	@PostConstruct
	public void postConstruct() throws NamingException {
		final String [] hostsAndPorts = getHostsAndPortsFromJavaOpts() ;
		
		if(hostsAndPorts !=null && hostsAndPorts.length > 0 ) {
			this.client  = new RemoteEJBClient(hostsAndPorts);
		}else {
			this.client  = new RemoteEJBClient();
		}
	}


	@Override
	public RemoteStateful lookupRemoteStatefulBean() throws NamingException { 
		return this.client.lookupRemoteStatefulBean();
	}


	@Override
	public RemoteStateless lookupRemoteStatelessBean() throws NamingException { 
		return this.client.lookupRemoteStatelessBean();
	}

}
