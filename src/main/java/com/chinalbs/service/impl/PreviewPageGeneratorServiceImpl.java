package com.chinalbs.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.chinalbs.service.PreviewPageGeneratorService;

/**
 * 静态页面生成器
 * 
 * @author shijun
 * 
 */
@Service("previewPageGeneratorServiceImpl")
public class PreviewPageGeneratorServiceImpl implements PreviewPageGeneratorService ,ServletContextAware{


  @Resource(name = "freeMarkerConfigurer")
  private FreeMarkerConfigurer freeMarkerConfigurer;

  private ServletContext servletContext;

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  /**
   * 生成预览静态页面
   */
  public int build(String templatePath, String staticPath, Map<String, Object> model) {
    Assert.hasText(templatePath);
    Assert.hasText(staticPath);

    FileOutputStream fileOutputStream = null;
    OutputStreamWriter outputStreamWriter = null;
    Writer writer = null;
    try {
      freemarker.template.Template template =
          freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
      File staticFile = new File(servletContext.getRealPath(staticPath));
      File staticDirectory = staticFile.getParentFile();
      if (!staticDirectory.exists()) {
        staticDirectory.mkdirs();
      }
      fileOutputStream = new FileOutputStream(staticFile);
      outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
      writer = new BufferedWriter(outputStreamWriter);
      template.process(model, writer);
      writer.flush();
      return 1;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(writer);
      IOUtils.closeQuietly(outputStreamWriter);
      IOUtils.closeQuietly(fileOutputStream);
    }
    return 0;
  }
}
