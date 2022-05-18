package org.ming.demo.d2.strategy;


import org.ming.demo.enmus.OrderEventEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OrderEventAnnotation {

    OrderEventEnum event();

}
