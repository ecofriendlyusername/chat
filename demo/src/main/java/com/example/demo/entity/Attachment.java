package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment extends File {
    private String sender;
    @Builder
    Attachment(String fileName, String sender) {
        super(fileName);
        this.sender = sender;
    }
}
