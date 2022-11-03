package yj.processor;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import yj.annotation.Permission;
import yj.dto.MappingPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 监听Spring Application启动完成事件，用于收集url与权限
 */
public class MappingPermissionListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        //获取配置的系统信息
        String property = ac.getEnvironment().getProperty("spring.application.name");
        if (property == null) {
            throw new RuntimeException("please config：[spring.application.name]，route system name");
        }
        //获取RequestMappingHandlerMapping
        RequestMappingHandlerMapping handlerMapping = ac.getBean(RequestMappingHandlerMapping.class);
        //获取映射及方法信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<MappingPermission> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            //映射信息
            RequestMappingInfo mappingInfo = entry.getKey();
            //获取方法上的注解信息
            Permission permission = entry.getValue().getMethodAnnotation(Permission.class);
            //注解不为空则获取注解的权限信息
            String p = null;
            if (permission != null) {
                p = permission.value();
            }
            //获取url信息
            PatternsRequestCondition patternsCondition = mappingInfo.getPatternsCondition();
            //获取请求方式信息
            RequestMethodsRequestCondition methodsCondition = mappingInfo.getMethodsCondition();
            if (patternsCondition != null) {
                Set<RequestMethod> methods = methodsCondition.getMethods();
                List<String> ms = methods.stream().map(Enum::name).collect(Collectors.toList());
                Set<String> patterns = patternsCondition.getPatterns();
                for (String pattern : patterns) {
                    MappingPermission mappingPermission = new MappingPermission();
                    mappingPermission.setUrl(pattern);
                    mappingPermission.setMethods(ms);
                    //权限为空则不需要校验
                    if (p == null) {
                        mappingPermission.setNeedAuth(false);
                    } else {
                        mappingPermission.setPermission(p);
                    }
                    //是否是路径变量
                    mappingPermission.setHavePathVariable(isPathVariable(pattern));
                    list.add(mappingPermission);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            MappingPostProcessor postProcessor = ac.getBean(MappingPostProcessor.class);
            postProcessor.process(property, list);
        }

    }

    /**
     * 路径中带有 '{' '}'
     *
     * @param pattern 路径
     * @return true or false
     */
    private boolean isPathVariable(String pattern) {
        int length = pattern.length();
        boolean seenLeft = false;
        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);
            if (c == '{') {
                seenLeft = true;
            }
            if (c == '}' && seenLeft) {
                return true;
            }
        }
        return false;
    }
}
