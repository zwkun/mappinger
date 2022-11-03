package yj.dto;

import java.util.List;

/**
 * url映射权限信息
 */
public class MappingPermission {
    /**
     * url
     */
    private String url;
    /**
     * 请求方式
     */
    private List<String> methods;
    /**
     * 权限
     */
    private String permission;
    /**
     * 是否需要校验
     */
    private Boolean needAuth = true;
    /**
     * url是否是路径变量
     */
    private Boolean havePathVariable = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(Boolean needAuth) {
        this.needAuth = needAuth;
    }

    public Boolean getHavePathVariable() {
        return havePathVariable;
    }

    public void setHavePathVariable(Boolean havePathVariable) {
        this.havePathVariable = havePathVariable;
    }
}
