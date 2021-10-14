package com.tang.enums.demo.enums;

import com.tang.enums.demo.interfaces.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : tangjian
 * @date : 2021-08-19 11:18
 */
@Getter
@AllArgsConstructor
public enum PhaseEnum implements BaseEnum<String> {

    child("1", "少年"),
    teenagers("2", "青少年"),
    youth("3", "青年"),
    middle_age("4", "中年"),
    old_age("5", "老年");

    private final String code;

    private final String desc;
}
