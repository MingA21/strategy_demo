package org.ming.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 返回对象
 * @author Ming
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Result<T> {

    private String message;

    private T data;

    public static <T> Result<T> success(String message, T t) {
        return new Result(message, t);
    }


    public static <T> Result<T> success(String message) {
        return new Result(message, null);
    }
}
