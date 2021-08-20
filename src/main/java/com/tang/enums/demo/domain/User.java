package com.tang.enums.demo.domain;

import com.tang.enums.demo.enums.GenderEnum;
import com.tang.enums.demo.enums.PhaseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * author: tangj <br>
 * date: 2019-04-04 15:20 <br>
 * description:
 */
@Data
public class User {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("性别")
    private GenderEnum gender;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("角色名")
    private String roleName;

}
