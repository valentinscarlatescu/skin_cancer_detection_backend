package com.scdbackend.data.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Malign cannot be null")
    private Integer malign = 0;

    @NotNull(message = "Benign cannot be null")
    private Integer benign = 0;

    @CreationTimestamp
    private LocalDateTime dateTime;

    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setMalign(Integer malign) {
        this.malign = 0;
    }

    public Integer getMalign() {
        return malign;
    }

    public void setBenign(Integer benign) {
        this.benign = 0;
    }

    public Integer getBenign() {
        return benign;
    }
}
