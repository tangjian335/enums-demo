package com.tang.enums.demo.enums;

import com.tang.enums.demo.interfaces.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : tangjian
 * @date : 2021-08-17 14:16
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum<Integer> {

    male(1, "男性", "xy"),
    female(2, "女性", "xx");

    private final Integer code;

    private final String desc;

    private final String gene;

    public static GenderEnum valueOf(Integer code){
        for (GenderEnum each : values()) {
            if (each.code.equals(code)) {
                return each;
            }
        }
        return null;
    }
}
