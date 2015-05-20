package com.chinalbs.template.directive;

import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.chinalbs.service.UserService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取用户配置信息指令类
 * 
 * @author shijun
 * 
 */
@Component("userConfDirective")
public class UserConfDirective extends BaseDirective {

  /** 变量名称 */
  private static final String VARIABLE_NAME = "userConf";

  @Resource(name = "userServiceImpl")
  UserService userService;

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars,
      TemplateDirectiveBody body) throws TemplateException, IOException {

    setLocalVariable(VARIABLE_NAME, userService.getCurrent(), env, body);
  }

}
