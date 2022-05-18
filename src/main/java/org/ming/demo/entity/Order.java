package org.ming.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 订单
 *
 * @author Ming
 */
@Data
@Accessors(chain = true)
public class Order {

    /**
     * 订单id
     */
    private String id;


    /**
     * 订单名字
     */
    private String name;


    /**
     * 创建日期
     */
    private LocalDateTime createTime;


    /**
     *  更新时间
     */
    private LocalDateTime modifyTime;


    /**
     * 状态
     */
    private String state;

    /**
     * 状态名称
     */
    private String zhState;
}
