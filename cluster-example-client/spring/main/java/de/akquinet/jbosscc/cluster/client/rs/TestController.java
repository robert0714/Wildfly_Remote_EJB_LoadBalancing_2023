package de.akquinet.jbosscc.cluster.client.rs;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.akquinet.jbosscc.cluster.RemoteStateful;
import de.akquinet.jbosscc.cluster.RemoteStateless;
import de.akquinet.jbosscc.cluster.client.RemoteEJBClient;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private  RemoteEJBClient remoteEJBClient;
	 
	@GetMapping("/stateful")
	public String getStateful() throws Exception {
		String nodeName = "No Catch";
		try {
			RemoteStateful statefulSession = remoteEJBClient.lookupRemoteStatefulBean();
			nodeName = statefulSession.getNodeName();
			log.info("StatefulSession IncrementCounter: {}", statefulSession.getAndIncrementCounter()); 
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		return nodeName;
		
	}
	@GetMapping("/stateless")
	public String stateless() throws Exception {
		String nodeName = "No Catch";
		try {
			RemoteStateless statelessProxy = remoteEJBClient.lookupRemoteStatelessBean();
			nodeName = statelessProxy.getNodeName();
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		return nodeName;
	}
}
