package com.zxwl.pay.api.service.impl;

import com.zxwl.pay.api.dao.ApyAccountDao;
import com.zxwl.pay.api.entity.ApyAccount;
import com.zxwl.pay.api.service.ApyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chendawei on 2017/6/18.
 */
@Service("ApyAccountService")
public class ApyAccountServiceImpl implements ApyAccountService
{

    @Autowired
    private AutowireCapableBeanFactory spring;
    @Autowired
    private ApyAccountDao dao;
    //缓存
    private final static Map<Integer, PayResponse> payResponses = new HashMap<Integer, PayResponse> ();
    @Override
    public PayResponse getPayResponse(Integer id) {
        PayResponse payResponse = payResponses.get(id);
        if (payResponse  == null) {
            ApyAccount apyAccount = dao.findByPayId (id);
            if (apyAccount == null) {
                throw new IllegalArgumentException ("无法查询");
            }
            payResponse = new PayResponse();
            spring.autowireBean(payResponse);
            payResponse.init(apyAccount);
//            payResponses.put(id, payResponse);
            // 查询
        }
        return payResponse;
    }

    @Override
    public void save(ApyAccount apyAccount) {

        dao.save ( apyAccount );
    }
}
