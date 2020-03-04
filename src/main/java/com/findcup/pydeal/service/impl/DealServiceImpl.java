package com.findcup.pydeal.service.impl;

import com.findcup.pydeal.dao.DealDao;
import com.findcup.pydeal.entity.Deal;
import com.findcup.pydeal.service.DealService;
import com.findcup.pydeal.utils.PageResult;
import com.findcup.pydeal.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DealService")
public class DealServiceImpl implements DealService {

    @Autowired(required=false)
    private DealDao dealDao;

    @Override
    public PageResult getDeals(PageUtil pageutil) {
        List<Deal> deals = dealDao.getDeals(pageutil);
        int total = dealDao.getDealNum();
        return new PageResult(deals,total,pageutil.getLimit(),pageutil.getPage());
    }

    @Override
    public int insertDeal(Deal item) {
        int result=dealDao.insertDeal(item);
        return result;
    }

    @Override
    public int DeleteDeal(int id) {
        int result =dealDao.deleteDeal(id);
        return result;
    }

    @Override
    public int UpdateDealState(Deal item) {
        int result = dealDao.UpdateDealState(item);
        return result;
    }
    @Override
    public int UpdateDealTerminator(Deal item){
        int result = dealDao.UpdateDealTerminator(item);
        return result;
    }

    public int findDeal(Deal item) {
        int result= dealDao.findDeal(item);
        return result;
    }

    public String getState(Deal item){
        String res = dealDao.getState(item);
        return res;
    }
}
