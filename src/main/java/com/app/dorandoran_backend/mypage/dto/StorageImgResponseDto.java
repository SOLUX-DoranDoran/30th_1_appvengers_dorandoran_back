package com.app.dorandoran_backend.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StorageImgResponseDto {
    private boolean success;
    private String fileName;
    private String profileImageUrl;
}
