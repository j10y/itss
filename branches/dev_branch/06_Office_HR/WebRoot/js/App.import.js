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
	PersonalMailBoxView : [
			__ctxPath + "/js/communicate/PersonalMailBoxView.js",
			__ctxPath + "/js/communicate/MailView.js",
			__ctxPath + "/js/communicate/MailForm.js",
			__ctxPath + "/js/communicate/MailFolderForm.js",
			__ctxPath + "/ext3/ux/RowExpander.js" ],
	MailForm : [ __ctxPath + "/js/communicate/MailForm.js" ],
	PersonalPhoneBookView : [
			__ctxPath + "/js/communicate/PersonalPhoneBookView.js",
			__ctxPath + "/js/communicate/PhoneBookView.js",
			__ctxPath + "/js/communicate/PhoneGroupForm.js",
			__ctxPath + "/js/communicate/PhoneBookForm.js" ],
	SharedPhoneBookView : [
			__ctxPath + "/js/communicate/SharedPhoneBookView.js",
			__ctxPath + "/js/communicate/SharedPhoneBookWin.js" ],
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
	BookManageView : [ __ctxPath + "/js/admin/BookManageView.js",
			__ctxPath + "/js/admin/BookView.js",
			__ctxPath + "/js/admin/BookForm.js",
			__ctxPath + "/js/admin/BookTypeForm.js",
			__ctxPath + "/js/admin/BookBorrowForm.js",
			__ctxPath + "/js/admin/BookAmountForm.js" ],
	BookTypeView : [ __ctxPath + "/js/admin/BookTypeView.js",
			__ctxPath + "/js/admin/BookTypeForm.js" ],
	BookBorrowView : [ __ctxPath + "/js/admin/BookBorrowView.js",
			__ctxPath + "/js/admin/BookBorrowForm.js",
			__ctxPath + "/js/admin/BookReturnForm.js" ],
	BookReturnView : [ __ctxPath + "/js/admin/BookReturnView.js",
			__ctxPath + "/js/admin/BookReturnForm.js" ],
	OfficeGoodsManageView : [ __ctxPath + "/js/admin/OfficeGoodsManageView.js",
			__ctxPath + "/js/admin/OfficeGoodsTypeForm.js",
			__ctxPath + "/js/admin/OfficeGoodsView.js",
			__ctxPath + "/js/admin/OfficeGoodsForm.js" ],
	InStockView : [ __ctxPath + "/js/admin/InStockView.js",
			__ctxPath + "/js/admin/InStockForm.js" ],
	GoodsApplyView : [ __ctxPath + "/js/admin/GoodsApplyView.js",
			__ctxPath + "/js/admin/GoodsApplyForm.js" ],
	CarView : [ __ctxPath + "/js/admin/CarView.js",
			__ctxPath + "/js/admin/CarForm.js" ],
	CartRepairView : [ __ctxPath + "/js/admin/CartRepairView.js",
			__ctxPath + "/js/admin/CartRepairForm.js" ],
	CarApplyView : [ __ctxPath + "/js/admin/CarApplyView.js",
			__ctxPath + "/js/admin/CarApplyForm.js" ],
	AppointmentView : [ __ctxPath + "/js/task/AppointmentView.js",
			__ctxPath + "/js/task/AppointmentForm.js" ],
	CalendarPlanView : [ __ctxPath + "/js/task/CalendarPlanView.js",
			__ctxPath + "/js/task/CalendarPlanForm.js",
			__ctxPath + "/js/task/CalendarPlanFinishForm.js" ],
	MyPlanTaskView : [ __ctxPath + "/js/task/CalendarPlanView.js",
			__ctxPath + "/js/task/CalendarPlanForm.js",
			__ctxPath + "/js/task/CalendarPlanFinishForm.js",
			__ctxPath + "/ext3/ux/caltask/e2cs_zh_CN.js",
			__ctxPath + "/ext3/ux/caltask/calendar.gzjs",
			__ctxPath + "/ext3/ux/caltask/scheduler.gzjs",
			__ctxPath + "/ext3/ux/caltask/monthview.gzjs",
			__ctxPath + "/ext3/ux/caltask/weekview.gzjs",
			__ctxPath + "/ext3/ux/caltask/dayview.gzjs",
			__ctxPath + "/ext3/ux/caltask/task.gzjs",
			__ctxPath + "/js/task/MyPlanTaskView.gzjs",
			__ctxPath + "/js/task/CalendarPlanDetailView.js" ],
	PlanTypeView : [ __ctxPath + "/js/task/PlanTypeView.js",
			__ctxPath + "/js/task/PlanTypeForm.js" ],
	WorkPlanView : [ __ctxPath + "/js/task/WorkPlanView.js",
			__ctxPath + "/js/task/NewWorkPlanForm.js" ],
	PersonalWorkPlanView : [ __ctxPath + "/js/task/PersonalWorkPlanView.js",
			__ctxPath + "/js/task/WorkPlanDetail.js" ],
	NewWorkPlanForm : [ __ctxPath + "/js/task/NewWorkPlanForm.js" ],
	DepWorkPlanView : [ __ctxPath + "/js/task/DepWorkPlanView.js",
			__ctxPath + "/js/task/WorkPlanDetail.js" ],
	CustomerView : [ __ctxPath + "/js/customer/CustomerView.js",
			__ctxPath + "/js/customer/CustomerForm.js",
			__ctxPath + "/js/customer/CusLinkmanForm.js",
			__ctxPath + "/js/customer/SendMailForm.js" ],
	CusLinkmanView : [ __ctxPath + "/js/customer/CusLinkmanView.js",
			__ctxPath + "/js/customer/CusLinkmanForm.js" ],
	FixedAssetsManageView : [ __ctxPath + "/js/admin/FixedAssetsManageView.js",
			__ctxPath + "/js/admin/FixedAssetsView.js",
			__ctxPath + "/js/admin/FixedAssetsForm.js",
			__ctxPath + "/js/admin/AssetsTypeForm.js",
			__ctxPath + "/js/admin/DepreWin.js",
			__ctxPath + "/js/admin/WorkGrossWin.js" ],
	DepreTypeView : [ __ctxPath + "/js/admin/DepreTypeForm.js",
			__ctxPath + "/js/admin/DepreTypeView.js" ],
	DepreRecordView : [ __ctxPath + "/js/admin/DepreRecordForm.js",
			__ctxPath + "/js/admin/DepreRecordView.js" ],
	CusConnectionView : [ __ctxPath + "/js/customer/CusConnectionView.js",
			__ctxPath + "/js/customer/CusConnectionForm.js" ],
	ProjectView : [ __ctxPath + "/js/customer/ProjectView.js",
			__ctxPath + "/js/customer/ProjectForm.js",
			__ctxPath + "/js/customer/ContractForm.js",
			__ctxPath + "/js/customer/ContractConfigView.js" ],
	ContractView : [ __ctxPath + "/js/customer/ContractView.js",
			__ctxPath + "/js/customer/ContractForm.js",
			__ctxPath + "/js/customer/ContractConfigView.js" ],
	ProductView : [ __ctxPath + "/js/customer/ProductView.js",
			__ctxPath + "/js/customer/ProductForm.js" ],
	ProviderView : [ __ctxPath + "/js/customer/ProviderView.js",
			__ctxPath + "/js/customer/ProviderForm.js",
			__ctxPath + "/js/customer/SendMailForm.js" ],
	HolidayRecordView : [ __ctxPath + "/js/personal/HolidayRecordView.js",
			__ctxPath + "/js/personal/HolidayRecordForm.js" ],
	DutySectionView : [ __ctxPath + "/js/personal/DutySectionView.js",
			__ctxPath + "/js/personal/DutySectionForm.js" ],
	DutySystemView : [ __ctxPath + "/js/personal/DutySystemView.js",
			__ctxPath + "/js/personal/DutySystemForm.js",
			__ctxPath + "/js/selector/DutySectionSelector.js" ],
	SignInOffView : [ __ctxPath + "/js/personal/SignInOffView.js" ],
	DutyRegisterPersonView : [ __ctxPath
			+ "/js/personal/DutyRegisterPersonView.js" ],
	DutyRegisterView : [ __ctxPath + "/js/personal/DutyRegisterView.js",
			__ctxPath + "/js/personal/DutyRegisterForm.js" ],
	ErrandsRegisterView : [ __ctxPath + "/js/personal/ErrandsRegisterView.js",
			__ctxPath + "/js/personal/ErrandsRegisterDetail.js",
			__ctxPath + "/js/personal/ErrandsRegisterForm.js" ],
	ErrandsRegisterOutView : [
			__ctxPath + "/js/personal/ErrandsRegisterOutView.js",
			__ctxPath + "/js/personal/ErrandsRegisterOutForm.js" ],
	SysConfigView : [ __ctxPath + "/js/system/SysConfigView.js" ],
	NoticeDetail : [ __ctxPath + "/js/info/NoticeDetail.js" ],
	NewsDetail : [ __ctxPath + "/js/info/NewsDetail.js" ],
	PublicDocumentDetail : [ __ctxPath + "/js/document/PublicDocumentDetail.js" ],
	MailDetail : [ __ctxPath + "/js/communicate/MailDetail.js",
			__ctxPath + "/js/communicate/MailForm.js" ],
	CalendarPlanDetail : [ __ctxPath + "/js/task/CalendarPlanDetail.js" ],
	AppointmentDetail : [ __ctxPath + "/js/task/AppointmentDetail.js" ],
	SearchNews : [ __ctxPath + "/js/search/SearchNews.js",
			__ctxPath + "/js/info/NewsDetail.js" ],
	SearchMail : [ __ctxPath + "/js/search/SearchMail.js",
			__ctxPath + "/ext3/ux/RowExpander.js",
			__ctxPath + "/js/communicate/MailView.js",
			__ctxPath + "/js/communicate/MailForm.js" ],
	SearchNotice : [ __ctxPath + "/js/search/SearchNotice.js" ],
	SearchDocument : [ __ctxPath + "/js/search/SearchDocument.js",
			__ctxPath + "/js/document/PublicDocumentDetail.js" ],
	HireIssueView : [ __ctxPath + "/js/hrm/HireIssueView.js",
			__ctxPath + "/js/hrm/HireIssueForm.js",
			__ctxPath + "/js/hrm/HireIssueCheckWin.js" ],
	ResumeView : [ __ctxPath + "/js/hrm/ResumeView.js",
			__ctxPath + "/js/hrm/ResumeForm.js" ],
	NewsCommentView : [ __ctxPath + "/js/info/NewsCommentView.js",
			__ctxPath + "/ext3/ux/RowExpander.js" ],
	DictionaryView : [ __ctxPath + "/js/system/DictionaryView.js",
			__ctxPath + "/js/system/DictionaryForm.js" ],
	SalaryItemView : [ __ctxPath + "/js/hrm/SalaryItemForm.js",
			__ctxPath + "/js/hrm/SalaryItemView.js" ],
	StandSalaryForm : [ __ctxPath + "/js/hrm/StandSalaryForm.js",
			__ctxPath + "/js/hrm/StandSalaryItemView.js",
			__ctxPath + "/js/selector/SalaryItemSelector.js" ],
	StandSalaryView : [ __ctxPath + "/js/hrm/StandSalaryView.js",
			__ctxPath + "/js/hrm/StandSalaryForm.js",
			__ctxPath + "/js/hrm/StandSalaryItemView.js",
			__ctxPath + "/js/hrm/CheckStandSalaryForm.js",
			__ctxPath + "/js/hrm/CheckStandSalaryItemView.js",
			__ctxPath + "/js/selector/SalaryItemSelector.js" ],
	JobChangeForm : [ __ctxPath + "/js/hrm/JobChangeForm.js",
			__ctxPath + "/js/selector/EmpProfileSelector.js" ],
	JobChangeView : [ __ctxPath + "/js/hrm/JobChangeView.js",
			__ctxPath + "/js/hrm/JobChangeForm.js",
			__ctxPath + "/js/selector/EmpProfileSelector.js",
			__ctxPath + "/js/hrm/CheckJobChangeWin.js" ],
	JobView : [ __ctxPath + "/js/hrm/JobView.js",
			__ctxPath + "/js/hrm/JobForm.js",
			__ctxPath + "/js/hrm/RecoveryJobWin.js" ],
	EmpProfileForm : [ __ctxPath + "/js/hrm/EmpProfileForm.js" ],
	EmpProfileView : [ __ctxPath + "/js/hrm/EmpProfileView.js",
			__ctxPath + "/js/hrm/EmpProfileForm.js",
			__ctxPath + "/js/hrm/CheckEmpProfileForm.js",
			__ctxPath + "/js/hrm/RecoveryProfileWin.js" ],
	SalaryPayoffForm : [ __ctxPath + "/js/hrm/SalaryPayoffForm.js",
			__ctxPath + "/js/selector/EmpProfileSelector.js" ],
	SalaryPayoffView : [ __ctxPath + "/js/hrm/SalaryPayoffForm.js",
			__ctxPath + "/js/selector/EmpProfileSelector.js",
			__ctxPath + "/js/hrm/CheckSalaryPayoffForm.js",
			__ctxPath + "/js/hrm/SalaryPayoffView.js" ],
	ArchiveTypeTempView : [ __ctxPath + "/js/archive/ArchiveTypeTempView.js",
			__ctxPath + "/js/archive/ArchivesTypeForm.js",
			__ctxPath + "/js/archive/ArchTemplateView.js",
			__ctxPath + "/js/archive/ArchTemplateForm.js",
			__ctxPath + "/js/archive/OfficeTemplateView.js" ],
	ArchivesDraftView : [ __ctxPath + "/js/archive/ArchiveTypeTempView.js",
			__ctxPath + "/js/archive/ArchivesDraftView.js",
			__ctxPath + "/js/archive/ArchivesDocForm.js",
			__ctxPath + "/js/archive/ArchTemplateView.js",
			__ctxPath + "/js/archive/ArchTemplateSelector.js",
			__ctxPath + "/js/archive/OfficeTemplateView.js",
			__ctxPath + "/js/archive/ArchivesDocHistoryWin.js" ],
	PersonalSalaryView : [ __ctxPath + "/js/personal/PersonalSalaryView.js",
			__ctxPath + "/ext3/ux/RowExpander.js" ],
	ArchRecTypeView : [ __ctxPath + "/js/archive/ArchRecTypeView.js",
			__ctxPath + "/js/archive/ArchRecTypeForm.js" ],
	ArchivesRecView : [ __ctxPath + "/js/archive/ArchivesRecView.js",
			__ctxPath + "/js/archive/ArchivesRecForm.js",
			__ctxPath + "/js/archive/ArchivesDetail.js" ],
	ArchivesHandleView : [ __ctxPath + "/js/archive/ArchivesHandleView.js",
			__ctxPath + "/js/archive/ArchivesHandleForm.js" ],
	LeaderReadView : [ __ctxPath + "/js/archive/LeaderReadView.js",
			__ctxPath + "/js/archive/LeaderReadForm.js" ],
	ArchDispatchView : [ __ctxPath + "/js/archive/ArchDispatchView.js",
			__ctxPath + "/js/archive/ArchDistributeForm.js" ],
	ArchUndertakeView : [ __ctxPath + "/js/archive/ArchUndertakeView.js",
			__ctxPath + "/js/archive/ArchUndertakeForm.js" ],
	ArchReadView : [ __ctxPath + "/js/archive/ArchReadView.js",
			__ctxPath + "/js/archive/ArchReadForm.js" ],
	ArchivesRecCtrlView : [ __ctxPath + "/js/archive/ArchivesRecCtrlView.js",
			__ctxPath + "/js/archive/ArchivesDetail.js",
			__ctxPath + "/js/archive/ArchHastenForm.js" ],
	ArchivesDraftManage : [ __ctxPath + "/js/archive/ArchivesDraftManage.js",
			__ctxPath + "/js/archive/ArchivesDraftView.js",
			__ctxPath + "/js/archive/ArchivesDraftWin.js",
			__ctxPath + "/js/archive/ArchTemplateView.js",
			__ctxPath + "/js/archive/ArchTemplateSelector.js",
			__ctxPath + "/js/archive/ArchivesDocForm.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js",
			__ctxPath + "/js/archive/ArchivesDocHistoryWin.js" ],
	ArchivesIssueSearch : [ __ctxPath + "/js/archive/ArchivesIssueSearch.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js" ],
	ArchivesIssueProof : [ __ctxPath + "/js/archive/ArchivesIssueProof.js",
			__ctxPath + "/js/archive/ArchivesDocWin.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js" ],
	ArchivesIssueAudit : [ __ctxPath + "/js/archive/ArchivesIssueAudit.js",
			__ctxPath + "/js/archive/ArchivesDraftWin.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js",
			__ctxPath + "/js/archive/ArchivesDocHistoryWin.js" ],
	ArchivesIssueLead : [ __ctxPath + "/js/archive/ArchivesIssueLead.js",
			__ctxPath + "/js/archive/ArchivesDraftWin.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js",
			__ctxPath + "/js/archive/ArchivesDocHistoryWin.js" ],
	ArchivesIssueCharge : [ __ctxPath + "/js/archive/ArchivesIssueCharge.js",
			__ctxPath + "/js/archive/ArchivesDraftWin.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js",
			__ctxPath + "/js/archive/ArchivesDocHistoryWin.js" ],
	ArchivesDocument : [ __ctxPath + "/js/archive/ArchivesDocument.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js" ],
	ArchivesIssueMonitor : [ __ctxPath + "/js/archive/ArchivesIssueMonitor.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js",
			__ctxPath + "/js/archive/ArchHastenForm.js" ],
	ArchivesIssueManage : [ __ctxPath + "/js/archive/ArchivesIssueManage.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js" ],
	DocHistoryView : [ __ctxPath + "/js/archive/DocHistoryView.js" ],
	ArchFlowConfView : [ __ctxPath + "/js/archive/ArchFlowConfView.js",
			__ctxPath + "/js/selector/FlowSelector.js" ],
	ArchivesSignView : [ __ctxPath + "/js/archive/ArchivesSignView.js",
			__ctxPath + "/js/archive/ArchivesRecForm.js",
			__ctxPath + "/js/archive/ArchivesDetailWin.js" ],
	SystemLogView : [ __ctxPath + "/js/system/SystemLogView.js" ],
	MyProcessRunView : [ __ctxPath + "/js/flow/MyProcessRunView.js",
			__ctxPath + "/js/flow/ProcessRunDetail.js" ],
	PersonalTipsView : [ __ctxPath + "/js/info/PersonalTipsView.js" ],
	BudgetView : [ __ctxPath + "/js/budget/BudgetView.js",
	        			__ctxPath + "/js/budget/BudgetForm.js",
	        			__ctxPath + "/js/budget/BudgetFormView.js",
	        			__ctxPath + "/js/budget/BudgetItemWin.js" ],
	BudgetStatusView : [ __ctxPath + "/js/budget/BudgetStatusView.js",
        			__ctxPath + "/js/budget/BudgetForm.js",
        			__ctxPath + "/js/budget/BudgetFormView.js",
        			__ctxPath + "/js/budget/BudgetItemWin.js" ],
	BudgetAlarmView : [ __ctxPath + "/js/budget/BudgetAlarmView.js",
	        			__ctxPath + "/js/budget/BudgetFormView.js"],
	RealExecutionView : [ __ctxPath + "/js/budget/RealExecutionView.js",
    			__ctxPath + "/js/budget/RealExecutionForm.js"],			
	TaskSchedulerView : [ __ctxPath + "/js/scheduler/TaskSchedulerView.js",
	 __ctxPath + "/js/scheduler/TaskSchedulerForm.js"
	],
	RewardsPunishmentsView : [ __ctxPath + "/js/rewardsPunishments/RewardsPunishmentsView.js",
             	 __ctxPath + "/js/rewardsPunishments/RewardsPunishmentsForm.js"],
 	IncomeTaxView : [ __ctxPath + "/js/incomeTax/IncomeTaxView.js",
 	                 __ctxPath + "/js/incomeTax/IncomeTaxItemForm.js"],
	DraftHrPaPerformanceindexView : [__ctxPath + "/js/kpi/DraftHrPaPerformanceindexView.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexForm.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreView.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreForm1.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreForm2.js"],
	InAuditHrPaPerformanceindexView : [__ctxPath + "/js/kpi/InAuditHrPaPerformanceindexView.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexFormView.js"],
	PublishHrPaPerformanceindexView : [__ctxPath + "/js/kpi/PublishHrPaPerformanceindexView.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexForm.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreView.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreForm1.js",
			__ctxPath + "/js/kpi/HrPaPerformanceindexscoreForm2.js"],
	DraftHrPaAssessmentcriteriaView : [__ctxPath + "/js/kpi/DraftHrPaAssessmentcriteriaView.js",
			__ctxPath + "/js/kpi/HrPaAssessmentcriteriaForm.js"],
	InAuditHrPaAssessmentcriteriaView : [__ctxPath + "/js/kpi/InAuditHrPaAssessmentcriteriaView.js",
			__ctxPath + "/js/kpi/HrPaAssessmentcriteriaFormView.js"],
	PublishHrPaAssessmentcriteriaView : [__ctxPath + "/js/kpi/PublishHrPaAssessmentcriteriaView.js",
			__ctxPath + "/js/kpi/HrPaAssessmentcriteriaForm.js"],
	DraftHrPaKpipbcView : [__ctxPath + "/js/kpi/DraftHrPaKpipbcView.js",
			__ctxPath + "/js/kpi/HrPaKpipbcForm.js",
			__ctxPath + "/js/kpi/HrPaPiView.js"],
	InAuditHrPaKpipbcView : [__ctxPath + "/js/kpi/InAuditHrPaKpipbcView.js",
			__ctxPath + "/js/kpi/HrPaKpipbcFormView.js"],
	PublishHrPaKpipbcView : [__ctxPath + "/js/kpi/PublishHrPaKpipbcView.js",
			__ctxPath + "/js/kpi/HrPaKpipbcForm.js",
			__ctxPath + "/js/kpi/HrPaPiView.js",
			__ctxPath + "/js/kpi/HrPaKpipbcFormView.js"],
	CriteriaTargetView : [__ctxPath + "/js/kpi/CriteriaTargetView.js"],
	CriteriaReachedView : [__ctxPath + "/js/kpi/CriteriaReachedView.js"],
	HrPaKpiPBC2UserView : [__ctxPath + "/js/kpi/HrPaKpiPBC2UserView.js",
			__ctxPath + "/js/kpi/AuthorizePbcView.js",
			__ctxPath + "/js/kpi/AuthorizePbcForm.js"],
	GradeAuthorizePbcView : [__ctxPath + "/js/kpi/GradeAuthorizePbcView.js",
			__ctxPath + "/js/kpi/GradeAuthorizePbcForm.js"],
	RewardsPunishmentsTypeView : [ __ctxPath + "/js/rewardsPunishments/RewardsPunishmentsTypeView.js",
			                    __ctxPath + "/js/rewardsPunishments/RewardsPunishmentsTypeForm.js" ],
	JobSalaryRelationView : [ __ctxPath + "/js/hrm/JobSalaryRelationView.js",
                          __ctxPath + "/js/hrm/JobSalaryRelationForm.js" ],		                    
	HrAnlyReportView:[__ctxPath + "/js/hrm/HrAnlyReportView.js"],
	ResultTotalScoreView : [__ctxPath + "/js/kpi/ResultTotalScoreView.js",
			__ctxPath + "/ext3/ux/RowExpander.js"],
	ShowHistoryView : [__ctxPath + "/js/kpi/ShowHistoryView.js",
			__ctxPath + "/ext3/ux/RowExpander.js"],
	HrPromApplyView	: [__ctxPath + "/js/hrm/HrPromApplyView.js",
			__ctxPath + "/js/hrm/HrPromApplyForm.js",
			__ctxPath + "/js/hrm/HrPromAssessmentForm.js"],
	HrPromApplyStatusView : [__ctxPath + "/js/hrm/HrPromApplyStatusView.js",
			__ctxPath + "/js/hrm/HrPromApplyPreview.js"],
	HrPromAssessmentStatusView : [__ctxPath + "/js/hrm/HrPromAssessmentStatusView.js",
			__ctxPath + "/js/hrm/HrPromAssessmentPreview.js"],
	HrTZAnlyReportView:[__ctxPath + "/js/hrm/HrTZAnlyReportView.js"],
	ExportSalaryView : [__ctxPath + "/js/hrm/ExportSalaryView.js",__ctxPath + "/js/hrm/ExportSalaryItemForm.js"]		
				                    
};