package org.ligson.cachedemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "f_org")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userCache")
@Cacheable
public class Org implements Serializable {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    @Column
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id")
    @JsonBackReference
    private Set<User> users = new HashSet<>();
}
