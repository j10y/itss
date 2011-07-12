package com.jeecms.cms.action.member;

import static com.jeecms.cms.Constants.TPLDIR_MEMBER;
import static com.jeecms.common.page.SimplePage.cpn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.CmsGroup;
import com.jeecms.cms.entity.main.CmsModel;
import com.jeecms.cms.entity.main.CmsModelItem;
import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.entity.main.CmsTopic;
import com.jeecms.cms.entity.main.CmsUser;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.entity.main.MemberConfig;
import com.jeecms.cms.entity.main.Content.ContentStatus;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsGroupMng;
import com.jeecms.cms.manager.main.CmsModelItemMng;
import com.jeecms.cms.manager.main.CmsModelMng;
import com.jeecms.cms.manager.main.CmsTopicMng;
import com.jeecms.cms.manager.main.CmsUserMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.cms.web.WebErrors;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.upload.FileRepository;
import com.jeecms.common.util.StrUtils;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.session.SessionProvider;
import com.jeecms.common.web.springmvc.MessageResolver;
import com.jeecms.core.manager.DbFileMng;
import com.jeecms.core.tpl.TplManager;
import com.jeecms.core.web.CoreUtils;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 相册Action
 * 
 * @author awen
 * 
 */
@Controller
public class AlbumAct {
	private static final Logger log = LoggerFactory
			.getLogger(AlbumAct.class);

	public static final String CONTRIBUTE_LIST = "tpl.contributeList";
	public static final String CONTRIBUTE_ADD = "tpl.contributeAdd";
	public static final String CONTRIBUTE_EDIT = "tpl.contributeEdit";
	
	@Autowired
	private ContentMng manager;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemMng cmsModelItemMng;
	@Autowired
	private CmsTopicMng cmsTopicMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private TplManager tplManager;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private DbFileMng dbFileMng;
	
	public static final String ALBUM_INPUT = "tpl.albumInput";
	public static final String ALBUM_EDIT = "tpl.albumEdit";
	public static final String ALBUM_List = "tpl.albumList";
	
