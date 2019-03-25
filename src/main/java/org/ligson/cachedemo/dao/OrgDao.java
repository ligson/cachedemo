package org.ligson.cachedemo.dao;

import org.ligson.cachedemo.entity.Org;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface OrgDao extends JpaRepository<Org, String> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    List<Org> findAll();
}
