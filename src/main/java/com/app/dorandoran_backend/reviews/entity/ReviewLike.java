package com.app.dorandoran_backend.reviews.entity;

import com.app.dorandoran_backend.mypage.entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviewLike",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "review_id"})
        })
@IdClass(ReviewLikeId.class)
@Getter
@Setter
@NoArgsConstructor
public class ReviewLike {

    public ReviewLike(ReviewPost review, Members member) {
        this.review = review;
        this.member = member;
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // FK to member.id
    private Members member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id") // FK to ReviewPost.id
    private ReviewPost review;
}