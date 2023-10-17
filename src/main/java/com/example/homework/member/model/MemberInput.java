package com.example.homework.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MemberInput {
    private String email;
    private String name;
    private String phone;
}
