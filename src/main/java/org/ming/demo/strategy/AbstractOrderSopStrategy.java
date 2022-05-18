package org.ming.demo.strategy;

import org.ming.demo.enmus.OrderEventEnum;
import org.ming.demo.entity.Order;

/**
 *  订单sop抽象接口
 * @author Ming
 */
public abstract class AbstractOrderSopStrategy implements OrderSopStrategyInterface {



    /**
     *  获取类型
     * @return
     */
    public abstract OrderEventEnum getType();

}
