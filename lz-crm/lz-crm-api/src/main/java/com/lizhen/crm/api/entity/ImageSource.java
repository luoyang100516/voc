package com.lizhen.crm.api.entity;
import lombok.Data;
import java.io.Serializable;

@Data
public class ImageSource  implements Serializable {

    private Integer id;

    private Integer type;

    private Integer reviewStatus;

    private String url;

    private String remark;
}