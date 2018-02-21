package test.dept;

import com.learn.permission.PermissionApplication;
import com.learn.permission.permission.dao.SysDeptMapper;
import com.learn.permission.permission.dao.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = PermissionApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Slf4j
public class UserTestApplication {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SysUserMapper userMapper;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    //@Rollback
    public void saveUserTest() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/sys/user/save")
                                            .param("username", "zhangdd")
                                            .param("mail", "zhangdd@163.com")
                                            .param("telephone", "1212122")
                                            .param("deptId", "4")
                                            .param("status", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                            );
        log.info("Save user result:{}", actions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateUserTest() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/sys/user/update")
                .param("id","6")
                .param("username", "zhangsanaaa")
                .param("mail", "katedd@qq.com")
                .param("telephone", "131444455355")
                .param("deptId", "4")
                .param("status", "0")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        log.info("Update user result:{}", actions.andReturn().getResponse().getContentAsString());
    }


    @Test
    public void pageTest() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/sys/user/page?deptId=4")
                                    .param("pageNo", "1")
                                    .param("pageSize", "10")
                                        .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        log.info("Page user result:{}", actions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/sys/user/delete?id=10"));
        log.info("Delete user result:{}", actions.andReturn().getResponse().getContentAsString());
    }
}
