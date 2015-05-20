package com.chinalbs.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinalbs.beans.Message;
import com.chinalbs.beans.Setting;
import com.chinalbs.common.log.LogUtil;
import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.entity.CustomizeFun;
import com.chinalbs.entity.Role;
import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.entity.User.UserType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.service.BackendModeService;
import com.chinalbs.service.CustomizeFunService;
import com.chinalbs.service.EnterpriseUserExtendService;
import com.chinalbs.service.FileService;
import com.chinalbs.service.IndividualUserExtendService;
import com.chinalbs.service.PreviewPageGeneratorService;
import com.chinalbs.service.RoleService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.FreemarkerUtils;
import com.chinalbs.utils.JsonUtils;
import com.chinalbs.utils.SettingUtils;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/console/backendmode")
public class BackendModeController extends BaseController {

  @Resource(name = "fileServiceImpl")
  private FileService fileService;

  @Resource(name = "customizeFunServiceImpl")
  private CustomizeFunService customizeFunService;

  @Resource(name = "backendModeServiceImpl")
  private BackendModeService backendModeService;

  @Resource(name = "userServiceImpl")
  private UserService userService;

  @Resource(name = "previewPageGeneratorServiceImpl")
  private PreviewPageGeneratorService previewPageGeneratorService;

  @Resource(name = "roleServiceImpl")
  private RoleService roleService;

  @Resource(name = "enterpriseUserExtendServiceImpl")
  private EnterpriseUserExtendService enterpriseUserExtendService;

  @Resource(name = "individualUserExtendServiceImpl")
  private IndividualUserExtendService individualUserExtendService;

  /**
   * 模板路径
   */
  private final String previewPath = "/preview/pages/${date?string('yyyyMM')}/${id}.html";
  private final String templatePath = "console/preview/preview.ftl";

  @RequestMapping(value = "/infoAdd", method = RequestMethod.GET)
  public String infoAdd(ModelMap model) {
    List<CustomizeFun> customizeFunList = new ArrayList<CustomizeFun>();
    User user = userService.getCurrent();
    if (user.getBackendModeUserExtend() == null && user.getDevModeUserExtend()==null) {
      model.put("display", "display");
    }
    customizeFunList = customizeFunService.findAll();
    model.addAttribute("customizeFunList", customizeFunList);
    model.addAttribute("user", user);
    model.addAttribute("backendModeUserExtend", user.getBackendModeUserExtend());
    return "/console/backendmode/info_add";
  }

  @RequestMapping(value = "/change", method = RequestMethod.GET)
  public String changeMode(ModelMap model) {
    List<CustomizeFun> customizeFunList = new ArrayList<CustomizeFun>();
    User user = userService.getCurrent();
    user.setModeType(ModeType.BACKENDMODE);
    userService.update(user);
    BackendModeUserExtend backendModeUserExtend = null;
    if (user.getBackendModeUserExtend() == null) {
      backendModeUserExtend = new BackendModeUserExtend();
      backendModeUserExtend.setSiteDomain(user.getDevModeUserExtend().getDevDomain());
    } else {
      backendModeUserExtend = user.getBackendModeUserExtend();
    }
    customizeFunList = customizeFunService.findAll();
    model.addAttribute("customizeFunList", customizeFunList);
    model.addAttribute("user", user);
    model.addAttribute("backendModeUserExtend", backendModeUserExtend);
    return "/console/backendmode/info_add";
  }

