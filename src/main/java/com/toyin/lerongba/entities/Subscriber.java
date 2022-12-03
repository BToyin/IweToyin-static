package com.toyin.lerongba.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "subscriber", schema = "lerongba")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Integer subscriberId;

    @Email(message = "Please enter a valid e-mail address")
    @NotEmpty(message = "Email can't be empty")
    @Basic
    @Column(name = "email", nullable = false)
    private String email;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return getSubscriberId().equals(that.getSubscriberId()) && getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubscriberId(), getEmail());
    }
}
