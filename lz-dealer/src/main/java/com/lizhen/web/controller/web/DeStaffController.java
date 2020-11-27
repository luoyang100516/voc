package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.ExcelUtil;
import com.lizhen.common.util.PageUtils;
import com.lizhen.common.util.PinYinUtil;
import com.lizhen.crm.api.entity.DeDepartment;
import com.lizhen.crm.api.entity.DeStaff;
import com.lizhen.crm.api.entity.MerchantBase;
import com.lizhen.crm.api.service.DeDepartmentService;
import com.lizhen.crm.api.service.DeStaffService;
import com.lizhen.web.util.UserUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 员工表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@RestController
@RequestMapping("/staff")
public class DeStaffController {
    @Reference
    private DeStaffService deStaffService;
    @Reference
    private DeDepartmentService deDepartmentService;

    private final static String STAFF_FIELDS = "name_0,code_1,genderStr_2,phone_3,departmentName_4,idCard_5,postName_6,workType_7,wechat_8,email_9,socialNo_10,uid_11";
    /**
     * 列表
     */
    @RequestMapping("/list")
    public DataResponse list(RequestBase requestBase){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        requestBase.setMerchantId(merchantBase.getId());
        PageUtils page = deStaffService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }


    /**
     * 搜索员工
     */
    @RequestMapping("/search")
    public DataResponse search(DeStaff deStaff){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deStaff.setMerchantId(merchantBase.getId());
        List<DeStaff> staff = deStaffService.searchStaff(deStaff);
        return new DataResponse().setData(staff);
    }


    /**
     * 信息
     */
    @RequestMapping("/info")
    public DataResponse info(DeStaff deStaff){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deStaff.setMerchantId(merchantBase.getId());
		DeStaff staff = deStaffService.getInfo(deStaff);
        return new DataResponse().setData(staff);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public DataResponse save(DeStaff deStaff){
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deStaff.setMerchantId(merchantBase.getId());
        deStaff.setAccount(PinYinUtil.toPinyin(deStaff.getName()));
		deStaffService.save(deStaff);
        return new DataResponse();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public DataResponse update(DeStaff deStaff){
        return getDataResponse(deStaff);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public DataResponse delete(DeStaff deStaff){
        deStaff.setStatus(2);
        return getDataResponse(deStaff);
    }

    private DataResponse getDataResponse(DeStaff deStaff) {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        deStaff.setMerchantId(merchantBase.getId());
        deStaffService.updateStaff(deStaff);
        return new DataResponse();
    }

    @RequestMapping(value = "importStaff")
    public DataResponse importStaff(MultipartFile file) throws Exception {
        MerchantBase merchantBase = UserUtil.getCurrentMerchant();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        // 根据模板文件生成workbook对象
        Workbook workbook;
        if (ext.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (ext.equals("xls")) {
            workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
        } else {
            return new DataResponse().setSuccess(false).setCode(ResCode.FAIL.getValue()).setMessage("不支持的文件类型");
        }
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return  new DataResponse().setSuccess(false).setCode(ResCode.FAIL.getValue()).setMessage("内容为空");
        }

        //从excel中读取数据转换成对应实体类
        //对应类中的字段 多个字段用，隔开。 格式如：  name_i,status_n,children.name_ii,children.status_nn 其中 _ 左边为字段名称，右边为对应excel中列标号。
        List<DeStaff> list =  ExcelUtil.toBeanFromExcel(sheet,STAFF_FIELDS,DeStaff.class, 1);
        StringBuilder sb=new StringBuilder();
        AtomicInteger count= new AtomicInteger();
        AtomicInteger failureNum= new AtomicInteger();
        AtomicInteger colNum= new AtomicInteger();
        if (list!=null&&list.size()>0){
            RequestBase requestBase = new RequestBase();
            requestBase.setId(merchantBase.getId());
            List<DeDepartment> departmentList = deDepartmentService.getAll(requestBase);
            list.forEach(staff->{
                try{
                    colNum.getAndIncrement();
                    String genderStr = staff.getGenderStr();
                    if("男".equals(genderStr)) {
                        staff.setGender(1);
                    }else if ("女".equals(genderStr)){
                        staff.setGender(2);
                    }else {
                        staff.setGender(3);
                    }
                    String departmentName = staff.getDepartmentName();
                    for(DeDepartment department : departmentList){
                        if(department.getName().equals(departmentName)){
                            staff.setDepartmentId(department.getId());
                            break;
                        }
                    }
                    if (staff.getDepartmentId() == null) {
                        throw new RuntimeException("部门有误");
                    }
                    staff.setAccount(PinYinUtil.toPinyin(staff.getName()));
                    staff.setMerchantId(merchantBase.getId());
                    deStaffService.save(staff);
                    count.getAndIncrement();
                }catch (Exception e){
                    sb.append("第")
                            .append(colNum.get())
                            .append("行导入失败,失败信息：")
                            .append(e.getMessage()).append("$");
                    failureNum.getAndIncrement();
                }
            });
        }
        if (sb.length()!=0){
            DataResponse response = new DataResponse().setMessage("成功导入："+count+"条用户信息,"+failureNum+"条用户导入失败:$"+sb.toString());
            if(count.get()==0){
                response.setSuccess(false).setCode(ResCode.FAIL.getValue());
            }
            return response;
        }else{
            return new DataResponse().setMessage("成功导入："+count+"条用户信息,无失败记录");
        }
    }


}
