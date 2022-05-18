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
import java.util.UUID;

/**
 * 下单策略
 *
 * @author Ming
 */
@Component(value ="OrderPlacedStrategy2")
@OrderEventAnnotation(event = OrderEventEnum.PLACED)
public class OrderPlacedStrategy  implements OrderSopStrategyInterface {



    @Override
    public String apply(Order order) {
        Assert.notNull(order.getName(), "参数校验失败");
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        Order saveOrder = new Order();
        saveOrder.setId(id).setCreateTime(LocalDateTime.now())
                .setModifyTime(order.getCreateTime())
                .setName(order.getName())
                .setState(OrderStateEnum.PLACED.name()).setZhState(OrderStateEnum.PLACED.getZhName());
        OrderController.orderMap.put(id, saveOrder);
        return "下单成功";
    }


}
