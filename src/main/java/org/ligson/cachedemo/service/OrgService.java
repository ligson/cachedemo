package org.ligson.cachedemo.service;

import org.ligson.cachedemo.entity.Org;

import java.util.List;

public interface OrgService {
    List<Org> localCache();

    List<Org> redisCache();
}
