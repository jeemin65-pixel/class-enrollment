package com.liveklass.enrollment.domain.user;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "User")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Lecture> lectures = new ArrayList<>();

    @Builder
    private User(String name, String email, Role role){
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