	/**
	 * 相册列表
	 * @param queryTitle
	 * @param queryChannelId
	 * @param pageNo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/album_list.jspx")
	public String list(String queryTitle, Integer queryChannelId,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination p = contentMng.getPageForMember(queryTitle, queryChannelId,
				site.getId(), user.getId(), cpn(pageNo), 20);
		model.addAttribute("pagination", p);
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (queryChannelId != null) {
			model.addAttribute("queryChannelId", queryChannelId);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, ALBUM_List);
	}
	
	/**
	 * 相册上传
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/album_input.jspx")
	public String input(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);
		model.addAttribute("channelList", channelList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, ALBUM_INPUT);
	}
	
	/**
	 * 相册修改
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/album_edit.jspx")
	public String edit(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);
		model.addAttribute("channelList", channelList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, ALBUM_EDIT);
	}

	/**
	 * 相册删除
	 * @param ids
	 * @param request
	 * @param nextUrl
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/album_delete.jspx")
	public String delete(Integer[] ids, HttpServletRequest request,
			String nextUrl, HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
//		WebErrors errors = validateDelete(ids, site, user, request);
//		if (errors.hasErrors()) {
//			return FrontUtils.showError(request, response, model, errors);
//		}
		Content[] arr = contentMng.deleteByIds(ids);
		log.info("member contribute delete Content success. ids={}",
				StringUtils.join(arr, ","));
		return FrontUtils.showSuccess(request, model, nextUrl);
	}
	
	
	@RequestMapping("/member/o_save.jspx")
	public String save(Content bean, ContentExt ext, ContentTxt txt,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, channelId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
//		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
//				.getMessage(request, "content.tagStr.split"));
		bean = manager.save(bean, ext, txt, channelIds, topicIds, viewGroupIds,
				null, attachmentPaths, attachmentNames, attachmentFilenames,
				picPaths, picDescs, channelId, typeId, draft, user, false);
		log.info("save Content id={}", bean.getId());
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		model.addAttribute("message", "global.success");
		return add(cid, request, model);
	}
	
	@RequestMapping("/member/v_add.jspx")
	public String add(Integer cid, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateAdd(cid, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		// 栏目
		Channel c;
		if (cid != null) {
			c = channelMng.findById(cid);
		} else {
			c = null;
		}
		// 模型
		CmsModel m;
		if (c != null) {
			m = c.getModel();
		} else {
			m = cmsModelMng.getDefModel();
			// TODO m==null给出错误提示
			if (m == null) {
				throw new RuntimeException("default model not found!");
			}
		}
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), false,
				false);
		// 栏目列表
		List<Channel> channelList;
		Set<Channel> rights;
//		if (user.getUserSite(siteId).getAllChannel()) {
//			// 拥有所有栏目权限
//			rights = null;
//		} else {
//			rights = user.getChannels(siteId);
//		}
		rights = null;
		channelList = c.getListForSelect(rights, true);
//		if (c != null) {
//			channelList = c.getListForSelect(rights, true);
//		} else {
//			List<Channel> topList = channelMng.getTopListByRigth(userId,
//					siteId, true);
//			channelList = Channel.getListForSelect(topList, rights, true);
//		}

		// 专题列表
		List<CmsTopic> topicList;
		if (c != null) {
			topicList = cmsTopicMng.getListByChannel(c.getId());
		} else {
			topicList = new ArrayList<CmsTopic>();
		}
		// 内容模板列表
		List<String> tplList = getTplContent(site, m, null);
		// 会员组列表
		List<CmsGroup> groupList = cmsGroupMng.getList();
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);

		model.addAttribute("model", m);
		model.addAttribute("itemList", itemList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("topicList", topicList);
		model.addAttribute("tplList", tplList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("typeList", typeList);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		if (c != null) {
			model.addAttribute("channel", c);
		}
		//return "content/add";
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, ALBUM_INPUT);
	}
	
	@RequestMapping("/member/o_update.jspx")
	public String update(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Content bean, ContentExt ext, ContentTxt txt,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		/*String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
				.getMessage(request, "content.tagStr.split"));*/
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		bean = manager.update(bean, ext, txt, null, channelIds, topicIds,
				viewGroupIds, attachmentPaths, attachmentNames,
				attachmentFilenames, picPaths, picDescs, attr, channelId,
				typeId, draft, user, false);
		log.info("update Content id={}.", bean.getId());
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}
	
	@RequestMapping("/member/v_list.jspx")
	public String list(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		queryTitle = StringUtils.trim(queryTitle);
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		queryInputUsername = StringUtils.trim(queryInputUsername);
		if (queryTopLevel == null) {
			queryTopLevel = false;
		}
		if (queryRecommend == null) {
			queryRecommend = false;
		}
		if (queryOrderBy == null) {
			queryOrderBy = 0;
		}
		ContentStatus status;
		if (!StringUtils.isBlank(queryStatus)) {
			status = ContentStatus.valueOf(queryStatus);
		} else {
			status = ContentStatus.all;
		}
		Integer queryInputUserId = null;
		if (!StringUtils.isBlank(queryInputUsername)) {
			CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
			if (u != null) {
				queryInputUserId = u.getId();
			} else {
				// 用户名不存在，清空。
				queryInputUsername = null;
			}
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		//byte currStep = user.getCheckStep(siteId);
		Byte currStep = user.getCheckStep(siteId);
		Pagination p = manager.getPageByRight(queryTitle, queryTypeId,
				queryInputUserId, queryTopLevel, queryRecommend, status, user
						.getCheckStep(siteId), siteId, cid, userId,
				queryOrderBy, cpn(pageNo), CookieUtils.getPageSize(request));
		List<ContentType> typeList = contentTypeMng.getList(true);
		model.addAttribute("pagination", p);
		model.addAttribute("cid", cid);
		model.addAttribute("typeList", typeList);
		model.addAttribute("currStep", currStep);

		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);

		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, ALBUM_List);
	}
	
	private void addAttibuteForQuery(ModelMap model, String queryTitle,
			String queryInputUsername, String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo) {
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (!StringUtils.isBlank(queryInputUsername)) {
			model.addAttribute("queryInputUsername", queryInputUsername);
		}
		if (queryTypeId != null) {
			model.addAttribute("queryTypeId", queryTypeId);
		}
		if (queryStatus != null) {
			model.addAttribute("queryStatus", queryStatus);
		}
		if (queryTopLevel != null) {
			model.addAttribute("queryTopLevel", queryTopLevel);
		}
		if (queryRecommend != null) {
			model.addAttribute("queryRecommend", queryRecommend);
		}
		if (queryOrderBy != null) {
			model.addAttribute("queryOrderBy", queryOrderBy);
		}
		if (pageNo != null) {
			model.addAttribute("pageNo", pageNo);
		}
	}
	
	private List<String> getTplContent(CmsSite site, CmsModel model, String tpl) {
		String sol = site.getSolutionPath();
		String tplPath = site.getTplPath();
		List<String> tplList = tplManager.getNameListByPrefix(model
				.getTplContent(sol, false));
		tplList = CoreUtils.tplTrim(tplList, tplPath, tpl);
		return tplList;
	}

	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateAdd(Integer cid, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (cid == null) {
			return errors;
		}
		Channel c = channelMng.findById(cid);
		if (errors.ifNotExist(c, Channel.class, cid)) {
			return errors;
		}
		Integer siteId = CmsUtils.getSiteId(request);
		if (!c.getSite().getId().equals(siteId)) {
			errors.notInSite(Channel.class, cid);
			return errors;
		}
		return errors;
	}
	
	private WebErrors validateSave(Content bean, Integer channelId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		if (errors.ifNull(channelId, "channelId")) {
			return errors;
		}
		Channel channel = channelMng.findById(channelId);
		if (errors.ifNotExist(channel, Channel.class, channelId)) {
			return errors;
		}
		if (channel.getChild().size() > 0) {
			errors.addErrorCode("content.error.notLeafChannel");
		}
		return errors;
	}

	private WebErrors validateView(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		// Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		// 是否有审核后更新权限。
//		if (!content.isHasUpdateRight()) {
//			errors.addErrorCode("content.error.afterCheckUpdate");
//			return errors;
//		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			if (vldExist(id, site.getId(), errors)) {
				return errors;
			}
			Content content = manager.findById(id);
			// TODO 是否有编辑的数据权限。
			// 是否有审核后删除权限。
			if (!content.isHasDeleteRight()) {
				errors.addErrorCode("content.error.afterCheckDelete");
				return errors;
			}

		}
		return errors;
	}

	private WebErrors validateCheck(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateReject(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateUpload(MultipartFile file,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(file, "file")) {
			return errors;
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Content entity = manager.findById(id);
		if (errors.ifNotExist(entity, Content.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(Content.class, id);
			return true;
		}
		return false;
	}
	
//	/**
//	 * 会员投稿列表
//	 * 
//	 * @param title
//	 *            文章标题
//	 * @param channelId
//	 *            栏目ID
//	 * @param pageNo
//	 *            页码
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_list.jspx")
//	public String list(String queryTitle, Integer queryChannelId,
//			Integer pageNo, HttpServletRequest request, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		Pagination p = contentMng.getPageForMember(queryTitle, queryChannelId,
//				site.getId(), user.getId(), cpn(pageNo), 20);
//		model.addAttribute("pagination", p);
//		if (!StringUtils.isBlank(queryTitle)) {
//			model.addAttribute("queryTitle", queryTitle);
//		}
//		if (queryChannelId != null) {
//			model.addAttribute("queryChannelId", queryChannelId);
//		}
//		return FrontUtils.getTplPath(request, site.getSolutionPath(),
//				TPLDIR_MEMBER, CONTRIBUTE_LIST);
//	}
//
//	/**
//	 * 会员投稿添加
//	 * 
//	 * @param request
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_add.jspx")
//	public String add(HttpServletRequest request, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		// 获得本站栏目列表
//		Set<Channel> rights = user.getGroup().getContriChannels();
//		List<Channel> topList = channelMng.getTopList(site.getId(), true);
//		List<Channel> channelList = Channel.getListForSelect(topList, rights,
//				true);
//		model.addAttribute("channelList", channelList);
//		return FrontUtils.getTplPath(request, site.getSolutionPath(),
//				TPLDIR_MEMBER, CONTRIBUTE_ADD);
//	}
//
//	/**
//	 * 会员投稿保存
//	 * 
//	 * @param id
//	 *            文章ID
//	 * @param title
//	 *            标题
//	 * @param author
//	 *            作者
//	 * @param description
//	 *            描述
//	 * @param txt
//	 *            内容
//	 * @param tagStr
//	 *            TAG字符串
//	 * @param channelId
//	 *            栏目ID
//	 * @param nextUrl
//	 *            下一个页面地址
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_save.jspx")
//	public String save(String title, String author, String description,
//			String txt, String tagStr, Integer channelId, String captcha,
//			String nextUrl, HttpServletRequest request,
//			HttpServletResponse response, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		WebErrors errors = validateSave(title, author, description, txt,
//				tagStr, channelId, site, user, captcha, request, response);
//		if (errors.hasErrors()) {
//			return FrontUtils.showError(request, response, model, errors);
//		}
//
//		Content c = new Content();
//		c.setSite(site);
//		ContentExt ext = new ContentExt();
//		ext.setTitle(title);
//		ext.setAuthor(author);
//		ext.setDescription(description);
//		ContentTxt t = new ContentTxt();
//		t.setTxt(txt);
//		ContentType type = contentTypeMng.getDef();
//		if (type == null) {
//			throw new RuntimeException("Default ContentType not found.");
//		}
//		Integer typeId = type.getId();
//		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
//		c = contentMng.save(c, ext, t, null, null, null, tagArr, null, null,
//				null, null, null, channelId, typeId, null, user, true);
//		log.info("member contribute save Content success. id={}", c.getId());
//		return FrontUtils.showSuccess(request, model, nextUrl);
//	}
//
//	/**
//	 * 会员投稿修改
//	 * 
//	 * @param id
//	 *            文章ID
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_edit.jspx")
//	public String edit(Integer id, HttpServletRequest request,
//			HttpServletResponse response, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		WebErrors errors = validateEdit(id, site, user, request);
//		if (errors.hasErrors()) {
//			return FrontUtils.showError(request, response, model, errors);
//		}
//		Content content = contentMng.findById(id);
//		// 获得本站栏目列表
//		Set<Channel> rights = user.getGroup().getContriChannels();
//		List<Channel> topList = channelMng.getTopList(site.getId(), true);
//		List<Channel> channelList = Channel.getListForSelect(topList, rights,
//				true);
//		model.addAttribute("content", content);
//		model.addAttribute("channelList", channelList);
//		return FrontUtils.getTplPath(request, site.getSolutionPath(),
//				TPLDIR_MEMBER, CONTRIBUTE_EDIT);
//	}
//
//	/**
//	 * 会有投稿更新
//	 * 
//	 * @param id
//	 *            文章ID
//	 * @param title
//	 *            标题
//	 * @param author
//	 *            作者
//	 * @param description
//	 *            描述
//	 * @param txt
//	 *            内容
//	 * @param tagStr
//	 *            TAG字符串
//	 * @param channelId
//	 *            栏目ID
//	 * @param nextUrl
//	 *            下一个页面地址
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_update.jspx")
//	public String update(Integer id, String title, String author,
//			String description, String txt, String tagStr, Integer channelId,
//			String nextUrl, HttpServletRequest request,
//			HttpServletResponse response, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		WebErrors errors = validateUpdate(id, channelId, site, user, request);
//		if (errors.hasErrors()) {
//			return FrontUtils.showError(request, response, model, errors);
//		}
//		Content c = new Content();
//		c.setId(id);
//		c.setSite(site);
//		ContentExt ext = new ContentExt();
//		ext.setId(id);
//		ext.setTitle(title);
//		ext.setAuthor(author);
//		ext.setDescription(description);
//		ContentTxt t = new ContentTxt();
//		t.setId(id);
//		t.setTxt(txt);
//		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
//		contentMng.update(c, ext, t, tagArr, null, null, null, null, null,
//				null, null, null, null, channelId, null, null, user, true);
//		return FrontUtils.showSuccess(request, model, nextUrl);
//	}
//
//	/**
//	 * 会员投稿删除
//	 * 
//	 * @param ids
//	 *            待删除的文章ID数组
//	 * @param nextUrl
//	 *            下一个页面地址
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/member/contribute_delete.jspx")
//	public String delete(Integer[] ids, HttpServletRequest request,
//			String nextUrl, HttpServletResponse response, ModelMap model) {
//		CmsSite site = CmsUtils.getSite(request);
//		CmsUser user = CmsUtils.getUser(request);
//		FrontUtils.frontData(request, model, site);
//		MemberConfig mcfg = site.getConfig().getMemberConfig();
//		// 没有开启会员功能
//		if (!mcfg.isMemberOn()) {
//			return FrontUtils.showMessage(request, model, "member.memberClose");
//		}
//		if (user == null) {
//			return FrontUtils.showLogin(request, model, site);
//		}
//		WebErrors errors = validateDelete(ids, site, user, request);
//		if (errors.hasErrors()) {
//			return FrontUtils.showError(request, response, model, errors);
//		}
//		Content[] arr = contentMng.deleteByIds(ids);
//		log.info("member contribute delete Content success. ids={}",
//				StringUtils.join(arr, ","));
//		return FrontUtils.showSuccess(request, model, nextUrl);
//	}

	private WebErrors validateSave(String title, String author,
			String description, String txt, String tagStr, Integer channelId,
			CmsSite site, CmsUser user, String captcha,
			HttpServletRequest request, HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		try {
			if (!imageCaptchaService.validateResponseForID(session
					.getSessionId(request, response), captcha)) {
				errors.addErrorCode("error.invalidCaptcha");
				return errors;
			}
		} catch (CaptchaServiceException e) {
			errors.addErrorCode("error.exceptionCaptcha");
			log.warn("", e);
			return errors;
		}
		if (errors.ifBlank(title, "title", 150)) {
			return errors;
		}
		if (errors.ifMaxLength(author, "author", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(description, "description", 255)) {
			return errors;
		}
		// 内容不能大于1M
		if (errors.ifBlank(txt, "txt", 1048575)) {
			return errors;
		}
		if (errors.ifMaxLength(tagStr, "tagStr", 255)) {
			return errors;
		}
		if (errors.ifNull(channelId, "channelId")) {
			return errors;
		}
		if (vldChannel(errors, site, user, channelId)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, CmsSite site, CmsUser user,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, new Integer[] { id })) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, Integer channelId,
			CmsSite site, CmsUser user, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, new Integer[] { id })) {
			return errors;
		}
		if (vldChannel(errors, site, user, channelId)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, CmsSite site, CmsUser user,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, ids)) {
			return errors;
		}
		return errors;
	}

	private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
			Integer[] ids) {
		for (Integer id : ids) {
			if (errors.ifNull(id, "id")) {
				return true;
			}
			Content c = contentMng.findById(id);
			// 数据不存在
			if (errors.ifNotExist(c, Content.class, id)) {
				return true;
			}
			// 非本用户数据
			if (!c.getUser().getId().equals(user.getId())) {
				errors.noPermission(Content.class, id);
				return true;
			}
			// 非本站点数据
			if (!c.getSite().getId().equals(site.getId())) {
				errors.notInSite(Content.class, id);
				return true;
			}
			// 文章级别大于0，不允许修改
			if (c.getCheckStep() > 0) {
				errors.addErrorCode("member.contentChecked");
				return true;
			}
		}
		return false;
	}

	private boolean vldChannel(WebErrors errors, CmsSite site, CmsUser user,
			Integer channelId) {
		Channel channel = channelMng.findById(channelId);
		if (errors.ifNotExist(channel, Channel.class, channelId)) {
			return true;
		}
		if (!channel.getSite().getId().equals(site.getId())) {
			errors.notInSite(Channel.class, channelId);
			return true;
		}
		if (!channel.getContriGroups().contains(user.getGroup())) {
			errors.noPermission(Channel.class, channelId);
			return true;
		}
		return false;
	}

	@Autowired
	private ContentMng contentMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
}
