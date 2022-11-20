package checker.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import checker.processor.MappingPermissionListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 收集系统中的url及权限信息
 * 1、配置文件中添加 spring.application.name 配置
 * 2、实现{@link checker.processor.MappingPostProcessor} 接口
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(MappingPermissionListener.class)
public @interface EnableMappingAutoConfigurer {
}
