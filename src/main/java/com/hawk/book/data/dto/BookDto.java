package com.hawk.book.data.dto;

import lombok.Data;

/**
 * BookDto
 *
 * @author wangshuguang
 * @since 2018/02/19
 */
@Data
public class BookDto {

    /**
     * 图书 id
     */
    private int id;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 上传该书的用户昵称
     */
    private String nickname;

    /**
     * 封面链接
     */
    private String coverUrl;

    /**
     * 文件链接
     */
    private String pdfUrl;

    /**
     * 下载数量
     */
    private int downloadCount;

    /**
     * 平均分，初始为 0，最高为 5
     */
    private double score = 0.0;

    /**
     * 图书上传时间
     */
    private String createTime;
}
