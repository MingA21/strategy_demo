package org.ming.demo.enmus;

/**
 * 订单实操枚举
 *
 * @author Ming
 */
public enum  OrderEventEnum {

    /**
     * 下单
     */
    PLACED("下单"),

    /**
     * 出库
     */
    WAREHOUSE("出库"),

    /**
     * 运输
     */
    TRANSPORT("运输"),

    /**
     * 到达
     */
    ARRIVED_STATION("到驿站"),

    /**
     * 到达
     */
    ARRIVED_CUPBOARD("到蜂巢"),

    /**
     * 签收
     */
    SIGN("签收"),

    /**
     * 取消
     */
    CANCEL("取消");



    private String zhName;

    OrderEventEnum(String zhName) {
        this.zhName = zhName;
    }

    public String getZhName() {
        return zhName;
    }


}
