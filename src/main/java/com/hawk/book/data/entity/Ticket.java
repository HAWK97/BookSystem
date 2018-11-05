package com.hawk.book.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Ticket
 *
 * @author wangshuguang
 * @since 2018/02/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseEntity {

    /**
     * 关联的用户 id
     */
    private int userId;

    /**
     * 编码
     */
    private String ticket;

    /**
     * 到期时间
     */
    private Date expired;
}
