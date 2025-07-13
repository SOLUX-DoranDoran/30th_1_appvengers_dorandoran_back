package com.app.dorandoran_backend.mypage.service;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
//@RequiredArgsConstructor
public class MemberProfileService {

    //private final MemberRepository memberRepository;
    //private final CloudinaryService cloudinaryService;

    //public String uploadProfileImage(Members member, MultipartFile imageFile) throws IOException {
        // Cloudinary에 이미지 업로드
        //String imageUrl = cloudinaryService.uploadImage(imageFile);

        // DB에 이미지 URL 저장
       // member.setProfileImage(imageUrl);
       // memberRepository.save(member);

        //return imageUrl;
    //}
}
