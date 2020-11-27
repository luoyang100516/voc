package test;

import com.lizhen.crm.api.service.StClockInRecordService;
import com.lizhen.crm.biz.LzCrmApplication;
import com.lizhen.crm.biz.impl.StClockInRecordServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LzCrmApplication.class)
@RunWith(SpringRunner.class)
public class NFCTest {

    @Autowired
    private StClockInRecordServiceImpl clockInRecordService;

    @Test
    public void nfcTest(){
        clockInRecordService.addViewRecord();
    }
}
