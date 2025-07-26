package com.app.dorandoran_backend.quotes.entity;

import com.app.dorandoran_backend.mypage.entity.Members;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quoteLike",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "quote_id"})
        })
@IdClass(QuoteLikeId.class)
@Getter
@Setter
@NoArgsConstructor
public class QuoteLike {
	
	public QuoteLike(QuotePost quote, Members member) {
        this.quote = quote;
        this.member = member;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private QuotePost quote;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

}

