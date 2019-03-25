package org.ligson.cachedemo.dao;

import org.ligson.cachedemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
}
