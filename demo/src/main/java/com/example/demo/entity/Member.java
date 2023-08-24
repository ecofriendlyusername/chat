package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;

    String mainChannelDestination;
    private String name;

    @Builder
    Member(String email, String name, String mainChannelDestination) {
        this.email = email;
        this.mainChannelDestination = mainChannelDestination;
        this.name = name;
    }
}
