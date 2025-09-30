package com.example.bankcards.util;

import com.example.bankcards.entity.BlockRequest;
import org.springframework.data.jpa.domain.Specification;

public class BlockRequestSpecification {
    public static Specification<BlockRequest> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
