package de.akquinet.jbosscc.cluster.client.rs;

import javax.naming.NamingException;

import de.akquinet.jbosscc.cluster.RemoteStateful;
import de.akquinet.jbosscc.cluster.RemoteStateless;

public interface RemoteEJBClientInterface {

	RemoteStateful lookupRemoteStatefulBean()throws NamingException;

	RemoteStateless lookupRemoteStatelessBean()throws NamingException;

}
