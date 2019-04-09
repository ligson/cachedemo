package org.ligson.cachedemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.ligson.cachedemo.dao.OrgDao;
import org.ligson.cachedemo.entity.Org;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrgServiceImpl implements OrgService {

    private final OrgDao orgDao;
    private List<Org> orgs = new ArrayList<>();
    private final RedisTemplate<String, ArrayList<Org>> redisTemplate;
    private final String ORGS_KEY = "orgs";
    private final ObjectMapper objectMapper;

    /****
     * 构造注入的好处
     * 依赖不可变：其实说的就是final关键字，这里不再多解释了。不明白的园友可以回去看看Java语法。
     * 依赖不为空（省去了我们对其检查）：当要实例化OrgService的时候，由于自己实现了有参数的构造函数，所以不会调用默认构造函数，那么就需要Spring容器传入所需要的参数，所以就两种情况：1、有该类型的参数->传入，OK 。2：无该类型的参数->报错。所以保证不会为空，Spring总不至于传一个null进去吧 :-(
     * 完全初始化的状态：这个可以跟上面的依赖不为空结合起来，向构造器传参之前，要确保注入的内容不为空，那么肯定要调用依赖组件的构造方法完成实例化。而在Java类加载实例化的过程中，构造方法是最后一步（之前如果有父类先初始化父类，然后自己的成员变量，最后才是构造方法，这里不详细展开。）。所以返回来的都是初始化之后的状态。
     * @param orgDao
     * @param redisTemplate
     * @param objectMapper
     */
    public OrgServiceImpl(OrgDao orgDao, RedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.orgDao = orgDao;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Org> localCache() {
        if (CollectionUtils.isEmpty(orgs)) {
            orgs = orgDao.findAll();
        }
        return orgs;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Org> redisCache() {
        Boolean hasKey = redisTemplate.hasKey(ORGS_KEY);
        if (hasKey != null && hasKey) {
            return redisTemplate.boundValueOps(ORGS_KEY).get();
        } else {
            ArrayList<Org> orgs = (ArrayList<Org>) localCache();
            redisTemplate.boundValueOps(ORGS_KEY).set(orgs);
            return orgs;
        }
    }
}
