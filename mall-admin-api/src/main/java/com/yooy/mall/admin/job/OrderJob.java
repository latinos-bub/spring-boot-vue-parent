package com.yooy.mall.admin.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yooy.mall.core.system.SystemConfig;
import com.yooy.mall.db.domain.LitemallGoodsProduct;
import com.yooy.mall.db.domain.MallOrder;
import com.yooy.mall.db.domain.MallOrderGoods;
import com.yooy.mall.db.service.LitemallGoodsProductService;
import com.yooy.mall.db.service.LitemallOrderGoodsService;
import com.yooy.mall.db.service.LitemallOrderService;
import com.yooy.mall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author: util.youl.com@gmail.com
 * @date: 2019/6/17 23:02
 * @description: 检测订单状态
 * @version: 1.0
 * @className: OrderJob
 */
public class OrderJob {

    private final Log logger = LogFactory.getLog(OrderJob.class);

    @Autowired
    private MallOrderGoodsService orderGoodsService;

    @Autowired
    private MallOrderService orderService;

    @Autowired
    private MallGoodsProductService productService;

    /**
     * 自动取消订单
     * <p>
     * 定时检查订单未付款情况，如果超时 LITEMALL_ORDER_UNPAID 分钟则自动取消订单
     * 定时时间是每次相隔半个小时。
     * <p>
     * TODO
     * 注意，因为是相隔半小时检查，因此导致订单真正超时时间是 [LITEMALL_ORDER_UNPAID, 30 + LITEMALL_ORDER_UNPAID]
     */
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void checkOrderUnpaid() {
        logger.info("系统开启任务检查订单是否已经超期自动取消订单");

        List<MallOrder> orderList = orderService.queryUnpaid(SystemConfig.getOrderUnpaid());
        for (MallOrder order : orderList) {
            // 设置订单已取消状态
            order.setOrderStatus(OrderUtil.STATUS_AUTO_CANCEL);
            order.setEndTime(LocalDateTime.now());
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                throw new RuntimeException("更新数据已失效");
            }

            // 商品货品数量增加
            Integer orderId = order.getId();
            List<MallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
            for (MallOrderGoods orderGoods : orderGoodsList) {
                Integer productId = orderGoods.getProductId();
                Short number = orderGoods.getNumber();
                if (productService.addStock(productId, number) == 0) {
                    throw new RuntimeException("商品货品库存增加失败");
                }
            }
            logger.info("订单 ID=" + order.getId() + " 已经超期自动取消订单");
        }
    }

    /**
     * 自动确认订单
     * <p>
     * 定时检查订单未确认情况，如果超时 LITEMALL_ORDER_UNCONFIRM 天则自动确认订单
     * 定时时间是每天凌晨3点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [LITEMALL_ORDER_UNCONFIRM, 1 + LITEMALL_ORDER_UNCONFIRM]
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void checkOrderUnconfirm() {
        logger.info("系统开启任务检查订单是否已经超期自动确认收货");

        List<MallOrder> orderList = orderService.queryUnconfirm(SystemConfig.getOrderUnconfirm());
        for (MallOrder order : orderList) {

            // 设置订单已取消状态
            order.setOrderStatus(OrderUtil.STATUS_AUTO_CONFIRM);
            order.setConfirmTime(LocalDateTime.now());
            if (orderService.updateWithOptimisticLocker(order) == 0) {
                logger.info("订单 ID=" + order.getId() + " 数据已经更新，放弃自动确认收货");
            } else {
                logger.info("订单 ID=" + order.getId() + " 已经超期自动确认收货");
            }
        }
    }

    /**
     * 可评价订单商品超期
     * <p>
     * 定时检查订单商品评价情况，如果确认商品超时 LITEMALL_ORDER_COMMENT 天则取消可评价状态
     * 定时时间是每天凌晨4点。
     * <p>
     * TODO
     * 注意，因为是相隔一天检查，因此导致订单真正超时时间是 [LITEMALL_ORDER_COMMENT, 1 + LITEMALL_ORDER_COMMENT]
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkOrderComment() {
        logger.info("系统开启任务检查订单是否已经超期未评价");

        LocalDateTime now = LocalDateTime.now();
        List<MallOrder> orderList = orderService.queryComment(SystemConfig.getOrderComment());
        for (MallOrder order : orderList) {
            order.setComments((short) 0);
            orderService.updateWithOptimisticLocker(order);

            List<MallOrderGoods> orderGoodsList = orderGoodsService.queryByOid(order.getId());
            for (MallOrderGoods orderGoods : orderGoodsList) {
                orderGoods.setComment(-1);
                orderGoodsService.updateById(orderGoods);
            }
        }
    }

}
