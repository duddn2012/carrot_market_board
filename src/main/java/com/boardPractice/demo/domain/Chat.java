package com.boardPractice.demo.domain;

import com.boardPractice.demo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "CHAT") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_id")
    private int chatId;

    @Column(name="user_id")
    private int userId;

    @Column(name="partner_id")
    private int partnerId;

    private String content;
    private String send;
    private String title;
    private String categorycd;
    private int price;
    private String shareyn;
    private String contents;
    private String priceadvyn;
    private String hopeaddr;

}
