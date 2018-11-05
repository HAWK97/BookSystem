package com.hawk.book.mapper;

import com.hawk.book.data.entity.Ticket;
import com.hawk.book.utils.SystemUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author wangshuguang
 * @since 2018/02/17
 */
@Mapper
public interface TicketDao {

    String TABLE_NAME = "ticket";

    String ALL_FIELDS = " id, userId, ticket, expired, createTime, updateTime, status";

    String INSERT_FIELDS = " userId, ticket, expired, createTime, updateTime";

    /**
     * 保存 Ticket
     *
     * @param ticket
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId},#{ticket},#{expired},#{createTime},#{updateTime})"})
    void save(Ticket ticket);

    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "ticket=#{ticket}", SystemUtil.STATUS})
    Ticket findByTicket(String ticket);

    /**
     * 查找用户的 Ticket
     *
     * @param userId
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "userId=#{userId}", SystemUtil.STATUS})
    Ticket findByUser(int userId);

    /**
     * 更新 Ticket 状态
     *
     * @param ticket
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set status=#{status} ", SystemUtil.WHERE, "userId=#{userId}"})
    void updateStatus(Ticket ticket);

    /**
     * 更新 Ticket 作废时间
     *
     * @param ticket
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, " set expired=#{expired}, updateTime=#{updateTime}, status=0", SystemUtil.WHERE, "userId=#{userId}"})
    void updateExpired(Ticket ticket);
}
