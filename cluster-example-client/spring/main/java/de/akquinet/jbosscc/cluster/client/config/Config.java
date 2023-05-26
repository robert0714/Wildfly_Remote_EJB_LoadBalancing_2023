package de.akquinet.jbosscc.cluster.client.config;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.akquinet.jbosscc.cluster.client.RemoteEJBClient; 
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
public class Config {
	
	@Bean
	public RemoteEJBClient reportFactory(@Autowired HostsAndPortsProperties data) {
		final String[] lines = data.getHostsAndPorts();
		RemoteEJBClient remoteEJBClient = null;
		try {
			remoteEJBClient = new RemoteEJBClient(lines);
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}
		return remoteEJBClient;
	}
}
