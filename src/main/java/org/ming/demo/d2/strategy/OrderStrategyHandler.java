package org.ming.demo.d2.strategy;

import cn.hutool.core.lang.ClassScanner;
import org.ming.demo.entity.Order;
import org.ming.demo.strategy.AbstractOrderSopStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单侧策略执行者
 *
 * @author Ming
 */
@Component(value = "OrderStrategyHandler2")
public class OrderStrategyHandler implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, OrderSopStrategyInterface> strategyInterfaceMap;


    @Override
    public void afterPropertiesSet() throws Exception {
        strategyInterfaceMap = new HashMap<>();

        ClassScanner.scanPackageByAnnotation("org.ming.demo.d2.strategy.impl", OrderEventAnnotation.class).stream().forEach(
                clazz -> {
                    String name = clazz.getAnnotation(OrderEventAnnotation.class).event().name();
                    OrderSopStrategyInterface contextBean = (OrderSopStrategyInterface) applicationContext.getBean(clazz);
                    strategyInterfaceMap.put(name, contextBean);
                }
        );
        System.out.println("加载完毕");
    }


    /**
     * 执行具体策略
     *
     * @param order
     * @param event
     * @return
     */
    public String handler(Order order, String event) {
        OrderSopStrategyInterface abstractOrderSopStrategy = strategyInterfaceMap.get(event);
        if (abstractOrderSopStrategy == null) {
            throw new IllegalArgumentException("未找到策略");
        }
        return abstractOrderSopStrategy.apply(order);
    }


}
