package cn.yanglj65.www.eloth_backend;

import cn.yanglj65.www.eloth_backend.entity.Result;
import cn.yanglj65.www.eloth_backend.entity.User;
import cn.yanglj65.www.eloth_backend.service.BugService;
import cn.yanglj65.www.eloth_backend.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElothBackendApplication.class)//指定启动类
@Transactional
@Rollback(true)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElothBackendApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    BugService bugService;
    @Autowired
    MockMvc mockMvc;
    private User user = null;

    @Before
    public void beforeTest() {
        Result result = userService.login("yanglj012", "123456");
        user = (User) (result.getData());
        System.out.println(result);
        Assert.assertEquals(0, result.getErrCode());
        Assert.assertNotNull(user);
    }

    @Test
    public void aTestRegister() {
        Result result = userService.register("yanglj333", "123456", "17665070189");
        System.out.println(result);
        Assert.assertEquals(0, result.getErrCode());
    }

//    @Test
//    @Ignore("before")
//    public void bTestLogin() {
//        Result result = userService.login("yanglj012", "123456");
//        user = (User) (result.getData());
//        System.out.println(result);
//        Assert.assertEquals(0, result.getErrCode());
//        Assert.assertNotNull(user);
//    }

    @Test
    public void cTestSendBug(){
        Result result = bugService.sendBug("test",user.getId());
        Assert.assertEquals(0, result.getErrCode());
    }

    @Test
    public void dTestSendBugUrl() throws Exception {
        String url="/bug/send";
            mockMvc.perform(post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",user.getAccessToken())
                    .param("bug","6666666"))
                    .andExpect(status().isOk()).andExpect(content().json("{\n" +
                            "    \"errCode\": 0,\n" +
                            "    \"msg\": \"ok\"\n" +
                            "}"));
    }
    @After
    public void afterTest() {

    }
}
