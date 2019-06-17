package com.yooy.mall.admin.util;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.yooy.mall.admin.annotation.RequirePermissionDesc;

/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:46
 * @description:
 * @version: 1.0
 * @className: Permission
 */
public class Permission {

    private RequiresPermissions requiresPermissions;

    private RequirePermissionDesc requirePermissionDesc;

    private String api;

    public RequiresPermissions getRequiresPermissions() {
        return requiresPermissions;
    }

    public void setRequiresPermissions(RequiresPermissions requiresPermissions) {
        this.requiresPermissions = requiresPermissions;
    }

    public RequirePermissionDesc getRequirePermissionDesc() {
        return requirePermissionDesc;
    }

    public void setRequirePermissionDesc(RequirePermissionDesc requirePermissionDesc) {
        this.requirePermissionDesc = requirePermissionDesc;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
