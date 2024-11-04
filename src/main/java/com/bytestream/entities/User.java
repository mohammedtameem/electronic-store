package com.bytestream.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name="user_name")
    private String name;

    @Column(name="user_email",unique = true)
    private String email;

    @Column(name="user_password",length = 10)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name="user_image_name")
    private String imageName;


}
