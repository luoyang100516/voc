package com.lizhen.crm.api.service;

import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtil;
import com.lizhen.crm.api.dto.MerchantOperatorDTO;

public interface MerchantService {

    DataResponse addMerchant(MerchantOperatorDTO merchantOperatorDTO);

    PageUtil<MerchantOperatorDTO> getMerchantList(MerchantOperatorDTO merchantOperatorDTO);

    DataResponse getMerchantDetail(MerchantOperatorDTO merchantOperatorDTO);

    DataResponse updateMerchant(MerchantOperatorDTO merchantOperatorDTO);

    DataResponse login(MerchantOperatorDTO merchantOperatorDTO) throws Exception;

    DataResponse getStaffIndexInfo(String url);

    DataResponse getWxStaffIndexInfo(String url,String wxId);

    DataResponse getMerchantIndexInfo(String url);

}
