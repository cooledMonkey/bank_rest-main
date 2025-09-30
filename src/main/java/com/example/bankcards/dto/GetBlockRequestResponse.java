package com.example.bankcards.dto;

import com.example.bankcards.entity.BlockRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class GetBlockRequestResponse {
    Long id;
    Long cardId;
    LocalDateTime requestTime;
    String status;

    public GetBlockRequestResponse(BlockRequest blockRequest){
        this.id = blockRequest.getId();
        this.cardId = blockRequest.getCard().getId();
        this.requestTime = blockRequest.getRequestTime();
        this.status = blockRequest.getStatus();
    }
}
