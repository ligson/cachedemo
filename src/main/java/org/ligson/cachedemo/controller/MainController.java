package org.ligson.cachedemo.controller;

import org.ligson.cachedemo.dao.OrgDao;
import org.ligson.cachedemo.entity.Org;
import org.ligson.cachedemo.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    private final OrgDao orgDao;
    private final OrgService orgService;

    @Autowired
    public MainController(OrgDao orgDao, OrgService orgService) {
        this.orgDao = orgDao;
        this.orgService = orgService;
    }

    @RequestMapping("/orgs")
    public List<Org> orgs() {
        return orgDao.findAll();
    }

    @RequestMapping("/redisCache")
    public List<Org> localCache() {
        return orgService.redisCache();
    }

    @RequestMapping("/redis")
    public List<Org> redisCache() {
        return orgService.redisCache();
    }
}
