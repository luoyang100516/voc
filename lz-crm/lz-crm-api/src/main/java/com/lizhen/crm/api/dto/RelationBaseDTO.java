/*
 * Welcome to use the TableGo Tools.
 * 
 * http://www.tablego.cn
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author: bianj
 * Email: tablego@qq.com
 * Version: 6.1.0
 */
package com.lizhen.crm.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class RelationBaseDTO implements java.io.Serializable {

    Integer id;
    List<Integer> ids;

}