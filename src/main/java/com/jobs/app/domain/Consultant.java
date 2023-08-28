package com.jobs.app.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consultant")
public class Consultant implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

}
