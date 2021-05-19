package jmu.shijh.community_system.common.sqlbuilder;

import jmu.shijh.community_system.common.util.SnowFlakeID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SqlBuilderConfig {
    public static Boolean useCamelToUnderscore = null;
    public static SnowFlakeID snowFlake = null;

    @Bean
    public SnowFlakeID snowFlakeID(
            @Value("${snowflake.work-id:1}") int workId,
            @Value("${snowflake.data-center-id:1}") int dataCenterId,
            @Value("${snowflake.sequence:1}") int sequence
    ) {
        SnowFlakeID snowFlakeID = new SnowFlakeID(workId, dataCenterId, sequence);
        snowFlake = snowFlakeID;
        return snowFlakeID;
    }

    @Value("${mybatis.configuration.map-underscore-to-camel-case:false}")
    public void setUseCamelToUnderscore(Boolean b) {
        useCamelToUnderscore = b;
    }
}
