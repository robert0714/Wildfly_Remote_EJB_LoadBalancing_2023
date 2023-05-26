package de.akquinet.jbosscc.cluster.client.rs;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.NamingException;
 
import de.akquinet.jbosscc.cluster.client.RemoteEJBClient;

@ApplicationScoped
public class RemoteEJBClientImpl extends RemoteEJBClient implements RemoteEJBClientInterface{

	public RemoteEJBClientImpl(String[] data) throws NamingException {
		super(data); 
	}
	public RemoteEJBClientImpl() throws NamingException {
		super(); 
	}

}
