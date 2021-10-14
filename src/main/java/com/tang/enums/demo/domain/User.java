package com.tang.enums.demo.domain;

import com.tang.enums.demo.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * author: tangj <br>
 * date: 2019-04-04 15:20 <br>
 * description:
 */
@Data
public class User {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "性别")
    private GenderEnum gender;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "角色名")
    private String roleName;


}
