package de.akquinet.jbosscc.cluster.client.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rmote.ejb", ignoreInvalidFields = true)
public class HostsAndPortsProperties {
	/**
	 * base URL
	 */
	private String[] hostsAndPorts;
}
