package com.hawk.book.mapper;

import com.hawk.book.data.entity.User;
import com.hawk.book.utils.SystemUtil;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangshuguang
 * @since 2018/02/17
 */
@Mapper
public interface UserDao {

    String TABLE_NAME = "user";

    String ALL_FIELDS = " id, username, nickname, password, salt, mail, createTime, updateTime, status";

    String INSERT_FIELDS = " username, nickname, password, salt, mail, createTime, updateTime";

    /**
     * 搜索所有用户
     *
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "status >= 0"})
    List<User> findAll();

    /**
     * 插入一个用户
     *
     * @param user
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{username},#{nickname},#{password},#{salt},#{mail},#{createTime},#{updateTime})"})
    void save(User user);

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "username=#{username}", SystemUtil.STATUS})
    User findByName(String username);

    /**
     * 通过用户 id 查找用户
     * @param id
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "id=#{id}", SystemUtil.STATUS})
    User findById(int id);

    /**
     * 修改密码
     *
     * @param user
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set password=#{password}, updateTime=#{updateTime}", SystemUtil.WHERE, "username=#{username}", SystemUtil.STATUS})
    void update(User user);
}
