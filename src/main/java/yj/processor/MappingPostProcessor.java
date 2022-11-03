package yj.processor;



import yj.dto.MappingPermission;

import java.util.List;

/**
 * 系统引入jar包后需要实现此接口
 * 获取完成映射的后置处理器
 */
public interface MappingPostProcessor {
    /**
     * 处理映射url的权限信息
     * eg: 使用dubbo推送管理平台门户
     *
     * @param system 具体的系统
     * @param list   映射信息
     */
    void process(String system, List<MappingPermission> list);
}
