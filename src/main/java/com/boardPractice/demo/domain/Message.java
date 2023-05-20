package com.boardPractice.demo.domain;

import com.boardPractice.demo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "MESSAGE") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private int messageId;

    @Column(name="chat_id")
    private int chatId;

    @Column(name="is_from_sender")
    private int is_from_sender;

    private String content;

    @Column(name="read_chk")
    private Boolean readChk;
}
