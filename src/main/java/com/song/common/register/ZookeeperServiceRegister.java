package com.song.common.register;

import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class ZookeeperServiceRegister {
	
	private static Logger logger = LoggerFactory.getLogger(ZookeeperServiceRegister.class);
	
	private String orgName = "song";
	@Value("${serviceName}")
	private String serviceName;	
	@Autowired
	private CuratorZookeeperClient client;
	
	@PostConstruct
	private void init() throws Exception {
		final ZooKeeper zooKeeper = client.getZooKeeper();
		if (zooKeeper.exists("/" + orgName, false) == null) {
			zooKeeper.create("/" + orgName, null,
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if (zooKeeper.exists("/" + orgName + "/services", false) == null) {
			zooKeeper.create("/" + orgName + "/services", null,
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if (zooKeeper.exists("/" + orgName + "/services/" + serviceName, false) == null) {
			zooKeeper.create("/" + orgName + "/services/" + serviceName, null,
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		zooKeeper.create("/" + orgName + "/services/" + serviceName + "/" + InetAddress.getLocalHost().getHostAddress() + ":9999", null,
				ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					zooKeeper.close();
					client.close();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(),e);
				}
			}
		}));
	}

}
