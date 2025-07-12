package com.app.dorandoran_backend.debate;

import com.app.dorandoran_backend.debate.Entity.DebateRoom;
import com.app.dorandoran_backend.debate.Entity.DebateMessage;
import com.app.dorandoran_backend.debate.Entity.DebateComments;
import com.app.dorandoran_backend.debate.repository.DebateRoomRepository;
import com.app.dorandoran_backend.debate.repository.DebateMessageRepository;
import com.app.dorandoran_backend.debate.repository.DebateCommentsRepository;
import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.dorandoran_backend.mypage.Entity.Provider.GOOGLE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@ActiveProfiles("test")
public class DebateTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private DebateRoomRepository debateRoomRepository;
    @Autowired private DebateMessageRepository debateMessageRepository;
    @Autowired private DebateCommentsRepository debateCommentsRepository;

    @Test
    public void testCreateDebateRoomAndMessageAndComment() {
        System.out.println("=== testCreateDebateRoomAndMessageAndComment 시작 ===");

        Members member = new Members();
        member.setNickname("테스트유저");
        member.setProvider(GOOGLE);
        member.setProviderId("debate_user");
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);
        System.out.println("회원 저장: " + member.getId());

        Books book = new Books();
        book.setTitle("테스트 책");
        book.setAuthor("저자");
        book.setPublisher("출판사");
        book.setPublisherDate(LocalDate.now());
        bookRepository.save(book);
        System.out.println("도서 저장: " + book.getId());

        DebateRoom room = new DebateRoom();
        room.setBook(book);
        room.setMember(member);
        room.setTitle("테스트 토론방");
        room.setContent("의견 내용입니다.");
        room.setCreatedAt(LocalDateTime.now());
        debateRoomRepository.save(room);
        System.out.println("토론방 저장: " + room.getId());

        DebateMessage message = new DebateMessage();
        message.setRoom(room);
        message.setMember(member);
        message.setContent("메시지 내용");
        message.setCreatedAt(LocalDateTime.now());
        debateMessageRepository.save(message);
        System.out.println("메시지 저장: " + message.getId());

        DebateComments comment = new DebateComments();
        comment.setDebateMessage(message);
        comment.setMember(member);
        comment.setContent("댓글 내용입니다.");
        comment.setCreatedAt(LocalDateTime.now());
        debateCommentsRepository.save(comment);
        System.out.println("댓글 저장: " + comment.getId());

        assertNotNull(room.getId());
        assertNotNull(message.getId());
        assertNotNull(comment.getId());

        System.out.println("토론방 제목: " + debateRoomRepository.findById(room.getId()).get().getTitle());
        System.out.println("메시지 내용: " + debateMessageRepository.findById(message.getId()).get().getContent());
        System.out.println("댓글 내용: " + debateCommentsRepository.findById(comment.getId()).get().getContent());

        System.out.println("=== testCreateDebateRoomAndMessageAndComment 종료 ===");
    }

    @Test
    public void testFindMessagesByRoom() {
        System.out.println("=== testFindMessagesByRoom 시작 ===");

        Members user = new Members();
        user.setNickname("사용자1");
        user.setProvider(GOOGLE);
        user.setProviderId("multi_msg");
        user.setCreatedAt(LocalDateTime.now());
        memberRepository.save(user);
        System.out.println("회원 저장: " + user.getId());

        Books book = new Books();
        book.setTitle("다중 메시지 책");
        book.setAuthor("저자");
        book.setPublisher("출판사");
        book.setPublisherDate(LocalDate.now());
        bookRepository.save(book);
        System.out.println("도서 저장: " + book.getId());

        DebateRoom room = new DebateRoom();
        room.setBook(book);
        room.setMember(user);
        room.setTitle("토론방1");
        room.setContent("내용");
        room.setCreatedAt(LocalDateTime.now());
        debateRoomRepository.save(room);
        System.out.println("토론방 저장: " + room.getId());

        for (int i = 1; i <= 3; i++) {
            DebateMessage msg = new DebateMessage();
            msg.setRoom(room);
            msg.setMember(user);
            msg.setContent("메시지 " + i);
            msg.setCreatedAt(LocalDateTime.now());
            debateMessageRepository.save(msg);
            System.out.println("메시지 " + i + " 저장: " + msg.getId());
        }

        List<DebateMessage> messages = debateMessageRepository.findByRoomId(room.getId());
        System.out.println("조회된 메시지 수: " + messages.size());
        assertEquals(3, messages.size());

        System.out.println("=== testFindMessagesByRoom 종료 ===");
    }

    @AfterEach
    public void tearDown() {
        debateCommentsRepository.deleteAll();
        debateMessageRepository.deleteAll();
        debateRoomRepository.deleteAll();
        memberRepository.deleteAll();
        bookRepository.deleteAll();
    }

}
