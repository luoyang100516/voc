package com.lizhen.crm.biz.impl2;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.entity2.MgClassLecturer;
import com.lizhen.crm.api.service.MgClassLecturerService;
import com.lizhen.crm.kernel.dao.MgClassLecturerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class MgClassLecturerServiceImpl extends ServiceImpl<MgClassLecturerMapper, MgClassLecturer> implements MgClassLecturerService {

    @Autowired
    MgClassLecturerMapper lecturerMapper;

    @Override
    public PageUtils queryPage(RequestBase requestBase) {
        LambdaQueryWrapper<MgClassLecturer> queryWrapper = new LambdaQueryWrapper();
        if (!StringUtil.isEmpty(requestBase.getName())) {
            queryWrapper.like(MgClassLecturer::getName,requestBase.getName());
        }
        if (!StringUtil.isEmpty(requestBase.getCode())) {
            queryWrapper.like(MgClassLecturer::getCode,requestBase.getCode());
        }
        queryWrapper.eq(MgClassLecturer::getStatus,1);
        IPage<MgClassLecturer> page = this.page(
                new Query<MgClassLecturer>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<MgClassLecturer> getAllLecturer() {
        return lecturerMapper.getAllLecturer();
    }

    @Override
    public void addLecturer(MgClassLecturer mgClassLecturer) {
        mgClassLecturer.setCode(getCode());
        lecturerMapper.insert(mgClassLecturer);
    }

    private String getCode(){
        String code = lecturerMapper.getLastCode();
        if(code!=null){
            Integer next = Integer.valueOf(code.substring(1,6))+1;
            return "T"+(String.format("%05d",next));
        }else{
            return "T00001";
        }
    }


}