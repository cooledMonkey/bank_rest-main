package com.example.bankcards.util;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecification {
        public static Specification<Card> hasUserId(Long userId) {
            return (root, query, cb) ->
                    userId == null ? null : cb.equal(root.join("owner").get("id"), userId);
        }
}
