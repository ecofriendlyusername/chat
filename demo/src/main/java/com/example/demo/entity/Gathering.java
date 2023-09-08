package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Gathering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gatheringName;
    @OneToOne
    private File gatheringImage;

    @Builder
    Gathering(String gatheringName, File gatheringImage) {
        this.gatheringImage = gatheringImage;
        this.gatheringName = gatheringName;
    }
}
