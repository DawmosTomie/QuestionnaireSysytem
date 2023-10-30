import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.controller.ProjectController;
import com.aim.questionnaire.controller.UserController;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireApplicationTests {

    @Resource
    private UserController userController;
    //Logger log = Logger.getLogger(QuestionnaireApplicationTests.class);


    @Test
    public void testSelect() {
        UserEntity userEntity = new UserEntity();
        //HttpResponseEntity httpResponseEntity=new HttpResponseEntity();
        //报错
        userEntity.setUsername("testCatch");
        HttpResponseEntity httpResponseEntity = userController.queryUserList(null);
        Assert.assertNull(httpResponseEntity.getCode());
        //log.info("---报错---");


        //存在
        userEntity.setUsername("admin");
        httpResponseEntity = userController.queryUserList(null);
        Assert.assertEquals("666", httpResponseEntity.getCode());
        //log.info("---存在---");
        //log.info(httpResponseEntity.getData().toString());

        //不存在
        userEntity.setUsername("no");
        httpResponseEntity=userController.queryUserList(null);
        Assert.assertEquals("0", httpResponseEntity.getCode());
        // log.info("---不存在---");

    }


    @Test
    public void testUserLogin() {

        //登录成功
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("123");
        HttpResponseEntity httpResponseEntity =userController.userLogin(userEntity);
        Assert.assertEquals("666", httpResponseEntity.getCode());

        //登录失败
        userEntity.setUsername("no");
        userEntity.setPassword("123456");
        httpResponseEntity =userController.userLogin(userEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        userEntity.setUsername("testCatch");
        httpResponseEntity =userController.userLogin(userEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testAddUserInfo() {

        //添加成功
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testAdd");
        userEntity.setPassword("123123");
        HttpResponseEntity httpResponseEntity =userController.addUserInfo(userEntity);
        Assert.assertEquals("666", httpResponseEntity.getCode());

        //添加失败
        userEntity.setUsername("admin");
        httpResponseEntity =userController.addUserInfo(userEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        /***UserEntity userEntity1=new UserEntity();
         userEntity1.setUsername(null);
         httpResponseEntity =userController.addUserInfo(userEntity1);
         Assert.assertEquals(null, httpResponseEntity.getCode());
         ***/
        //报错
        userEntity.setUsername("testCatch");
        httpResponseEntity =userController.addUserInfo(userEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testModifyUserInfo() {

        //成功
        UserEntity userEntity = new UserEntity();
        userEntity.setId("2");
        userEntity.setUsername("modify");
        userEntity.setPassword("123123");
        HttpResponseEntity httpResponseEntity =userController.modifyUserInfo(userEntity);
        Assert.assertEquals("10", httpResponseEntity.getCode());

        //失败
        userEntity.setId("6");
        userEntity.setUsername("failModify");
        userEntity.setPassword("123123");
        httpResponseEntity =userController.modifyUserInfo(userEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        userEntity.setUsername("testCatch");
        httpResponseEntity =userController.modifyUserInfo(userEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testDeleteUserByName() {

        //成功
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testDelete");
        userController.addUserInfo(userEntity);
        HttpResponseEntity httpResponseEntity =userController.deteleUserInfoById(userEntity);
        Assert.assertEquals("10", httpResponseEntity.getCode());

        //失败
        userEntity.setUsername("failDelete");
        httpResponseEntity =userController.deteleUserInfoById(userEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        userEntity.setUsername("testCatch");
        httpResponseEntity =userController.deteleUserInfoById(userEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Resource
    private ProjectController projectController;

    @Test
    public void testQueryProjectList(){
        ProjectEntity projectEntity = new ProjectEntity();
        //失败
        projectEntity.setProjectName("testno");
        HttpResponseEntity httpResponseEntity =projectController.queryProjectList(projectEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //成功
        projectEntity.setProjectName("test");
        httpResponseEntity =projectController.queryProjectList(projectEntity);
        Assert.assertEquals("666", httpResponseEntity.getCode());

        //报错
        projectEntity.setProjectName("testCatch");
        httpResponseEntity =projectController.queryProjectList(projectEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testAddProjectInfo(){

        //添加成功
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("2");
        projectEntity.setProjectName("add");
        projectEntity.setProjectContent("add");
        HttpResponseEntity httpResponseEntity =projectController.addProjectInfo(projectEntity);
        Assert.assertEquals("666", httpResponseEntity.getCode());

        //添加失败
        projectEntity.setProjectName("testAdd");
        httpResponseEntity =projectController.addProjectInfo(projectEntity);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        projectEntity.setProjectName("testCatch");
        httpResponseEntity =projectController.addProjectInfo(projectEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testModifyProjectInfo(){

        //修改成功
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId("2");
        projectEntity.setProjectName("testModify");
        projectEntity.setProjectContent("testModify");
        HttpResponseEntity httpResponseEntity =projectController.modifyProjectInfo(projectEntity);
        Assert.assertEquals("666", httpResponseEntity.getCode());

        //修改失败
        ProjectEntity projectEntity1 = new ProjectEntity();
        projectEntity1.setId("1000");
        projectEntity1.setProjectName("testModify");
        projectEntity1.setProjectContent("testModify");
        httpResponseEntity =projectController.modifyProjectInfo(projectEntity1);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        projectEntity.setProjectName("testCatch");
        httpResponseEntity =projectController.modifyProjectInfo(projectEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }

    @Test
    public void testDeleteProjectById(){

        //成功
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName("testDelete");
        HttpResponseEntity httpResponseEntity =projectController.deleteProjectById(projectEntity);
        Assert.assertEquals("10", httpResponseEntity.getCode());

        //失败
        ProjectEntity projectEntity1 = new ProjectEntity();
        projectEntity1.setProjectName("failDelete");
        projectEntity1.setId("100");
        httpResponseEntity =projectController.deleteProjectById(projectEntity1);
        Assert.assertEquals("0", httpResponseEntity.getCode());

        //报错
        projectEntity.setProjectName("testCatch");
        httpResponseEntity =projectController.deleteProjectById(projectEntity);
        Assert.assertNull(httpResponseEntity.getCode());
    }



}
