package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.dto.MerchantOperatorDTO;
import com.lizhen.crm.api.entity.MerchantBase;

import java.util.List;

public interface MerchantBaseMapper {

    int save(MerchantBase merchantBase);

    int update(MerchantBase merchantBase);

    List<MerchantOperatorDTO> getMerchantList(MerchantOperatorDTO merchantOperatorDTO);

    MerchantBase getMerchantDetail(MerchantOperatorDTO merchantOperatorDTO);

    int getMerchantListCount(MerchantOperatorDTO merchantOperatorDTO);

    MerchantBase getMerchantByAccount(MerchantOperatorDTO merchantOperatorDTO);
}