package com.hawk.book.data;

import lombok.Getter;

/**
 * 评价枚举类，0-1 分代表差评，2-3 分代表中评，4-5 分代表好评
 *
 * @author wangshuguang
 * @since 2018/02/19
 */
public enum CommentEnum {

    GOOD(1, "好评"),

    MEDIUM(2, "中评"),

    NEGATIVE(3, "差评");

    CommentEnum(int id, String type) {
        this.id = id;
        this.type = type;
    }

    @Getter
    private int id;

    @Getter
    private String type;
}
