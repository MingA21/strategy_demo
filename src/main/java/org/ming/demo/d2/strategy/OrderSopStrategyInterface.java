package org.ming.demo.d2.strategy;

import org.ming.demo.entity.Order;

/**
 *
 *  订单策略接口
 *
 * @author Ming
 */
@FunctionalInterface
public interface OrderSopStrategyInterface {


    /**
     *
     * @param order 订单实体
     * @return
     */
     String apply(Order order);
}
