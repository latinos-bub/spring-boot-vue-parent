package com.yooy.mall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.yooy.mall.admin.annotation.RequirePermissionDesc;
import com.yooy.mall.core.util.ResponseUtil;
import com.yooy.mall.core.validator.Order;
import com.yooy.mall.core.validator.Sort;
import com.yooy.mall.db.domain.MallAd;
import com.yooy.mall.db.service.MallAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 22:40
 * @description:
 * @version: 1.0
 * @className: AdminAdController
 */
@RestController
@RequestMapping("/admin/ad")
@Validated
public class AdminAdController {

    private final Log logger = LogFactory.getLog(AdminAdController.class);

    @Autowired
    private mallAdService adService;


    @RequiresPermissions("admin:ad:list")
    @RequirePermissionDesc(menu={"推广管理" , "广告管理"}, button="查询")
    @GetMapping("/list")
    public Object list(String name, String content,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<MallAd> adList = adService.querySelective(name, content, page, limit, sort, order);
        return ResponseUtil.okList(adList);
    }
}
