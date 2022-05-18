package org.ming.demo.controller;


import org.ming.demo.enmus.OrderEventEnum;
import org.ming.demo.enmus.OrderStateEnum;

import org.ming.demo.entity.Order;
import org.ming.demo.entity.Result;
import org.ming.demo.strategy.OrderSopStrategyInterface;
import org.ming.demo.strategy.OrderStrategyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * 订单接口
 *
 * @author Ming
 */
@RestController
@RequestMapping("order")
public class OrderController {

    /**
     * 模拟数据库
     */
    public static Map<String, Order> orderMap = new HashMap<String, Order>();

    /**
     * 下单接口
     *
     * @param id
     * @return
     */
    @PostMapping("find")
    public Result<Order> find(@RequestParam String id) {
        return Result.<Order>success("查询成功", orderMap.get(id));
    }

    /**
     * 下单接口
     *
     * @return
     */
    @PostMapping("findAll")
    public Result<Map> findAll() {
        return Result.<Map>success("查询成功", orderMap);
    }


    /**
     * 下单接口
     *
     * @param name
     * @return
     */
    @PostMapping("place")
    public Result place(@RequestParam String name) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        Order order = new Order();
        order.setId(id).setCreateTime(LocalDateTime.now())
                .setModifyTime(LocalDateTime.now())
                .setName(name)
                .setState(OrderStateEnum.PLACED.name()).setZhState(OrderStateEnum.PLACED.getZhName());
        orderMap.put(id, order);
        return Result.success("下单成功");
    }


    /**
     * 出库
     *
     * @param id
     * @return
     */
    @PostMapping("warehouse")
    public Result warehouse(@RequestParam String id) {

        Order order = orderMap.get(id);
        if (order == null) {
            throw new RuntimeException("出库失败、商品不存在");
        }
        order.setModifyTime(LocalDateTime.now()).setState(OrderStateEnum.WAREHOUSE.name())
                .setZhState(OrderStateEnum.WAREHOUSE.getZhName());
        orderMap.put(id, order);
        return Result.success("出库成功");
    }


    /**
     * 实操节点操作
     *
     * @param order
     * @param event
     * @return
     */
    @PostMapping("sop")
    public Result sop(Order order, OrderEventEnum event) {

        String message;

        // 下单
        if (event.name().equals(OrderEventEnum.PLACED.name())) {

            Assert.notNull(order.getName(), "参数校验失败");

            String id = UUID.randomUUID().toString().replaceAll("-", "");
            Order saveOrder = new Order();
            saveOrder.setId(id).setCreateTime(LocalDateTime.now())
                    .setModifyTime(LocalDateTime.now())
                    .setName(order.getName())
                    .setState(OrderStateEnum.PLACED.name()).setZhState(OrderStateEnum.PLACED.getZhName());
            orderMap.put(id, saveOrder);
            message = "下单成功";

        } else if (event.name().equals(OrderStateEnum.WAREHOUSE.name())) {

            Assert.notNull(order.getId(), "参数校验失败");

            Order updateOrder = orderMap.get(order.getId());
            if (updateOrder == null) {
                throw new RuntimeException("出库失败、商品不存在");
            }
            updateOrder.setModifyTime(LocalDateTime.now()).setState(OrderStateEnum.WAREHOUSE.name())
                    .setZhState(OrderStateEnum.WAREHOUSE.getZhName());
            orderMap.put(order.getId(), updateOrder);
            message = "出库成功";

        } else {
            message = "暂不支持操作";
        }
        // else if n个
        return Result.success(message);
    }


    /**
     * 实操节点操作
     *
     * @param order
     * @param event
     * @return
     */
    @PostMapping("sop2")
    public Result sop2(Order order, OrderEventEnum event) {
        String message;
        // 下单
        if (event.name().equals(OrderEventEnum.PLACED.name())) {
            message = placeMethod(order);
        }
        // 出库
        else if (event.name().equals(OrderStateEnum.WAREHOUSE.name())) {
            message = warehouseMethod(order);
        } else {
            message = "暂不支持此操作";
        }
        // else if n个
        return Result.success(message);
    }


    private String placeMethod(Order order) {
        Assert.notNull(order.getName(), "参数校验失败");
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        Order saveOrder = new Order();
        saveOrder.setId(id).setCreateTime(LocalDateTime.now())
                .setModifyTime(LocalDateTime.now())
                .setName(order.getName())
                .setState(OrderStateEnum.PLACED.name()).setZhState(OrderStateEnum.PLACED.getZhName());
        orderMap.put(id, saveOrder);
        return "下单成功";
    }

    private String warehouseMethod(Order order) {
        Assert.notNull(order.getId(), "参数校验失败");

        Order updateOrder = orderMap.get(order.getId());
        if (updateOrder == null) {
            throw new RuntimeException("出库失败、商品不存在");
        }
        updateOrder.setModifyTime(LocalDateTime.now()).setState(OrderStateEnum.WAREHOUSE.name())
                .setZhState(OrderStateEnum.WAREHOUSE.getZhName());
        orderMap.put(order.getId(), updateOrder);
        return "出库成功";
    }


    /**
     * 实操节点操作
     *
     * @param order
     * @param event
     * @return
     */
    @PostMapping("sop3")
    public Result sop3(Order order, OrderEventEnum event) {

        // 存储事件、函数映射
        Map<String, Function<Order, String>> methodMap = new HashMap<>();

        // 下单
        methodMap.put(OrderEventEnum.PLACED.name(), new Function<Order, String>() {
            @Override
            public String apply(Order o) {
                return placeMethod(o);
            }
        });

        // 出库
        methodMap.put(OrderEventEnum.WAREHOUSE.name(), (orderReq) ->
                warehouseMethod(orderReq));

        if (methodMap.get(event.name()) == null) {
            return Result.success("暂不支持此操作");
        } else {
            String message = methodMap.get(event.name()).apply(order);
            return Result.success(message);
        }

    }


    /**
     * 实操节点操作
     *
     * @param order
     * @param event
     * @return
     */
    @PostMapping("sop4")
    public Result sop4(Order order, OrderEventEnum event) {

        Map<String, OrderSopStrategyInterface> methodMap = new HashMap<>();

        methodMap.put(OrderEventEnum.PLACED.name(), (orderReq) ->
                placeMethod(orderReq));

        methodMap.put(OrderEventEnum.WAREHOUSE.name(), (orderReq) ->
                warehouseMethod(orderReq));

        if (methodMap.get(event.name()) == null) {
            return Result.success("暂不支持此操作");
        } else {
            String message = methodMap.get(event.name()).apply(order);
            return Result.success(message);
        }
    }


    // 1、事件类型与函数的对应关系处理

    // 2、将不同事件的处理逻辑分散到不同的实现类中


    @Autowired
    OrderStrategyHandler orderStrategyHandler;


    /**
     * 实操节点操作
     *
     * @param order
     * @param event
     * @return
     */
    @PostMapping("sop5")
    public Result sop5(Order order, OrderEventEnum event) {
        String message = orderStrategyHandler.handler(order, event.name());
        return Result.success(message);
    }
}
