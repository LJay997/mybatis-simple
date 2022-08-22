package tk.mybatis.simple.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 创建信息
 */
@Getter
@Setter
public class CreateInfo {
    private String createBy;
    private Date createTime;

}
