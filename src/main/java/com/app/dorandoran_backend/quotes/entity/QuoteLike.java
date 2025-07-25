package com.app.dorandoran_backend.quotes.entity;

import com.app.dorandoran_backend.mypage.entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quoteLike",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"quote_id", "member_id"})
        })
@IdClass(QuoteLikeId.class)
@Getter
@Setter
@NoArgsConstructor
public class QuoteLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

}

