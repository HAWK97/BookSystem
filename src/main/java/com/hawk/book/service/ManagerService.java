package com.hawk.book.service;

import com.hawk.book.data.ResultEnum;
import com.hawk.book.data.dto.BookAndCommentDto;
import com.hawk.book.data.dto.BookDto;
import com.hawk.book.data.dto.CommentDto;
import com.hawk.book.data.entity.Book;
import com.hawk.book.exception.MyException;
import com.hawk.book.handle.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * ManagerService
 *
 * @author lirongqian
 * @since 2018/02/17
 */
@Service
@Slf4j
public class ManagerService {

    private static ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DtoService dtoService;

    public void upload(MultipartFile book, String name) {
        int dotPos = book.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            throw new MyException(ResultEnum.BOOK_FORMAT_ERROR);
        }
        String bookExt = book.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!bookExt.equals("pdf")) {
            throw new MyException(ResultEnum.BOOK_FORMAT_ERROR);
        }
        String coverUrl = qiniuService.uploadCover(book);
        Book newBook = new Book();
        if (StringUtils.isBlank(name)) {
            newBook.setBookName(book.getOriginalFilename().substring(0, dotPos));
        } else {
            newBook.setBookName(name);
        }
        newBook.setUserId(UserContextHolder.get().getId());
        newBook.setCoverUrl(coverUrl);
        newBook.setCreateTime(new Date());
        newBook.setUpdateTime(new Date());
        String pdfUrl = qiniuService.uploadBook(book);
        newBook.setPdfUrl(pdfUrl);
        bookService.saveBook(newBook);
    }

    public void delete(int bookId) {
        Book book = bookService.getInfo(bookId);
        if (book.getUserId() != UserContextHolder.get().getId()) {
            throw new MyException(ResultEnum.BOOK_DELETE_ERROR);
        }
        executor.execute(() -> qiniuService.deleteBook(book));
        bookService.delete(book.getId());
        log.info("delete success!");
    }

    public BookAndCommentDto getInfo(int id) {
        BookDto book = dtoService.getBookDto(bookService.getInfo(id));
        List<CommentDto> commentDtoList = commentService.findByBookId(id)
                .stream()
                .map(comment -> dtoService.getCommentDto(comment))
                .collect(Collectors.toList());
        return new BookAndCommentDto(book, commentDtoList);
    }
}
