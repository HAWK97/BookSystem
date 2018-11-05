package com.hawk.book.mapper;

import com.hawk.book.data.entity.Book;
import com.hawk.book.utils.SystemUtil;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wangshuguang
 * @since 2018/02/19
 */
@Mapper
public interface BookDao {

    String TABLE_NAME = "book";

    String ALL_FIELDS = " id, bookName, userId, coverUrl, pdfUrl, downloadCount, createTime, updateTime, status";

    String INSERT_FIELDS = " bookName, userId, coverUrl, pdfUrl, createTime, updateTime";

    /**
     * 获得所有图书，按照上传时间降序排序，并进行分页
     *
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "status >= 0",
            " ORDER BY createTime DESC", " LIMIT #{offset}, #{rowCount}"})
    List<Book> findAll(@Param("offset") int offset, @Param("rowCount") int rowCount);

    @Select({SystemUtil.SELECT, "count(*)", SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "status >= 0",
            " ORDER BY createTime DESC"})
    Integer findAllTotal();

    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "bookName like #{key}",
            SystemUtil.STATUS, " ORDER BY createTime DESC", " LIMIT #{offset}, #{rowCount}"})
    List<Book> findByKey(@Param("offset") int offset, @Param("rowCount") int rowCount, @Param("key") String key);

    @Select({SystemUtil.SELECT, "count(*)", SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "bookName like #{key}",
            SystemUtil.STATUS, " ORDER BY createTime DESC"})
    Integer findByKeyTotal(@Param("key") String key);

    /**
     * 插入一本图书
     *
     * @param book
     */
    @Insert({SystemUtil.INSERT, TABLE_NAME, "(", INSERT_FIELDS, ") VALUES(#{bookName},#{userId},#{coverUrl},#{pdfUrl},",
            "#{createTime},#{updateTime})"})
    void save(Book book);

    /**
     * 根据用户 id 获得其上传的所有图书，按照上传时间降序排序
     *
     * @param userId
     * @return 图书
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "userId=#{userId}",
            SystemUtil.STATUS, " ORDER BY createTime DESC"})
    List<Book> findByUserId(int userId);

    /**
     * 根据 id 获取一本图书的信息
     *
     * @param id
     * @return
     */
    @Select({SystemUtil.SELECT, ALL_FIELDS, SystemUtil.FROM, TABLE_NAME, SystemUtil.WHERE, "id=#{id}", SystemUtil.STATUS})
    Book getInfo(int id);

    /**
     * 下载图书时修改下载数量
     *
     * @param id
     * @param count
     */
    @Update({SystemUtil.UPDATE, TABLE_NAME, "set downloadCount=#{count}", SystemUtil.WHERE, "id=#{id}", SystemUtil.STATUS})
    void downloadBook(@Param("id") int id, @Param("count") int count);

    /**
     * 删除一本图书
     *
     * @param id
     */
    @Update({SystemUtil.DELETE, TABLE_NAME, SystemUtil.WHERE, "id=#{id}"})
    void delete(@Param("id") int id);
}
