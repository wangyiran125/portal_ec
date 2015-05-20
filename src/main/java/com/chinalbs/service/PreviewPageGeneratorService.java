package com.chinalbs.service;

import java.util.Map;

public interface PreviewPageGeneratorService {
  public int build(String templatePath, String staticPath, Map<String, Object> model);
}
