package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.Query;
import com.lizhen.common.util.StringUtil;
import com.lizhen.crm.api.dto.RelationBaseDTO;
import com.lizhen.crm.api.dto.ViewRecordDTO;
import com.lizhen.crm.api.entity.*;
import com.lizhen.crm.api.service.DeProjectBaseService;
import com.lizhen.crm.kernel.dao.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class DeProjectBaseServiceImpl extends ServiceImpl<DeProjectBaseMapper, DeProjectBase> implements DeProjectBaseService {

    @Autowired
    DeClassLessonMapper deClassLessonMapper;
    @Autowired
    DeStaffMapper deStaffMapper;
    @Autowired
    TestPaperMapper testPaperMapper;
    @Autowired
    StViewRecordMapper viewRecordMapper;


    @SneakyThrows
    @Override
    public PageUtils projectList(RequestBase requestBase) {
        LambdaQueryWrapper<DeProjectBase> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(requestBase.getStartDate()!=null,DeProjectBase::getStartDate,requestBase.getStartDate())
                    .like(StringUtil.isNotEmpty(requestBase.getName()),DeProjectBase::getName,requestBase.getName())
                    .like(StringUtil.isNotEmpty(requestBase.getMasterName()),DeProjectBase::getMasterName,requestBase.getMasterName())
                    .like(StringUtil.isNotEmpty(requestBase.getCode()),DeProjectBase::getCode,requestBase.getCode())
                    .like(StringUtil.isNotEmpty(requestBase.getTp()),DeProjectBase::getType,requestBase.getTp())
                    .eq(DeProjectBase::getMerchantId,requestBase.getMerchantId())
                    .eq(DeProjectBase::getStatus,1);
        IPage<DeProjectBase> page = this.page(
                new Query<DeProjectBase>().getPage(requestBase),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void addProject(DeProjectBase projectBase) {
        this.save(projectBase);
        String lessonPaperJson = projectBase.getLessonPaperJson();
        String staffIdsStr = projectBase.getStaffIds();
        saveLessonPaperAndStaff(lessonPaperJson,staffIdsStr,projectBase);
        if (lessonPaperJson != null || staffIdsStr!=null) {
            this.updateById(projectBase);
        }
    }

    @Override
    public boolean checkKey(String updateKey) {
        if (updateKey != null) {
            String key = this.baseMapper.getUpdateKey();
            return updateKey.equals(key);
        }
        return false;
    }

    @Override
    public void updateProject(DeProjectBase projectBase) {
        String lessonPaperJson = projectBase.getLessonPaperJson();
        String staffIdsStr = projectBase.getStaffIds();
        saveLessonPaperAndStaff(lessonPaperJson,staffIdsStr,projectBase);
        this.updateById(projectBase);
    }

    @Override
    public DeProjectBase getProjectDetail(RequestBase requestBase) {
        DeProjectBase projectBase = this.getById(requestBase.getId());
        List<Integer> lessonIds = this.baseMapper.getLessonRelation(requestBase.getId());
        if (lessonIds.size()!=0){
            List<DeClassLesson> lessonList = deClassLessonMapper.selectBatchIds(lessonIds);
            projectBase.setLessonList(lessonList);
        }
        List<Integer> staffIds = this.baseMapper.getStaffRelation(requestBase.getId());
        if(staffIds.size()!=0){
            List<DeStaff> staffs = deStaffMapper.selectBatchIds(staffIds);
            projectBase.setStaffList(staffs);
        }
        return projectBase;
    }

    @Override
    public void addProjectPaper(RequestBase requestBase) {
        String paperIds = requestBase.getIds();
        if (paperIds != null) {
            List<Integer> ids = StringUtil.splitToIntList(paperIds,",");
            this.baseMapper.insertPaperRelation(requestBase.getId(),ids);
        }
    }

    @Override
    @Transactional
    public void updateProjectPaper(RequestBase requestBase) {
        String paperIds = requestBase.getIds();
        if (paperIds != null) {
            this.baseMapper.deletePaperRelation(requestBase.getId());
            List<Integer> ids = StringUtil.splitToIntList(paperIds,",");
            this.baseMapper.insertPaperRelation(requestBase.getId(),ids);
        }
    }

    @Override
    public List<DeProjectBase> getProjectList(DeStaff deStaff) {
        List<Integer> list = this.baseMapper.getProjectIds(deStaff.getId());
        if (list == null||list.size()==0) {
            return null;
        }
        List<DeProjectBase> projectBaseList = this.baseMapper.selectBatchIds(list);
        List<ViewRecordDTO> viewRecords = viewRecordMapper.selectRecordList(deStaff.getId());
        projectBaseList.forEach(projectBase -> {
            List<Integer> lessonIds = this.baseMapper.getLessonRelation(projectBase.getId());
            if (lessonIds.size()!=0){
                List<DeClassLesson> lessonList = deClassLessonMapper.selectBatchIds(lessonIds);
                projectBase.setLessonList(getViewStatus(lessonList,viewRecords));
            }
        });
        return projectBaseList;
    }

    List<DeClassLesson> getViewStatus(List<DeClassLesson> list,List<ViewRecordDTO> viewRecords){
        list.forEach(lesson->{
            long total = lesson.getVideoTime();
            lesson.setVideoTime(0L);
            lesson.setStatus(2);
            viewRecords.forEach(viewRecord->{
                if(lesson.getClassId().equals(viewRecord.getLessonId())){
                    long viewTime = viewRecord.getVideoTime();
                    if (viewTime >= 0.95 * total) {
                        lesson.setStatus(1);
                    }
                    lesson.setVideoTime(viewTime);
                }
            });
        });
        return list;
    }


    @Override
    public List<TestPaper> queryProjectPaper(RequestBase requestBase) {
        List<Integer> paperIds = this.baseMapper.getPaperRelation(requestBase.getId());
        if (paperIds.size()!=0) {
            return testPaperMapper.selectBatchIds(paperIds);
        }
        return null;
    }
    @Override
    public List<TestPaper> queryLessonPaper(RequestBase requestBase) {
        List<Integer> paperIds = this.baseMapper.getLessonPaperRelation(requestBase.getId(),requestBase.getLessonId());
        if (paperIds.size()!=0) {
            return testPaperMapper.selectBatchIds(paperIds);
        }
        return null;
    }

    public void saveLessonPaperAndStaff(String lessonPaperJson,String staffIdsStr,DeProjectBase projectBase){
        if(StringUtil.isNotEmpty(lessonPaperJson)){
            this.baseMapper.deleteLessonRelation(projectBase.getId());
            List<RelationBaseDTO> list = parseList(lessonPaperJson);
            if(list.size()!=0){
                List<Integer> lessonIds = new ArrayList<>();
                list.forEach(relationBaseDTO -> {
                    lessonIds.add(relationBaseDTO.getId());
                    if(relationBaseDTO.getIds()==null){
                        this.baseMapper.deleteLessonPaperRelation(projectBase.getId(),relationBaseDTO.getId());
                    }else if(relationBaseDTO.getIds().size()!=0){
                        this.baseMapper.deleteLessonPaperRelation(projectBase.getId(),relationBaseDTO.getId());
                        this.baseMapper.insertLessonPaperRelation(projectBase.getId(),relationBaseDTO.getId(),relationBaseDTO.getIds());
                    }
                });
                this.baseMapper.insertLessonRelation(projectBase.getId(),lessonIds);
                List<DeClassLesson> classLessons = deClassLessonMapper.selectBatchIds(lessonIds);
                AtomicReference<Long> videoTime = new AtomicReference<>(0L);
                AtomicReference<BigDecimal> classTime = new AtomicReference<>(new BigDecimal(0));
                AtomicReference<Integer> minutes = new AtomicReference<>(0);
                classLessons.forEach(lesson-> {
                    videoTime.updateAndGet(v -> v + lesson.getVideoTime());
                    classTime.updateAndGet(v ->v.add(lesson.getClassTime()));
                    minutes.updateAndGet(v -> v + lesson.getMinutes());
                });
                projectBase.setVideoTime(videoTime.get());
                projectBase.setClassTime(classTime.get());
                projectBase.setMinutes(minutes.get());
            }
        }
        if (StringUtil.isNotEmpty(staffIdsStr)) {
            this.baseMapper.deleteStaffRelation(projectBase.getId());
            List<Integer> staffIds = StringUtil.splitToIntList(staffIdsStr,",");
            this.baseMapper.insertStaffRelation(projectBase.getId(),staffIds);
            projectBase.setStaffCount(staffIds.size());
        }
    }


    public static List<RelationBaseDTO> parseList(String jsonArray){
        List<JSONObject> list = JSONObject.parseArray(jsonArray, JSONObject.class);
        List<RelationBaseDTO> res = new ArrayList<>();
        list.forEach(jsonObject -> {
            RelationBaseDTO relationBaseDTO = new RelationBaseDTO();
            relationBaseDTO.setId(jsonObject.getInteger("id"));
            if("null".equals(jsonObject.getString("ids"))){
                relationBaseDTO.setIds(null);
            }else{
                List<Integer> ids = StringUtil.splitToIntList(jsonObject.getString("ids"),",");
                relationBaseDTO.setIds(ids);
            }
            res.add(relationBaseDTO);
        });
        return res;
    }

    public static void main(String[] args) {
//        String a = "[{'id':1,'ids':'1,2'},{'id':2,'ids':''},{'id':2,'ids':'null'}]";
//        List<RelationBaseDTO> res = parseList(a);
//        System.out.println(res);
        AtomicReference<BigDecimal> classTime = new AtomicReference<>(new BigDecimal(0));
        BigDecimal b = new BigDecimal(12);
        classTime.updateAndGet(v ->v.add(b));
        System.out.println(classTime);
    }


//    public static BigDecimal getClassTime(Long videoTime){
//        BigDecimal oneClass = new BigDecimal(45*60*1000);
//        BigDecimal videoTimes = new BigDecimal(videoTime);
//        return  videoTimes.divide(oneClass,1,BigDecimal.ROUND_DOWN);
//    }

}