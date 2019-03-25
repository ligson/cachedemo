package org.ligson.cachedemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "f_user")
public class User {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    @Column
    private String name;
    @ManyToOne
    @JsonIgnore
    private Org org;
}
