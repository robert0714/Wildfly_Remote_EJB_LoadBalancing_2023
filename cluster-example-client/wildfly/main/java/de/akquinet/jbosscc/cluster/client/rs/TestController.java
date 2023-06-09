package de.akquinet.jbosscc.cluster.client.rs;
  
import de.akquinet.jbosscc.cluster.RemoteStateful;
import de.akquinet.jbosscc.cluster.RemoteStateless;
import de.akquinet.jbosscc.cluster.client.RemoteEJBClient;
import lombok.extern.jbosslog.JBossLog;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject; 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 

//@Slf4j 
@JBossLog
@Path("test")
public class TestController {
	
	@Inject
	private  RemoteEJBClientInterface remoteEJBClient;
	 
	@GET
	@Path("/stateful")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStateful() throws Exception {
		String nodeName = "No Catch";
		try {
			RemoteStateful statefulSession = remoteEJBClient.lookupRemoteStatefulBean();
			nodeName = statefulSession.getNodeName();
			log.info("StatefulSession IncrementCounter: "+ statefulSession.getAndIncrementCounter()); 
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		return nodeName;
		
	} 

	@GET
	@Path("/stateless")
	@Produces(MediaType.APPLICATION_JSON)
	public String stateless() throws Exception {
		String nodeName = "No Catch";
		try {
			RemoteStateless statelessProxy = remoteEJBClient.lookupRemoteStatelessBean();
			nodeName = statelessProxy.getNodeName();
                        log.info("nodeName: "+ nodeName); 
		 
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		return nodeName;
	}
}
