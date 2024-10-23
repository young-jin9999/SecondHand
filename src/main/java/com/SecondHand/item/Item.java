package com.SecondHand.item;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 고유ID
    private String title;   // 상품명
    private Integer price;  // 가격
    private String imgURL;  // 이미지

    @Column(name = "`desc`")
    private String desc;    // 설명

    @CreationTimestamp
    private LocalDateTime uploadDate;   // 등록 날짜
}
