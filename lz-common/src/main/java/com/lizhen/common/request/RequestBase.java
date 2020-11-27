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
package com.lizhen.common.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RequestBase implements java.io.Serializable {

    Integer id;
    Integer merchantId;
    Integer lessonId;
    Integer projectId;
    Integer staffId;
    String staffName;
    Integer chapterId;
    String name;
    String projectName;
    String lecName;
    String masterName;
    String lesson;
    String wechat;
    String phone;
    String department;
    String idCard;
    String label;
    Integer type;
    Long videoTime;
    String tp;
    String code;
    String ids;
    String searchDate;
    Integer status;
    Integer paperStatus;
    Integer currPage;
    Integer pageSize;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

}