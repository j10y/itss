package com.jeecms.cms.action.directive;

import static com.jeecms.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.freemarker.DirectiveUtils;
import com.jeecms.common.web.freemarker.ParamsRequiredException;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容对象标签
 * 
 * @author liufang
 * 
 */
public class ContentDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，内容ID。
	 */
	public static final String PARAM_ID = "id";
	/**
	 * 输入参数，下一篇。
	 */
	public static final String PRAMA_NEXT = "next";
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_CHANNEL_ID = "channelId";
	
	/**
	 * 输入参数，如果是期刊的话，需要同期的下一个版面值：D1---D4。可后台配置数值
	 */
	public static final String PARAM_JOUNAL_PAGE_NUM = "forum";
	
	/**
	 * 输入参数，如果是期刊，则需要提供相同期数下的上下页，故此需要提供期数。
	 */
	public static final String PARAM_JOUNAL_NUM	= "jounalNum";
	
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = getId(params);
		Boolean next = DirectiveUtils.getBool(PRAMA_NEXT, params);
		Content content;
		if (next == null) {
			content = contentMng.findById(id);
		} else {
			CmsSite site = FrontUtils.getSite(env);
			Integer channelId = DirectiveUtils.getInt(PARAM_CHANNEL_ID, params);
			
			String jounalNum = DirectiveUtils.getString(PARAM_JOUNAL_NUM, params);
			if(jounalNum != null){
				String forum = DirectiveUtils.getString(PARAM_JOUNAL_PAGE_NUM, params);
				content = contentMng.getSide(id, site.getId(), channelId, next, jounalNum, forum);
			}else{
				content = contentMng.getSide(id, site.getId(), channelId, next);
			}
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(content));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getId(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		if (id != null) {
			return id;
		} else {
			throw new ParamsRequiredException(PARAM_ID);
		}
	}

	@Autowired
	private ContentMng contentMng;
}
