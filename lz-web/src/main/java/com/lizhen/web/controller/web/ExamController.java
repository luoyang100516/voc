package com.lizhen.web.controller.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lizhen.common.request.RequestBase;
import com.lizhen.common.response.DataResponse;
import com.lizhen.common.response.ResCode;
import com.lizhen.common.util.ExcelUtil;
import com.lizhen.common.util.PageUtils;
import com.lizhen.crm.api.entity.TestPaper;
import com.lizhen.crm.api.entity.TestQuestion;
import com.lizhen.crm.api.service.TestPaperService;
import com.lizhen.crm.api.service.TestQuestionService;
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
 * 试卷表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@RestController
@RequestMapping("/exam")
public class ExamController {
    @Reference
    private TestPaperService testPaperService;
    @Reference
    private TestQuestionService testQuestionService;

    private final static String QUESTION_FIELDS = "name_0,description_1,typeStr_2,score_3,answer_4,label_5";

    @RequestMapping(value = "importQuestion")
    public DataResponse importStaff(MultipartFile file) throws Exception {
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
        List<TestQuestion> list =  ExcelUtil.toBeanFromExcel(sheet,QUESTION_FIELDS,TestQuestion.class, 1);
        StringBuilder sb=new StringBuilder();
        AtomicInteger count= new AtomicInteger();
        AtomicInteger failureNum= new AtomicInteger();
        AtomicInteger colNum= new AtomicInteger();
        if (list!=null&&list.size()>0){
            list.forEach(question->{
                try{
                    colNum.getAndIncrement();
                    String typeStr = question.getTypeStr();
                    if("单选".equals(typeStr)) {
                        question.setType(1);
                    }else if ("判断".equals(typeStr)){
                        question.setType(2);
                    }else {
                        throw new RuntimeException("题目类型有误");
                    }
                    String description = question.getDescription();
                    question.setDescription(getDescription(description));
                    testQuestionService.save(question);
                    count.getAndIncrement();
                }catch (Exception e){
                    e.printStackTrace();
                    sb.append("第")
                    .append(colNum.get())
                    .append("行导入失败,失败信息：")
                    .append(e.getMessage()).append("$");
                    failureNum.getAndIncrement();
                }
            });
        }
        if (sb.length()!=0){
            DataResponse response = new DataResponse().setMessage("成功导入："+count+"条题目,"+failureNum+"条题目导入失败:$"+sb.toString());
            if(count.get()==0){
                response.setSuccess(false).setCode(ResCode.FAIL.getValue());
            }
            return response;
        }else{
            return new DataResponse().setMessage("成功导入："+count+"条题目,无失败记录");
        }
    }

    public static String getDescription(String description){
        JSONArray array = new JSONArray();
        for(String item : description.split("；")){
            JSONObject object = new JSONObject();
            object.put("key",item.split("，")[0]);
            object.put("value",item.replaceFirst(item.split("，")[0]+"，",""));
            array.add(object);
        }
        return array.toJSONString();
    }

    /**
     * 保存试题
     */
    @RequestMapping("/addQuestion")
    public DataResponse addQuestion(TestQuestion testQuestion){
        testQuestionService.addQuestion(testQuestion);
        return new DataResponse();
    }

    /**
     * 题目列表
     */
    @RequestMapping("/getQuestionList")
    public DataResponse getQuestionList(RequestBase requestBase){
        PageUtils page = testQuestionService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }

    /**
     * 题目详情编辑页
     */
    @RequestMapping("/getQuestionDetail")
    public DataResponse getQuestionDetail(RequestBase requestBase){
        TestQuestion testQuestion = testQuestionService.getById(requestBase.getId());
        return new DataResponse().setData(testQuestion);
    }

    /**
     * 编辑题目
     */
    @RequestMapping("/updateQuestion")
    public DataResponse updateQuestion(TestQuestion testQuestion){
        testQuestionService.updateQuestionById(testQuestion);
        return new DataResponse();
    }

    /**
     * 停用启用题目
     */
    @RequestMapping("/notifyQuestion")
    public DataResponse notifyQuestion(RequestBase requestBase){
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setId(requestBase.getId());
        testQuestion.setStatus(requestBase.getStatus());
        testQuestionService.updateById(testQuestion);
        return new DataResponse();
    }

    /**
     * 试卷添加-题目列表，根据课程名称和标签搜索课程关联题目
     */
    @RequestMapping("/searchQuestion")
    public DataResponse searchQuestion(RequestBase requestBase){
        List<TestQuestion> list = testQuestionService.searchQuestion(requestBase);
        return new DataResponse().setData(list);
    }


    /**
     * 试卷列表
     */
    @RequestMapping("/getPaperList")
    public DataResponse getPaperList(RequestBase requestBase){
        PageUtils page = testPaperService.queryPage(requestBase);
        return new DataResponse().setData(page);
    }
    /**
     * 试卷详情
     */
    @RequestMapping("/getPaperDetail")
    public DataResponse getPaperDetail(RequestBase requestBase){
        TestPaper testPaper = testPaperService.getById(requestBase.getId());
        return new DataResponse().setData(testPaper);
    }
    /**
     * 试卷关联试题
     */
    @RequestMapping("/getPaperQuestions")
    public DataResponse getPaperQuestions(RequestBase requestBase){
        List<TestQuestion> list = testQuestionService.getQuestions(requestBase.getId());
        return new DataResponse().setData(list);
    }

    /**
     * 保存试卷
     */
    @RequestMapping("/addPaper")
    public DataResponse addPaper(TestPaper testPaper){
        testPaperService.addPaper(testPaper);
        return new DataResponse();
    }


    /**
     * 编辑试卷
     */
    @RequestMapping("/updatePaper")
    public DataResponse updatePaper(TestPaper testPaper){
        testPaperService.updatePaper(testPaper);
        return new DataResponse();
    }

    /**
     * 发布取消试卷
     */
    @RequestMapping("/publishPaper")
    public DataResponse publishPaper(RequestBase requestBase){
        TestPaper testPaper = new TestPaper();
        testPaper.setId(requestBase.getId());
        testPaper.setPaperStatus(requestBase.getPaperStatus());
        testPaperService.updateById(testPaper);
        return new DataResponse();
    }

    /**
     * 启用停用试卷
     */
    @RequestMapping("/notifyPaper")
    public DataResponse notifyPaper(RequestBase requestBase){
        TestPaper testPaper = new TestPaper();
        testPaper.setId(requestBase.getId());
        testPaper.setStatus(requestBase.getStatus());
        testPaperService.updateById(testPaper);
        return new DataResponse();
    }

}
