package com.app.dorandoran_backend.quotes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class QuoteLikeId implements Serializable {

    private Long quote;
    private Long member;

    public QuoteLikeId(Long quote, Long member) {
        this.quote = quote;
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuoteLikeId)) return false;
        QuoteLikeId that = (QuoteLikeId) o;
        return Objects.equals(quote, that.quote) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quote, member);
    }

}
