package com.hawk.book.data.dto;

import lombok.Data;

/**
 * CommentDto
 *
 * @author wangshuguang
 * @since 2018/02/19
 */
@Data
public class CommentDto {

    /**
     * 评论 id
     */
    private int id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论分数
     */
    private int score = 0;

    /**
     * 评论人昵称
     */
    private String nickname;

    /**
     * 评论类别，分为好评、中评、差评
     */
    private String type;

    /**
     * 评论时间
     */
    private String createTime;
}
