package org.ligson.cachedemo;

import org.ligson.cachedemo.dao.OrgDao;
import org.ligson.cachedemo.entity.Org;
import org.ligson.cachedemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableCaching
public class Application {
    @Autowired
    private OrgDao orgDao;

    @RequestMapping("/save")
    public String save() {
        User user1 = new User();
        user1.setName("user1");
        Org org = new Org();
        org.setName("org");
        org.getUsers().add(user1);
        orgDao.save(org);
        return org.getId();
    }

    @RequestMapping("/orgs")
    public List<Org> orgs() {
        return orgDao.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
