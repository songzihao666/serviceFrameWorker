# serviceFrameWorker

分布式微服务框架，基于thrift高性能rpc调用，服务注册，服务发现，服务负载均衡，分布式服务的链路追踪，服务的断路器，降级防雪崩保护。

需要zookeeper，kafka

服务端classpath下添加config.properties

serviceName=test  //提供服务的名称，用于依赖关系计算
selectorThreads=3 //thrift selector线程数
workerThreads=10 //业务工作线程数
kafkaServer=10.211.55.5:9092 //kafka地址，集群用逗号分割
zkServer=10.211.55.3:2181 //zookeeper地址，集群用逗号分割
port=8888 //thrift监听端口
rate=1 //链路追踪采集速率百分比，最小0.01

客户端在web程序内，在web程序classpath下添加config.properties

serviceName=web  //提供服务的名称，用于依赖关系计算
workerThreads=10 //thrift服务的tcp连接数，建议与web业务线程数保持一致
kafkaServer=10.211.55.5:9092 //kafka地址，集群用逗号分割
zkServer=10.211.55.3:2181 //zookeeper地址，集群用逗号分割
rate=1 //链路追踪采集速率百分比，最小0.01
