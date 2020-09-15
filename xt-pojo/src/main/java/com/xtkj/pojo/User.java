package com.xtkj.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 3594080611398247861L;

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField("login_id")
    private String loginId;

    @TableField("login_pwd")
    private String loginPwd;

    @Version
    @TableField("version")
    private Integer version;


}
