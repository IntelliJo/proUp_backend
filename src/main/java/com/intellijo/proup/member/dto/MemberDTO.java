package com.intellijo.proup.member.dto;

import com.intellijo.proup.member.entity.MemberEntity;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter @Setter
public class MemberDTO {
    private Long id;
    private String name;
    private String pw;
    private String adr;
    private String nickname;

    @Builder(builderClassName = "toDTOBuilder", builderMethodName = "toDTOBuilder")
    MemberDTO(MemberEntity memberEntity){
        this.id = memberEntity.getId();
        this.name = memberEntity.getName();
        this.pw = memberEntity.getPw();
        this.adr = memberEntity.getAdr();
        this.nickname = memberEntity.getNickname();
    }

    public static MemberEntity memberEntityConverter(MemberDTO memberDTO){
        return MemberEntity.builder()
                .id(memberDTO.getId())
                .pw(memberDTO.getPw())
                .name(memberDTO.getName())
                .adr(memberDTO.getAdr())
                .nickname(memberDTO.getNickname())
                .build();
    }

    @Builder
    public MemberDTO(Long id, String name, String pw, String adr, String nickname){
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.adr = adr;
        this.nickname = nickname;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberRequestDTO{
        private String adr;    // 회원 주소
        private String nickname;   // 회원 닉네임
    }

    @Getter
    @NoArgsConstructor
    public static class MemberResponseDTO{
        private Long id;
        private String name;   // 회원 이름
        private String adr;    // 회원 주소
        private String nickname;   // 회원 닉네임

        @Builder(builderClassName = "toDTOBuilder", builderMethodName = "toDTOBuilder")
        MemberResponseDTO(MemberEntity memberEntity){
            this.id = memberEntity.getId();
            this.name = memberEntity.getName();
            this.adr = memberEntity.getAdr();
            this.nickname = memberEntity.getNickname();
        }

        @Builder
        public MemberResponseDTO(Long id, String name, String adr, String nickname){
            this.id = id;
            this.name = name;
            this.adr = adr;
            this.nickname = nickname;
        }
    }

}

