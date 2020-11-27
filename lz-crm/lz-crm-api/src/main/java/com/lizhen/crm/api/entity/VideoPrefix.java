package com.lizhen.crm.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VideoPrefix implements Serializable {
    private Integer id;
    private String name;
    private Integer parentId;
    private List<VideoPrefix> children;
}
