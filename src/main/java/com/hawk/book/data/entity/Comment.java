package com.hawk.book.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Comment
 *
 * @author wangshuguang
 * @since 2018/02/19
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment extends BaseEntity {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论分数
     */
    private int score = 0;

    /**
     * 评论人 id，根据 id 去获取用户的昵称
     */
    private int userId;

    /**
     * 评论图书的 id
     */
    private int bookId;
}
