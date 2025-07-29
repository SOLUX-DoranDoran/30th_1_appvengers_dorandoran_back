package com.app.dorandoran_backend.books.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 도서 ID

    @Column(nullable = false, length = 255)
    private String title; // 도서 제목

    @Column(nullable = false, length = 100)
    private String author; // 도서 저자

    @Column(nullable = false, length = 100)
    private String publisher; // 도서 출판사

    @Column(name = "publisher_date", nullable = false)
    private LocalDate publisherDate; // 도서 출판일

    @Column(name = "cover_image_url", length = 255)
    private String coverImageUrl; // 도서 표지 이미지 URL
}
