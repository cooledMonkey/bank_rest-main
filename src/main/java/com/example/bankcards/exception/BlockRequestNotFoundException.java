package com.example.bankcards.exception;

public class BlockRequestNotFoundException extends RuntimeException {
    public BlockRequestNotFoundException() {
        super("Block request not found exception");
    }
}
