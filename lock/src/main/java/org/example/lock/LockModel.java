package org.example.lock;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String picture;
    @Column(unique = true)
    private String uid;
    private float power;
    private boolean online;
    private boolean locked;
    private Long ownerId;
    private float latitude;
    private float longitude;
    private int reportBattery;
    private int reportLocation;
    @ElementCollection
    private List<Long> users = new ArrayList<>();
    @ElementCollection
    private List<Long> managers = new ArrayList<>();
}
