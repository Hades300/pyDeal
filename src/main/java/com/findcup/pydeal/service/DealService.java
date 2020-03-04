package com.findcup.pydeal.service;

import com.findcup.pydeal.entity.Deal;
import com.findcup.pydeal.utils.PageResult;
import com.findcup.pydeal.utils.PageUtil;

import java.util.List;
import java.util.Map;

public interface DealService {

    /**
     * 获取规定数量的Deal
     * @param pageutil
     * @return
     */
    PageResult getDeals(PageUtil pageutil);

    /**
     * 插入Deal
     * @param item
     * @return
     */
    int insertDeal(Deal item);

    /**
     * 删除Deal
     * @param id
     * @return
     */
    int DeleteDeal(int id);

    /**
     * 更新Deal
     * @param item
     * @return
     */
    int UpdateDealState(Deal item);

    /**
     * 查找Deal是否存在
     * @param item
     * @return
     */

    int UpdateDealTerminator(Deal item);

    int findDeal(Deal deal);


    String getState(Deal deal);
}
