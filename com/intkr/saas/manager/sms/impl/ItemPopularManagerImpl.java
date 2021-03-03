package com.intkr.saas.manager.sms.impl;

import com.intkr.saas.dao.BaseDAO;
import com.intkr.saas.manager.BaseManagerImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.intkr.saas.dao.sms.ItemPopularDAO;
import com.intkr.saas.domain.bo.sms.ItemPopularBO;
import com.intkr.saas.domain.dbo.sms.ItemPopularDO;
import com.intkr.saas.manager.sms.ItemPopularManager;

/**
 * 人气商品
 * 
 * @table item_popular
 * 
 * @author Beiden
 * @date 2020-11-11 23:10:57
 * @version 1.0
 */
@Repository("ItemPopularManager")
public class ItemPopularManagerImpl extends BaseManagerImpl<ItemPopularBO, ItemPopularDO> implements ItemPopularManager {

	@Resource
	private ItemPopularDAO itemPopularDAO;

	public BaseDAO<ItemPopularDO> getBaseDAO() {
		return itemPopularDAO;
	}

}
