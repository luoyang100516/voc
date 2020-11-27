package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.entity.OperatorBase;

public interface OperatorBaseMapper {

    int save(OperatorBase operatorBase);

    int update(OperatorBase operatorBase);

}