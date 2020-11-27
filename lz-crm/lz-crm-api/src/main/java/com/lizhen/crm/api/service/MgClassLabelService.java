package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity2.MgClassLabel;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
public interface MgClassLabelService extends IService<MgClassLabel> {

    PageUtils queryPage(Map<String, Object> params);

    List<MgClassLabel> getLabelList(MgClassLabel mgClassLabel);
}

