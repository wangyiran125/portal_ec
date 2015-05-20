package com.chinalbs.json;

import com.chinalbs.entity.UserStatistics;

public class ApiUserStatJson extends ApiCommonJson{
  
    private UserStatistics user;

    public UserStatistics getUser() {
      return user;
    }

    public void setUser(UserStatistics user) {
      this.user = user;
    }
}
