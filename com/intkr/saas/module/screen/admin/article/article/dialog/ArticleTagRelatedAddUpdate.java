package com.intkr.saas.module.screen.admin.article.article.dialog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intkr.saas.domain.bo.article.ArticleTagRelatedBO;
import com.intkr.saas.manager.cms.article.ArticleTagRelatedManager;
import com.intkr.saas.client.log.UserLogClient;
import com.intkr.saas.util.RequestUtil;
import com.intkr.saas.util.claz.IOC;

/**
 * 
 * @author Beiden
 * @date 2016-10-20 下午10:11:06
 * @version 1.0
 */
public class ArticleTagRelatedAddUpdate {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ArticleTagRelatedManager manager = IOC.get(ArticleTagRelatedManager.class);

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String articleTagRelatedId = RequestUtil.getParam(request, "articleTagRelatedId");
		ArticleTagRelatedBO articleTagRelated = manager.get(articleTagRelatedId);
		request.setAttribute("articleTagRelated", articleTagRelated);
		request.setAttribute("addId", manager.getId());
	}

}
