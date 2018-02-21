package test.dept;

import com.learn.permission.PermissionApplication;
import com.learn.permission.common.util.JsonMapperUtil;
import com.learn.permission.configuration.RootConfiguration;
import com.learn.permission.configuration.WebConfiguration;
import com.learn.permission.permission.dao.SysDeptMapper;
import com.learn.permission.permission.model.SysDept;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = PermissionApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Slf4j
public class DeptTestApplication {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SysDeptMapper deptMapper;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void saveTest() throws Exception {
        SysDept newDept = SysDept.builder().name("test_dept")
                                    .parentId(2)
                                    .seq(1)
                                    .remark("test department description").build();
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/sys/dept/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonMapperUtil.obj2String(newDept)));

        log.info("Save dept result:{}", result.andReturn().getResponse().getContentAsString());
        //log.info("Save dept result:{}", result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateTest() throws Exception {
        SysDept dept = deptMapper.selectByPrimaryKey(13);
        dept.setName("test_dept_new");
        dept.setParentId(4);
        dept.setRemark("description new");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sys/dept/update")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(JsonMapperUtil.obj2String(dept)));
        log.info("Update dept result:{}", resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateChildDeptTest() throws Exception {
        SysDept dept = deptMapper.selectByPrimaryKey(4);
        dept.setName("UI设计new");
        dept.setParentId(3);
        dept.setSeq(1);
        dept.setRemark("UI设计部");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/sys/dept/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapperUtil.obj2String(dept)));
        log.info("Update dept result:{}", resultActions.andReturn().getResponse().getContentAsString());

    }
}
