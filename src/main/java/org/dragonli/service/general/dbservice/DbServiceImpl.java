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
    String redisKey;
    @Value("${service.general.db-config.defaultDbName:}")
    String defaultDbName;
    @Value("${service.general.db-config.autoUpdateTableName:db_service_update}")
    String autoUpdateTableName;
    @Value("${service.general.db-config.primaryKey:id}")
    String primaryKey;
    @Value("${service.general.db-config.versionKey:version}")
    String versionKey;
    @Value("${service.general.db-config.tableNameTag:__TABLE_NAME}")
    String tableNameTag;

    @PostConstruct
    public void initService() throws Exception {
        redisKey = redisKey !=null && !"".equals(redisKey=redisKey.trim()) ? redisKey : null;
        defaultDbName = defaultDbName !=null && !"".equals(defaultDbName=defaultDbName.trim()) ? defaultDbName : null;
        this.initGenralConfig(redisKey,defaultDbName,autoUpdateTableName,primaryKey,versionKey,tableNameTag);

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
