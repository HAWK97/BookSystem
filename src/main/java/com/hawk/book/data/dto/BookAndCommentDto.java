package com.hawk.book.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BookAndCommentDto
 *
 * @author wangshuguang
 * @since 2018/02/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAndCommentDto {

    private BookDto book;

    private List<CommentDto> comments;
}
