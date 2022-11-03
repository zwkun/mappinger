package yj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import yj.processor.MappingPermissionListener;

/**
 * 收集系统中的url及权限信息
 * 1、配置文件中添加 mapping.permission.system 配置
 * 2、实现{@link yj.processor.MappingPostProcessor} 接口
 */
@Configuration
@Import(MappingPermissionListener.class)
public class MappingAutoConfigurer {
}
