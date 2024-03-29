/**
 *
 */
package org.dragonli.service.general.dbservice;

import com.alibaba.dubbo.config.annotation.Service;
import org.dragonli.service.db.service.DbCore;
import org.dragonli.service.general.interfaces.general.DbService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author dev
 *
 */
@Service(interfaceClass = DbService.class, register = true, timeout = 6000, retries = -1, delay = -1)
public class DbServiceImpl extends DbCore {
    @Autowired
    RedissonClient redissonClient;
    @Value("${DATA_SOURCE_CONFIG:}")
    private String dataSourceConfigValue;
    @Value("${DATA_SOURCE_CONFIG_SEPARATOR:}")
    private String dataSourceConfigPathSeparator;
    @Value("${DATA_SOURCE_CONFIG_LIST_PATH:}")
    private String dataSourceConfigListPath;

    @Value("${service.general.db-config.redisKey:}")
    String redisKeyValue;
    @Value("${service.general.db-config.defaultDbName:}")
    String defaultDbNameValue;
    @Value("${service.general.db-config.autoUpdateTableName:db_service_update}")
    String autoUpdateTableNameValue;
    @Value("${service.general.db-config.primaryKey:id}")
    String primaryKeyValue;
    @Value("${service.general.db-config.versionKey:version}")
    String versionKeyValue;
    @Value("${service.general.db-config.tableNameTag:__TABLE_NAME}")
    String tableNameTagValue;

    @PostConstruct
    public void initService() throws Exception {
        redisKeyValue = redisKeyValue !=null && !"".equals(redisKeyValue=redisKeyValue.trim()) ? redisKeyValue : null;
        defaultDbNameValue = defaultDbNameValue !=null && !"".equals(
                defaultDbNameValue = defaultDbNameValue.trim()) ? defaultDbNameValue : null;
        this.initGeneralConfig(redisKeyValue, defaultDbNameValue,autoUpdateTableNameValue,primaryKeyValue,versionKeyValue,tableNameTagValue);

        if (null != dataSourceConfigValue && !"".equals(dataSourceConfigValue = dataSourceConfigValue.trim()) &&
                dataSourceConfigPathSeparator != null &&
                !"".equals(dataSourceConfigPathSeparator = dataSourceConfigPathSeparator.trim()))
        {
            super.initByConfig(redissonClient,dataSourceConfigValue,dataSourceConfigPathSeparator);
            return;
        }
        if (null != dataSourceConfigListPath && !"".equals(dataSourceConfigListPath = dataSourceConfigListPath.trim()) )
        {
            super.initByConfig(redissonClient,dataSourceConfigListPath);
            return;
        }
        throw new Exception("plz config ( --DATA_SOURCE_CONFIG and --DATA_SOURCE_CONFIG_SEPARATOR ) or --DATA_SOURCE_CONFIG_LIST_PATH ");
    }
}
