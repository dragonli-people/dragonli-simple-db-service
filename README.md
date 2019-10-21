# dragonli-db-service-example
a example that db-service base db-core. NodeJs can use spring-jdbc by this service

1 部分通用配置详见dragonli-db-core项目的readme
1 您可以修改service.micro-service.simple-db-service来修改服务所需的配置
1 与所有服务一样，您可以设置 --MICRO_SERVICE_PORT、--MICRO_SERVICE_GROUP、--MICRO_SERVICE_HTTP_PORT分别用来覆盖微服务端口号、分组、http端口号，（尤其是在端口号冲突之时）
1 启动本服务最重要的步骤，即为服务指定数据源（DataSource）。这需要由几个步骤来完成：
    a 在配置中心（比如spring-cloud-config-server或项目自身的yml文件），以map格式定制数据源。以yml文件为例，格式大致如下：
        data-source-configs:
          datasource1:
            type: com.zaxxer.hikari.HikariDataSource
            data-config:
              jdbc-url: jdbc:mysql://192.168.7.104:3306/db1?useSSL=false&characterEncoding=utf8
              username: root
              password: 111111
              driver-class-name: com.mysql.jdbc.Driver
              hikari:
                pool-name: HikariCP
                auto-commit: false
                connection-timeout: 5000
                max-lifetime: 600000
                maximum-pool-size: 50
                minimum-idle: 5
           datasource2:
             type: com.zaxxer.hikari.HikariDataSource
             data-config:
               jdbc-url: jdbc:mysql://192.168.7.104:3306/db2?useSSL=false&characterEncoding=utf8
               username: root
               password: 111111
               driver-class-name: com.mysql.jdbc.Driver
               hikari:
                 pool-name: HikariCP
                 auto-commit: false
                 connection-timeout: 5000
                 max-lifetime: 600000
                 maximum-pool-size: 50
                 minimum-idle: 5
            ...
            (type和data-config这两个tag可以修改，参见dragonli-java-tools项目中的readme)
    b 设置启动参数 --DATA_SOURCE_CONFIG ，例，按上面的配置则应是：
        --DATA_SOURCE_CONFIG=data-source-configs.datasource1;data-source-configs.datasource2 。
        并为刚才的字符串配置分隔符，即设置启动参数 --dataSourceConfigPathSeparator=;

    c 另有一种设置数据源的方式。假如配置中心同时还配置了此节点：
        datasourceList:
          - data-source-configs.datasource1
          - data-source-configs.datasource2
        则此时设置一个启动参数 --DATA_SOURCE_CONFIG_LIST_PATH即可。例，按上面的配置应为：
        --DATA_SOURCE_CONFIG_LIST_PATH=datasourceList
  