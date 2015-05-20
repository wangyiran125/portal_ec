package com.chinalbs.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinalbs.dao.UserDao;
import com.chinalbs.entity.User;
import com.chinalbs.entity.Role;
import com.chinalbs.beans.Principal;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.UserService;

/**
 * Service - 用户
 * 
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "userDaoImpl")
	public void setBaseDao(UserDao userDao) {
		super.setBaseDao(userDao);
	}

	@Transactional(readOnly = true)
	public boolean userNameExists(String userName) {
		return userDao.userNameExists(userName);
	}

	@Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userDao.emailExists(email);
    }
	
	@Transactional(readOnly = true)
    public boolean mobileExists(String mobile) {
        return userDao.mobileExists(mobile);
    }
	
	@Transactional(readOnly = true)
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}
	
  @Transactional(readOnly = true)
  public List<User> findByName (String name)
  {
    return userDao.findByName (name);
  }

  @Override
  public User findByNameAccurate (String name)
  {
    return userDao.findByNameAccurate (name);
  }

  @Transactional(readOnly = true)
	public Page<User> findPage(Pageable pageable, User user) {
		return userDao.findPage(pageable, user);
	}
	
	@Transactional(readOnly = true)
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		User user = userDao.find(id);
		if (user != null) {
			for (Role role : user.getRoles()) {
				authorities.addAll(role.getAuthorities());
			}
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}

	@Transactional(readOnly = true)
	public User getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return userDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void save(User user) {
		super.save(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user) {
		return super.update(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User user, String... ignoreProperties) {
		return super.update(user, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(User user) {
		super.delete(user);
	}

	@Transactional(readOnly = true)
	public boolean isSystemuser() {
		User user = getCurrent();
		Set<Role> roles = user.getRoles();
		Iterator<Role> it = roles.iterator();  
		while (it.hasNext()) {  
			Role role = it.next();			
			if (role.getIsSystem()) {
				return 	true;
			}
		}  
		return false;
	}

}