package org.ming.demo.d2.strategy.impl;

import org.ming.demo.controller.OrderController;
import org.ming.demo.d2.strategy.OrderEventAnnotation;
import org.ming.demo.d2.strategy.OrderSopStrategyInterface;
import org.ming.demo.enmus.OrderEventEnum;
import org.ming.demo.enmus.OrderStateEnum;
import org.ming.demo.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;


/**
 * 出库策略
 *
 * @author Ming
 */
@Component(value ="OrderWarehouseStrategy2")
@OrderEventAnnotation(event = OrderEventEnum.WAREHOUSE)
public class OrderWarehouseStrategy implements OrderSopStrategyInterface {


    @Override
    public String apply(Order order) {
        Assert.notNull(order.getId(), "参数校验失败");

        Order updateOrder = OrderController.orderMap.get(order.getId());
        if (updateOrder == null) {
            throw new RuntimeException("出库失败、商品不存在");
        }
        updateOrder.setModifyTime(LocalDateTime.now()).setState(OrderStateEnum.WAREHOUSE.name())
                .setZhState(OrderStateEnum.WAREHOUSE.getZhName());
        OrderController.orderMap.put(order.getId(), updateOrder);
        return "出库成功";
    }
}
