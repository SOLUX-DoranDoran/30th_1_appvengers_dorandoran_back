package com.app.dorandoran_backend.reviews.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
public class ReviewLikeId implements Serializable {

    private Long member;
    private Long review;

    public ReviewLikeId(Long member, Long review) {
        this.member = member;
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewLikeId)) return false;
        ReviewLikeId that = (ReviewLikeId) o;
        return Objects.equals(member, that.member) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, review);
    }
}

