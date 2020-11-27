package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.crm.api.dto.StProjectDTO;
import com.lizhen.crm.api.dto.StaffNFC;
import com.lizhen.crm.api.entity.StClockInRecord;
import com.lizhen.crm.api.entity.StViewRecord;
import com.lizhen.crm.api.entity2.MgClassChapter;
import com.lizhen.crm.api.service.StClockInRecordService;
import com.lizhen.crm.kernel.dao.StClockInRecordMapper;
import com.lizhen.crm.kernel.dao.StViewRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@Service
public class StClockInRecordServiceImpl extends ServiceImpl<StClockInRecordMapper, StClockInRecord> implements StClockInRecordService {

    @Autowired
    StViewRecordMapper viewRecordMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StClockInRecord> page = this.page(
                new Query<StClockInRecord>().getPage(params),
                new QueryWrapper<StClockInRecord>()
        );
        return new PageUtils(page);
    }

    @Override
    public void addViewRecord() {
        List<StProjectDTO> staffList= this.baseMapper.getStaffProject();
        Integer projectId = 0;
        Calendar cal0 = Calendar.getInstance();
        cal0.setTime(new Date());
        cal0.set(Calendar.MINUTE,0);
        cal0.set(Calendar.HOUR_OF_DAY,11);
        Date time11 = cal0.getTime();
        cal0.set(Calendar.HOUR_OF_DAY,14);
        Date time14 = cal0.getTime();
        cal0.set(Calendar.HOUR_OF_DAY,19);
        Date time19 = cal0.getTime();
        cal0.set(Calendar.HOUR_OF_DAY,23);
        Date time23 = cal0.getTime();

        List<MgClassChapter> chapterList = new ArrayList<>();
        for(StProjectDTO staff : staffList){
            if(staff.getProjectId()!=projectId) {
                projectId = staff.getProjectId();
                chapterList = this.baseMapper.getProjectChapter(projectId);
            }
            Integer firstChapter = 0;
            Integer chapterId = this.baseMapper.getStaffLastChapter(staff.getStaffId());
            if(chapterId!=null){
                firstChapter = chapterId;
            }
            Random random = new Random();
            List<StViewRecord> records = new ArrayList<>();
            Date startDate = time11;
            for(MgClassChapter chapter :chapterList){
                if(chapter.getId()>firstChapter){
                    StViewRecord startRecord = new StViewRecord();
                    startRecord.setChapterId(chapter.getId());
                    startRecord.setStaffId(staff.getStaffId());
                    startRecord.setVideoTime(0L);
                    cal0.setTime(startDate);
                    cal0.add(Calendar.SECOND,random.nextInt(1000));
                    Date start = cal0.getTime();
                    startRecord.setCreateDate(start);
                    StViewRecord endRecord = new StViewRecord();
                    endRecord.setChapterId(chapter.getId());
                    endRecord.setStaffId(staff.getStaffId());
                    endRecord.setVideoTime(chapter.getVideoTime());
                    cal0.add(Calendar.SECOND,chapter.getVideoTime().intValue()/1000);
                    Date end = cal0.getTime();
                    endRecord.setCreateDate(end);
                    records.add(startRecord);
                    records.add(endRecord);
                    if(end.getTime()<time14.getTime()||(end.getTime()>time19.getTime()&&end.getTime()<time23.getTime())){
                        startDate = end;
                    }else if(end.getTime()>time14.getTime()&&end.getTime()<time19.getTime()){
                        startDate = time19;
                    }else if(end.getTime()>time23.getTime()){
                        break;
                    }
                }
            }
            if(records.size()!=0){
                viewRecordMapper.insertBatch(records);
            }

        }
    }


    @Override
    public DataResponse addNFCSignRecord(StaffNFC staffNFC) {
        DataResponse response = new DataResponse().setMessage("打卡成功");
        try{
            Integer id = this.baseMapper.getStaffByNFC(staffNFC);
            if(id!=null){
                StClockInRecord record = new StClockInRecord();
                record.setIpUrl(staffNFC.getIpUrl());
                record.setStaffId(id);
                this.save(record);
                this.baseMapper.saveStaffNFC(record);
                return response;
            }else{
                response.setSuccess(false).setCode(500).setMessage("用户不存在");
            }
        }catch (Exception e){
            log.error(e+e.getMessage());
            response.setSuccess(false).setCode(500).setMessage("打卡失败");
        }
        return response;
    }

}