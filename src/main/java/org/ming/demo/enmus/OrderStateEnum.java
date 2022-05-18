package org.ming.demo.enmus;


/**
 * 订单状态枚举
 *
 * @author Ming
 */
public enum OrderStateEnum {

    /**
     * 下单
     */
    PLACED("已下单"),

    /**
     * 出库
     */
    WAREHOUSE("已出库"),

    /**
     * 运输
     */
    TRANSPORT("运输中"),

    /**
     * 到达
     */
    ARRIVED_STATION("待取件-驿站"),

    /**
     * 到达
     */
    ARRIVED_CUPBOARD("待取件-蜂巢"),

    /**
     * 签收
     */
    SIG("已签收"),

    /**
     * 取消
     */
    CANCEL("已取消");


    private String zhName;

    OrderStateEnum(String zhName) {
        this.zhName = zhName;
    }

    public String getZhName() {
        return zhName;
    }
}
