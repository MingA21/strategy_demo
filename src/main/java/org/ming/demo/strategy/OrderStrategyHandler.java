package org.ming.demo.strategy;

import org.ming.demo.entity.Order;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单侧策略执行者
 *
 * @author Ming
 */
@Component
public class OrderStrategyHandler implements InitializingBean {


    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, AbstractOrderSopStrategy> strategyInterfaceMap;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractOrderSopStrategy> beansOfType
                = applicationContext.getBeansOfType(AbstractOrderSopStrategy.class);
        strategyInterfaceMap = beansOfType.values().stream()
                .collect(Collectors.toMap((item) ->
                                item.getType().name()
                        , Function.identity()));
    }


    /**
     * 执行具体策略
     *
     * @param order
     * @param event
     * @return
     */
    public String handler(Order order, String event) {
        AbstractOrderSopStrategy abstractOrderSopStrategy = strategyInterfaceMap.get(event);
        if (abstractOrderSopStrategy == null) {
            throw new IllegalArgumentException("未找到策略");
        }
        return abstractOrderSopStrategy.apply(order);
    }
}
