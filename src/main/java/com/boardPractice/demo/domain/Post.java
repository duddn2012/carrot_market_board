package com.boardPractice.demo.domain;

import com.boardPractice.demo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "POST") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private int postId;

    @Column(name="user_id")
    private int userId;

    @Column(name="buy_user_id")
    private int buyUserId;

    private String postcd;
    private String poststatuscd;
    private String title;
    private String categorycd;
    private int price;
    private String shareyn;
    private String contents;
    private String priceadvyn;
    private String hopeaddr;
}
