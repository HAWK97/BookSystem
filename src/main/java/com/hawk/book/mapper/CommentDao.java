package com.hawk.book.mapper;

import com.hawk.book.data.entity.Comment;
import com.hawk.book.utils.SystemUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangshuguang
 * @since 2018/02/19
 */
@Mapper
public interface CommentDao {

    String TABLE_NAME = "comment";

    String ALL_FIELDS = " id, content, score, userId, bookId, createTime, updateTime, status";

    String INSERT_FIELDS = " content, score, userId, bookId, createTime, updateTime";

    /**
     * 保存一条评论
     *
     * @param comment
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content},#{score},#{userId},#{bookId},#{createTime},#{updateTime})"})
    void save(Comment comment);

    /**
     * 通过用户 id 查找一条评论
     *
     * @param userId
     * @param bookId
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "userId=#{userId} and bookId=#{bookId}", SystemUtil.STATUS, "limit 1"})
    Comment findByUserIdAndBookId(@Param("userId") int userId, @Param("bookId") int bookId);

    /**
     * 通过图书 id 查找评论
     *
     * @param bookId
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "bookId=#{bookId}", SystemUtil.STATUS, "order by score desc"})
    List<Comment> findByBookId(int bookId);
}
