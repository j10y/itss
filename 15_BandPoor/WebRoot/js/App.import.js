Ext.ns("App");
App.importJs = {
	AppRoleView : [ __ctxPath + "/js/system/AppRoleView.js",
			__ctxPath + "/ext3/ux/CheckTreePanel.js",
			__ctxPath + "/js/system/RoleGrantRightView.js",
			__ctxPath + "/js/system/AppRoleForm.js" ],
	UserSubView : [ __ctxPath + "/js/system/UserSubView.js",
			__ctxPath + "/js/system/UserSubForm.js" ],
	PersonalDocumentView : [
			__ctxPath + "/js/document/PersonalDocumentView.js",
			__ctxPath + "/js/document/DocumentView.js",
			__ctxPath + "/js/document/DocumentForm.js",
			__ctxPath + "/js/document/DocumentSharedForm.js",
			__ctxPath + "/js/document/DocFolderForm.js" ],
	DocumentSharedView : [ __ctxPath + "/js/document/DocumentSharedView.js",
			__ctxPath + "/js/document/DocumentSharedDetail.js" ],
	DocFolderSharedView : [
			__ctxPath + "/js/document/FindPublicDocumentView.js",
			__ctxPath + "/js/document/DocFolderView.js",
			__ctxPath + "/js/document/DocFolderForm.js",
			__ctxPath + "/js/document/DocFolderSharedView.js",
			__ctxPath + "/js/document/DocFolderSharedForm.js",
			__ctxPath + "/js/document/DocPrivilegeForm.js",
			__ctxPath + "/js/document/DocPrivilegeView.js" ],
	FindPublicDocumentView : [
			__ctxPath + "/js/document/FindPublicDocumentView.js",
			__ctxPath + "/js/document/DocumentView.js",
			__ctxPath + "/js/document/PublicDocumentView.js",
			__ctxPath + "/js/document/PublicDocumentDetail.js",
			__ctxPath + "/js/document/NewPublicDocumentForm.js",
			__ctxPath + "/js/document/DocFolderSelector.js" ],
	NewPublicDocumentForm : [
			__ctxPath + "/js/document/NewPublicDocumentForm.js",
			__ctxPath + "/js/document/DocFolderSelector.js" ],
	DocFolderMoveForm : [ __ctxPath + "/js/document/DocFolderMoveForm.js",
			__ctxPath + "/js/document/PersonalDocFolderSelector.js" ],
	NoticeView : [ __ctxPath + "/js/info/NoticeView.js",
			__ctxPath + "/js/info/NoticeForm.js" ],
	ReportTemplateView : [ __ctxPath + "/js/system/ReportTemplateView.js",
			__ctxPath + "/js/system/ReportTemplateForm.js",
			__ctxPath + "/js/system/ReportParamForm.js",
			__ctxPath + "/js/system/ReportParamView.js" ],
	MessageView : [ __ctxPath + "/js/info/MessageView.js",
			__ctxPath + "/js/info/MessageForm.js",
			__ctxPath + "/js/info/MessageWin.js" ],
	MessageManageView : [ __ctxPath + "/js/info/MessageManageView.js",
			__ctxPath + "/js/info/MessageForm.js" ],
	PhoneBookView : [ __ctxPath + "/js/communicate/PhoneBookView.js",
			__ctxPath + "/js/communicate/PhoneGroupForm.js",
			__ctxPath + "/js/communicate/PhoneBookForm.js" ],
	DepartmentView : [ __ctxPath + "/js/system/DepartmentView.js",
			__ctxPath + "/js/system/DepartmentForm.js",
			__ctxPath + "/js/system/AppUserView.js",
			__ctxPath + "/ext3/ux/ItemSelector.js",
			__ctxPath + "/ext3/ux/MultiSelect.js",
			__ctxPath + "/js/system/AppUserForm.js" ],
	AppUserView : [ __ctxPath + "/js/system/AppUserView.js",
			__ctxPath + "/js/system/AppUserForm.js",
			__ctxPath + "/ext3/ux/ItemSelector.js",
			__ctxPath + "/ext3/ux/MultiSelect.js",
			__ctxPath + "/js/system/ResetPasswordForm.js" ],
	ProfileForm : [ __ctxPath + "/js/system/ProfileForm.js",
			__ctxPath + "/js/system/ResetPasswordForm.js" ],
	NewsView : [ __ctxPath + "/js/info/NewsView.js",
			__ctxPath + "/js/info/NewsForm.js",
			__ctxPath + "/js/info/NewsTypeTree.js",
			__ctxPath + "/js/info/NewsTypeForm.js" ],
	NewsTypeView : [ __ctxPath + "/js/info/NewsTypeView.js",
			__ctxPath + "/js/info/NewsTypeForm.js" ],
	CompanyView : [ __ctxPath + "/js/system/CompanyView.js" ],
	FileAttachView : [ __ctxPath + "/js/system/FileAttachView.js",
			__ctxPath + "/js/system/FileAttachDetail.js" ],
	DiaryView : [ __ctxPath + "/js/system/DiaryView.js",
			__ctxPath + "/js/system/DiaryForm.js" ],
	MySubUserDiaryView : [ __ctxPath + "/js/system/MySubUserDiaryView.js",
			__ctxPath + "/js/system/DiaryDetail.js" ],
	FlowManagerView : [ __ctxPath + "/js/flow/ProTypeForm.js",
			__ctxPath + "/js/flow/ProDefinitionForm.js",
			__ctxPath + "/js/flow/ProDefinitionView.js",
			__ctxPath + "/js/flow/FlowManagerView.js",
			__ctxPath + "/js/flow/ProDefinitionDetail.js",
			__ctxPath + "/js/flow/ProcessRunStart.js",
			__ctxPath + "/js/flow/ProDefinitionSetting.js",
			__ctxPath + "/js/flow/MyTaskView.js",
			__ctxPath + "/js/flow/ProcessNextForm.js",
			__ctxPath + "/js/flow/FormDesignWindow.js",
			__ctxPath + "/js/flow/FormEditorWindow.js",
			__ctxPath + "/js/flowDesign/FlowDesignerWindow.js" ],
	NewProcess : [ __ctxPath + "/js/flow/NewProcess.js",
			__ctxPath + "/js/flow/ProDefinitionDetail.js",
			__ctxPath + "/js/flow/ProDefinitionView.js",
			__ctxPath + "/js/flow/ProcessRunStart.js" ],
	ProcessRunView : [ __ctxPath + "/js/flow/ProcessRunView.js",
			__ctxPath + "/js/flow/ProcessRunDetail.js",
			__ctxPath + "/js/flow/ProcessRunStart.js" ],
	MyTaskView : [ __ctxPath + "/js/flow/MyTaskView.js",
			__ctxPath + "/js/flow/ChangeTaskView.js",
			__ctxPath + "/js/flow/ProcessNextForm.js" ],
	ProcessRunFinishView : [ __ctxPath + "/js/flow/ProcessRunFinishView.js",
			__ctxPath + "/js/flow/ProcessRunDetail.js" ],
	SysConfigView : [ __ctxPath + "/js/system/SysConfigView.js" ],
	NoticeDetail : [ __ctxPath + "/js/info/NoticeDetail.js" ],
	NewsDetail : [ __ctxPath + "/js/info/NewsDetail.js" ],
	PublicDocumentDetail : [ __ctxPath + "/js/document/PublicDocumentDetail.js" ],
	SearchNews : [ __ctxPath + "/js/search/SearchNews.js",
			__ctxPath + "/js/info/NewsDetail.js" ],
	SearchNotice : [ __ctxPath + "/js/search/SearchNotice.js" ],
	SearchDocument : [ __ctxPath + "/js/search/SearchDocument.js",
			__ctxPath + "/js/document/PublicDocumentDetail.js" ],
	NewsCommentView : [ __ctxPath + "/js/info/NewsCommentView.js",
			__ctxPath + "/ext3/ux/RowExpander.js" ],
	DictionaryView : [ __ctxPath + "/js/system/DictionaryView.js",
			__ctxPath + "/js/system/DictionaryForm.js" ],
	ResourceView : [ __ctxPath + "/js/system/ResourceView.js",
			__ctxPath + "/js/system/ResourceForm.js" ],
	SystemLogView : [ __ctxPath + "/js/system/SystemLogView.js" ],
	MyProcessRunView : [ __ctxPath + "/js/flow/MyProcessRunView.js",
			__ctxPath + "/js/flow/ProcessRunDetail.js" ],
	PersonalTipsView : [ __ctxPath + "/js/info/PersonalTipsView.js" ],
	
	BandView : [__ctxPath + "/js/bandpoor/BandView.js",
	        __ctxPath + "/js/bandpoor/BandForm.js"],
	ProClassView : [__ctxPath + "/js/bandpoor/ProClassView.js",
	        __ctxPath + "/js/bandpoor/ProClassForm.js"],
	BandStyleView : [__ctxPath + "/js/bandpoor/BandStyleView.js",
	        __ctxPath + "/js/bandpoor/BandStyleForm.js"],
	BusinessAreaView : [__ctxPath + "/js/bandpoor/BusinessAreaView.js",
	        __ctxPath + "/js/bandpoor/BusinessAreaForm.js"],  		
	ScoreManageView: [ __ctxPath + "/js/bandpoor/ScoreManageView.js",
			__ctxPath + "/js/bandpoor/ScoreManageForm.js" ],
	FloorView : [__ctxPath + "/js/bandpoor/FloorView.js",
	        __ctxPath + "/js/bandpoor/FloorForm.js"],
	MainPriceView : [__ctxPath + "/js/bandpoor/MainPriceView.js",
	        __ctxPath + "/js/bandpoor/MainPriceForm.js"],
	SaleStoreView : 
		[__ctxPath + "/js/bandpoor/SaleStoreView.js",
	        __ctxPath + "/js/bandpoor/SaleStoreForm.js"]
};