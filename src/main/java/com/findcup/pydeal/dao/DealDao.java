package com.findcup.pydeal.dao;

import com.findcup.pydeal.entity.Deal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DealDao {

    /**
     * 获取规定数量的Deal
     * @param param
     * @return
     */
    List<Deal> getDeals(Map param);

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
    int deleteDeal(Object id);

    /**
     * 更新Deal
     * @param item
     * @return
     */
    int updateDeal(Deal item);

    /**
     * 获取交易总数
     * @return
     */
    int getDealNum();

    /**
     * 查找Deal
     * @param item
     * @return
     */
    int findDeal(Deal item);

}
