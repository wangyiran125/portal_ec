package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.User;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;



/**
 * Dao - 用户
 * 
 */
public interface UserDao extends BaseDao<User, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean userNameExists(String userName);

	/**
     * 判断E-mail是否存在
     * 
     * @param email
     *            E-mail(忽略大小写)
     * @return E-mail是否存在
     */
    boolean emailExists(String email);
    
    /**
     * 判断手机号码是否存在
     * 
     * @param mobile
     *           
     * @return mobile是否存在
     */
    boolean mobileExists(String mobile);
    
	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户，若不存在则返回null
	 */
	User findByUserName(String userName);
	
	/**
	 * 根据名字查询
	 * @param name
	 * @return
	 */
	List<User> findByName(String name);
	
	/**
	 * 通过名字精确查找
	 * @param name
	 * @return
	 */
	User findByNameAccurate (String name);
	
	/**
	 * 查找用户实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<User> findPage(Pageable pageable, User admin);

}