  /**
   * 保存后台模式数据
   * 
   * @param model
   * @param backendModeUserExtend
   * @param customizeFunsId
   * @return
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(ModelMap model, BackendModeUserExtend backendModeUserExtend,
      Long[] customizeFunsId) {
    try {


      backendModeUserExtend.getUserCustomizeFuns();
      List<CustomizeFun> list = customizeFunService.findList(customizeFunsId);
      Set<CustomizeFun> customizeFuns = new HashSet<CustomizeFun>(list);
      User user = userService.getCurrent();
      backendModeUserExtend.setUserCustomizeFuns(customizeFuns);
      backendModeUserExtend.setUser(user);
      Setting setting = SettingUtils.get();

      if (user.getUserType().equals(UserType.ENTERPRISE)) {
        if (backendModeUserExtend.getDefaultSiteDomain() == null
            || (backendModeUserExtend.getDefaultSiteDomain() != null && backendModeUserExtend
                .getDefaultSiteDomain().indexOf(setting.getDefaultSiteDomainForEnterprise()) != -1)) {
          String defaultSiteDomain =
              setting.getDefaultSiteDomainForEnterprise() + "?domainCode=" + UUID.randomUUID();
          backendModeUserExtend.setDefaultSiteDomain(defaultSiteDomain);
        }
      }
      if (user.getUserType().equals(UserType.INDIVIDUAL)) {
        if (backendModeUserExtend.getDefaultSiteDomain() == null
            || (backendModeUserExtend.getDefaultSiteDomain() != null && backendModeUserExtend
                .getDefaultSiteDomain().indexOf(setting.getDefaultSiteDomainForIndividual()) != -1)) {
          String defaultSiteDomain =
              setting.getDefaultSiteDomainForIndividual() + "?domainCode=" + UUID.randomUUID();
          backendModeUserExtend.setDefaultSiteDomain(defaultSiteDomain);
        }
      }



      Set<Role> roles = new HashSet<Role>();
      Role role = null;
      if (ModeType.DEVMODE.equals(user.getModeType())) {
        role = roleService.find(Long.parseLong(SettingUtils.get().getDevelopmentModeRoleId()));
      } else {
        role = roleService.find(Long.parseLong(SettingUtils.get().getBackendModeRoleId()));
      }
      if (null != role) {
        roles.add(role);
      }
      if (LogUtil.isDebugEnabled(SettingController.class)) {
        LogUtil.debug(BaseDaoImpl.class, "selectModeType", "Setting user role with role id : %d",
            role.getId());
      }
      user.setRoles(roles);
      user.setConfIsEnable(1);
      userService.update(user);
      if (user != null && user.getBackendModeUserExtend() != null) {
        BackendModeUserExtend dbBackendModeUserExtend = user.getBackendModeUserExtend();
        backendModeUserExtend.setId(dbBackendModeUserExtend.getId());
        backendModeService.update(backendModeUserExtend);
      } else {
        backendModeService.save(backendModeUserExtend);
      }
      model.addAttribute("backendModeUserExtend", backendModeUserExtend);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/console/common/main.jhtml";
  }

  /**
   * 上传logo图片
   * 
   * @param files
   * @return
   */
  @RequestMapping(value = "/logo/file", method = RequestMethod.POST,
      consumes = "multipart/form-data")
  @ResponseBody
  public Message createLogo(@RequestParam(value = "logoFile", required = true) MultipartFile[] files) {
    String filePath = null;
    Message message = new Message();
    try {
      if (files.length > 0) {
        filePath = this.fileService.saveImage(files);
        if (LogUtil.isDebugEnabled(BackendModeController.class)) {
          LogUtil.debug(BackendModeController.class, "createLogo", "Logo file path: %s", filePath);
        }
        message.setContent(filePath);
        message.setType(Message.Type.success);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return message;
  }

  /**
   * 上传favicon图片
   * 
   * @param files
   * @return
   */
  @RequestMapping(value = "/favicon/file", method = RequestMethod.POST,
      consumes = "multipart/form-data")
  @ResponseBody
  public Message createFavIcon(
      @RequestParam(value = "favIconFile", required = true) MultipartFile[] files) {
    String filePath = null;
    Message message = new Message();
    try {
      if (files.length > 0) {
        filePath = this.fileService.saveImage(files);
        if (LogUtil.isDebugEnabled(BackendModeController.class)) {
          LogUtil.debug(BackendModeController.class, "create", "favIcon file path: %s", filePath);
        }
        message.setContent(filePath);
        message.setType(Message.Type.success);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return message;
  }

  /**
   * 上传icon图片
   * 
   * @param files
   * @return
   */
  @RequestMapping(value = "/icon/file", method = RequestMethod.POST,
      consumes = "multipart/form-data")
  @ResponseBody
  public Message createIcon(@RequestParam(value = "iconFile", required = true) MultipartFile[] files) {
    String filePath = null;
    Message message = new Message();
    try {
      if (files.length > 0) {
        filePath = this.fileService.saveImage(files);
        if (LogUtil.isDebugEnabled(BackendModeController.class)) {
          LogUtil.debug(BackendModeController.class, "createIcon", "Icon file path: %s", filePath);
        }
        message.setContent(filePath);
        message.setType(Message.Type.success);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return message;
  }

  /**
   * 预览
   * 
   * @param backendModeUserExtend
   * @param customizeFunsId
   * @return
   */
  @RequestMapping(value = "/preview", method = RequestMethod.POST)
  @ResponseBody
  public String preview(BackendModeUserExtend backendModeUserExtend, Long[] customizeFunsId) {
    User user = userService.getCurrent();
    user.setBackendModeUserExtend(backendModeUserExtend);
    List<CustomizeFun> list = customizeFunService.findList(customizeFunsId);
    Set<CustomizeFun> customizeFuns = new HashSet<CustomizeFun>(list);
    backendModeUserExtend.setUserCustomizeFuns(customizeFuns);
    user.setBackendModeUserExtend(backendModeUserExtend);
    Map<String, Object> modelMap = new HashMap<String, Object>();
    if (user.getUserType().equals(UserType.ENTERPRISE)) {
        modelMap.put("company_name", user.getEnterpriseUserExtend().getCompanyName());
    }
    if (user.getUserType().equals(UserType.INDIVIDUAL)) {
        modelMap.put("company_name", user.getIndividualUserExtend().getName());
    }
    modelMap.put("date", new Date());
    modelMap.put("id", user.getId());
    modelMap.put("userConf", user);
    String staticPath = null;
    try {
      staticPath = FreemarkerUtils.process(previewPath, modelMap);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
    previewPageGeneratorService.build(templatePath, staticPath, modelMap);

    return staticPath;
  }

  /**
   * 获取所有配置信息
   * 
   * @param request
   * @param response
   * @param model
   * @param companyDomain
   */
  @RequestMapping("/retrieveConfigInfo")
  @ResponseBody
  public void retrieveConfigInfo(HttpServletRequest request, HttpServletResponse response,
      ModelMap model, String companyDomain) {

    try {
      String domain = URLDecoder.decode(companyDomain, "utf-8");
      String callbackFunName = request.getParameter("callback");
      BackendModeUserExtend backendModeUserExtend =
          backendModeService.getRootModeUserExtendByDomain(domain);
      Map<String, Object> result = new HashMap<String, Object>();
      if (backendModeUserExtend != null) {
        User user = backendModeUserExtend.getUser();
        Setting setting = SettingUtils.get();
        String url = setting.getApiUrl() + setting.getApiGetUserId();

        if (user.getUserType().equals(UserType.ENTERPRISE)) {
          result.put("company_name", user.getEnterpriseUserExtend().getCompanyName());
        }
        if (user.getUserType().equals(UserType.INDIVIDUAL)) {
          result.put("company_name", user.getIndividualUserExtend().getName());
        }

        result.put("root_user_name", user.getUserName());
        result.put("url_apiGetUserId", url);
        result.put("root_app_name", user.getAppName());
        result.put("isEnable", user.getConfIsEnable());
        result.put("company_logo", "/upload/" + backendModeUserExtend.getLogo());
        result.put("company_theme", backendModeUserExtend.getFrontStyle());
        result.put("copyright", backendModeUserExtend.getCustCopyRight());
        
        result.put("company_siteName", backendModeUserExtend.getSiteName());
        result.put("company_favIcon", "/upload/" + backendModeUserExtend.getFavIcon());
        result.put("company_icon", "/upload/" + backendModeUserExtend.getIcon());
        
        List<Map<String, Object>> function_list = new ArrayList<Map<String, Object>>();
        result.put("function_list", function_list);
        for (CustomizeFun customizeFun : backendModeUserExtend.getUserCustomizeFuns()) {
          Map<String, Object> function = new HashMap<String, Object>();
          function.put("function_name", customizeFun.getFuncationName());
          function.put("value", true);
          function_list.add(function);
        }
      }


      response.getWriter().write(callbackFunName + "(" + JsonUtils.obj2Str(result) + ")");
    } catch (Exception e) {
      e.printStackTrace();
    }
    response.setContentType("text/javascript");

  }

  /**
   * 验证网站域名是否唯一
   * 
   * @param siteDomain
   * @return
   */
  @RequestMapping("/check_sitedomain")
  public @ResponseBody
  Boolean checkSiteDomain(String siteDomain) {
    User user = userService.getCurrent();
    if (user != null && user.getBackendModeUserExtend() != null
        && user.getBackendModeUserExtend().getSiteDomain() != null
        && user.getBackendModeUserExtend().getSiteDomain().equalsIgnoreCase(siteDomain)) {
      return true;
    }
    if (backendModeService.siteDomainExist(siteDomain)) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 验证网站名称是否唯一
   * 
   * @param siteName
   * @return
   */
  @RequestMapping("/check_sitename")
  public @ResponseBody
  Boolean checkSiteName(String siteName) {
    User user = userService.getCurrent();
    if (user != null && user.getBackendModeUserExtend() != null
        && user.getBackendModeUserExtend().getSiteName().equalsIgnoreCase(siteName)) {
      return true;
    }
    if (StringUtils.isEmpty(siteName)) {
      return false;
    }
    if (backendModeService.siteNameExist(siteName)) {
      return false;

    } else {
      return true;
    }

  }

}
