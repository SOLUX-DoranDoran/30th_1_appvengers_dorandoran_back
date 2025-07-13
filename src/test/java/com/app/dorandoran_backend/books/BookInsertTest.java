package com.app.dorandoran_backend.books;

import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class BookInsertTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void insertSampleBooks() {
        List<Books> books = List.of(
                createBook("나미야 잡화점의 기적", "히가시노 게이고", "현대문학", "2012-12-19", "https://raw.githubusercontent.com/dhhe1234/solux/main/나미야잡화점의기적_표지.jpg"),
                createBook("미드나잇 라이브러리", "매트 헤이그", "인플루엔셜", "2021-04-28", "https://raw.githubusercontent.com/dhhe1234/solux/main/미드나잇라이브러리_표지.jpg"),
                createBook("파친코", "이민진", "문학사상사", "2018-03-19", "https://raw.githubusercontent.com/dhhe1234/solux/main/파친코_표지.jpg"),
                createBook("데미안", "헤르만 헤세", "민음사", "2000-12-20", "https://raw.githubusercontent.com/dhhe1234/solux/main/데미안_표지.jpg"),
                createBook("달러구트 꿈 백화점", "이미예", "팩토리나인", "2020-07-08", "https://raw.githubusercontent.com/dhhe1234/solux/main/달러구트꿈백화점_표지.jpg"),
                createBook("불편한 편의점", "김호연", "나무옆의자", "2021-04-20", "https://raw.githubusercontent.com/dhhe1234/solux/main/불편한편의점_표지.jpg"),
                createBook("아몬드", "손원평", "창비", "2017-03-31", "https://raw.githubusercontent.com/dhhe1234/solux/main/아몬드_표지.jpg"),
                createBook("쇼코의 미소", "최은영", "문학동네", "2016-07-07", "https://raw.githubusercontent.com/dhhe1234/solux/main/쇼코의미소_표지.jpg"),
                createBook("소년이 온다", "한강", "창비", "2014-05-19", "https://raw.githubusercontent.com/dhhe1234/solux/main/소년이온다_표지.jpg"),
                createBook("아가미", "구병모", "위즈덤하우스", "2018-03-30", "https://raw.githubusercontent.com/dhhe1234/solux/main/아가미_표지.jpg")
        );

        bookRepository.saveAll(books);
        System.out.println("도서 10권이 저장되었습니다.");
    }

    private Books createBook(String title, String author, String publisher, String date, String imageUrl) {
        Books book = new Books();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setPublisherDate(LocalDate.parse(date));
        book.setCoverImageUrl(imageUrl);
        return book;
    }
}
