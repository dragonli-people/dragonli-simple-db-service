# dragonli-db-service-example:   
# simple db-service base db-core. NodeJs can use spring-jdbc by this service  

* 部分通用配置详见dragonli-db-core项目的readme  
* 您可以修改service.micro-service.simple-db-service来修改服务所需的配置  
* 与所有服务一样，您可以设置 --MICRO_SERVICE_PORT、--MICRO_SERVICE_GROUP、--MICRO_SERVICE_HTTP_PORT分别用来覆盖微服务端口号、分组、http端口号，（尤其是在端口号冲突之时）  
* 启动本服务最重要的步骤，即为服务指定数据源（DataSource）。此处详见config-datasource.readme.txt  
