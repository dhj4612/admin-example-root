package org.example.framework.database.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.framework.database.core.AutoFillMetaObjectHandler;
import org.example.framework.database.core.DbEncryptHelper;
import org.example.framework.database.core.DbFieldCryptoHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties({DbProperties.class})
public class MyBatisPlusConfiguration {

    private final DbProperties dbProperties;

    @PostConstruct
    public void dbEncryptHelperInit() {
        DbEncryptHelper.init(dbProperties.getAesKey());
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new AutoFillMetaObjectHandler();
    }

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    @ConditionalOnProperty(name = "db-aes-key")
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 注册自定义的类型处理器
            configuration.getTypeHandlerRegistry().register(DbFieldCryptoHandler.class);
            //configuration.addInterceptor(new MyBatisEncryptDecryptInterceptor());
        };
    }
}
