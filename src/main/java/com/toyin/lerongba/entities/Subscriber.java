package com.toyin.lerongba.entities;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subscriberId;

    @Email(message = "Please enter a valid e-mail address")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @CreationTimestamp
    private LocalDateTime createdTime;


    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return getSubscriberId().equals(that.getSubscriberId()) && getEmail().equals(that.getEmail()) && Objects.equals(getCreatedTime(), that.getCreatedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubscriberId(), getEmail(), getCreatedTime());
    }
}
