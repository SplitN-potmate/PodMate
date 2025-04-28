package com.podmate.domain.token.domain.entity;

import com.podmate.domain.model.entity.BaseEntity;
import com.podmate.domain.user.domain.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
@Getter
public class Token extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @OneToOne(fetch = FetchType.LAZY) // 유저당 하나의 refreshToken을 가지도록 설정! (개발 단순 but, 디바이스별 토큰 발급 및 저장 불가!)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String refreshToken;

    private LocalDateTime expiredDate;

    @Builder
    public Token(User user, String refreshToken, LocalDateTime expiredDate) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiredDate = expiredDate;
    }

    // static method로 객체를 생성 - 생성 의도 파악 쉬웁
    public static Token toEntity(User user, String refreshToken, LocalDateTime expiredDate){
        return Token.builder()
                .user(user)
                .refreshToken(refreshToken)
                .expiredDate(expiredDate)
                .build();
    }

    public void update(String refreshToken, LocalDateTime expiredDate){
        this.refreshToken = refreshToken;
        this.expiredDate = expiredDate;
    }

}
