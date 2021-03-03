package com.intkr.saas.module.screen.admin.item.dialog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intkr.saas.client.log.UserLogClient;
import com.intkr.saas.domain.bo.item.ItemSpuTemplateValueBO;
import com.intkr.saas.manager.item.ItemSpuTemplateValueManager;
import com.intkr.saas.util.RequestUtil;
import com.intkr.saas.util.claz.IOC;

/**
 * 
 * @author Beiden
 * @date 2016-10-20 下午10:11:06
 * @version 1.0
 */
public class ItemSpuTemplateValueAddUpdate {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ItemSpuTemplateValueManager itemSpuTemplateValueManager = IOC.get("ItemSpuTemplateValueManager");

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String shopSpuTemplateValueId = RequestUtil.getParam(request, "shopSpuTemplateValueId");
		ItemSpuTemplateValueBO shopSpuTemplateValue = itemSpuTemplateValueManager.get(shopSpuTemplateValueId);
		request.setAttribute("shopSpuTemplateValue", shopSpuTemplateValue);
		request.setAttribute("addId", itemSpuTemplateValueManager.getId());
	}

}
