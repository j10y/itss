/**
   ��ܶ฽���ϴ���� author: sujs���ս�����
*/

 //ɾ������
  function getRemoveFile(value,panelId,clazz) {
			Ext.Ajax.request({
						url : webContext
								+ '/extjs/dataAction?method=remove',
						params : {
							clazz : clazz,
							id : value
						},
						timeout : 100000,
						success : function(response, options) {
	                           var spanvale="su_"+ value;
                               document.getElementById(spanvale).outerHTML="";     
						},
						scope : this
					}, this);
         
 }
// ����ϴ����
UpDownLoadFile = Ext.extend(Ext.util.Observable, {
	// �ϴ�����
	getUpLoadFileSu : function() {
		// �ϴ�����
		// alert(this.nowtimesu);
		// alert(this.EntityClazz);
		this.dialog = new Ext.ux.UploadDialog.Dialog({
			url : webContext + '/extjs/systemFileUpload?nowtime='
					+ this.nowtimesu + '&columnId=' + this.columnId,
			// permitted_extensions : ['out', 'xml'],
			reset_on_hide : false,
			draggable : true,
			width : 400,
			height : 180,
			allow_close_on_upload : false,
			upload_autostart : false
		});
		this.dialog.height = 180;
		this.dialog.width = 450;
		this.dialog.on("uploadsuccess", this.getUploadSucceSu, this);
		this.dialog.show('show-button');

	},
	// �ϴ��ɹ�
	getUploadSucceSu : function(dialog, filename, result, record) {          
		if (result.success == true) {
         var tempId= result.fileIdTemp;
         var fileTempName=result.fileNameTemp;
         var hiddenPanel=this.hiddenId;
         var columnId=this.columnId;
         var clazz=this.className;
         var addpanel= document.getElementById(hiddenPanel).innerText; 
           if (addpanel != '') {  
		         var addpanelOld = document.getElementById(hiddenPanel).innerHTML;
                 var childlength=document.getElementById(hiddenPanel).childNodes.length+1;
		         var panelValue = addpanelOld
		              + '<span id =su_'+tempId+'><img src='+webContext+'/images/other/file.gif >'
                      + '<a href='+webContext+'/fileDown.do?methodCall=downloadFile&id='+tempId+'&columnId='+columnId+'>'
                      +fileTempName
                      + '</a><img src='+webContext+'/images/other/suremove.gif onClick=getRemoveFile('
		              + tempId+','+hiddenPanel+',"'+clazz+'") alt="ɾ������" style="cursor:hand;margin:-1 0">';
                     if(childlength%2!=0){
                         panelValue=panelValue+'<br></span>';  
                      }else{
                         panelValue=panelValue+'</span>';
                      }
                      document.getElementById(hiddenPanel).innerHTML=panelValue;         
		      } else {
		            document.getElementById(hiddenPanel).innerHTML ='<span id =su_'+tempId+'><img src='+webContext+'/images/other/file.gif >'+ 
                      '<a href='+webContext+'/fileDown.do?methodCall=downloadFile&id='+tempId+'&columnId='+columnId+'>'
                      +fileTempName+ '</a><img src='+webContext+'/images/other/suremove.gif onClick=getRemoveFile('
                      + tempId+','+hiddenPanel+',"'+clazz+'") alt="ɾ������" style="cursor:hand;margin:-1 0"></span>';
		      }   
		  dialog.close();     
      }		                           
	},
    // �ϴ����ظ��������
	getUpDownLoadFileSu : function(nowtime,columnId, className,hiddenId) {
		this.nowtimesu = nowtime;
		this.columnId = columnId;
		this.className = className;
        this.hiddenId=hiddenId;
        this.dialog = new Ext.ux.UploadDialog.Dialog({
                  url : webContext + '/extjs/systemFileUpload?nowtime='
                                      + this.nowtimesu + '&columnId=' + this.columnId,
                  // permitted_extensions : ['out', 'xml'],
                  reset_on_hide : false,
                  draggable : true,
                  width : 400,
                  height : 180,
                  allow_close_on_upload : false,
                  upload_autostart : false
        });
        this.dialog.height = 180;
        this.dialog.width = 450;
        this.dialog.on("uploadsuccess", this.getUploadSucceSu, this);
        this.dialog.show('show-button');
	}
	
});
