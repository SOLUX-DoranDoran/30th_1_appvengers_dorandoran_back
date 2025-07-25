package com.app.dorandoran_backend.auth.oauth;

import com.app.dorandoran_backend.mypage.entity.Provider;

public interface OAuth2UserInfo {
    String getProviderId();
    Provider getProvider();
    String getName();
    String getEmail();
}

