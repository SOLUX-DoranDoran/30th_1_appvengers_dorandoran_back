package com.app.dorandoran_backend.mypage.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.dorandoran_backend.mypage.dto.StorageImgResponseDto;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberRepository memberRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Autowired
    private AmazonS3Client amazonS3Client;

    public StorageImgResponseDto uploadProfileImage(Members member, MultipartFile file) throws IOException {
        // 기존 프로필 이미지가 있으면 S3에서 삭제
        if (member.getProfileImage() != null && !member.getProfileImage().isEmpty()) {
            // 기존 URL에서 S3 키(파일명) 추출
            String existingUrl = member.getProfileImage();
            String key = extractKeyFromUrl(existingUrl);
            if (amazonS3Client.doesObjectExist(bucketName, key)) {
                amazonS3Client.deleteObject(bucketName, key);
            }
        }

        String fileName = member.getId() + "-profileImage-" + LocalDateTime.now()
                .toString()
                .replace(" ", "-")
                .replace(":", "-")
                .replace(".", "-") + ".png";

        // S3에 업로드할 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setContentDisposition("inline"); // 브라우저에서 바로 열 수 있도록 설정

        // S3 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        // 이미지 URL 생성
        String imageUrl = amazonS3Client.getUrl(bucketName, fileName).toString();

        // 유저에 이미지 URL 저장
        member.setProfileImage(imageUrl);
        memberRepository.save(member);

        // URL 가져오기
        String imgUrl = amazonS3Client.getUrl(bucketName, fileName).toString();

        return new StorageImgResponseDto(true, fileName, imgUrl);
    }

    // 기존 URL에서 파일명 추출 (ex: https://bucket.s3.../파일명.png → 파일명.png)
    private String extractKeyFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
