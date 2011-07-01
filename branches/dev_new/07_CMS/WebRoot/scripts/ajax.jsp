<% 

	//根据规则轮训FTP目录
	String _html= "";
	_html += "<li>";
	_html += "<a href='ftp://172.16.100.26/cms/u/cms/www/201106/29142153reqq.jpg'>";
//	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "</a>";
	_html += "</li>";
	_html += "<li>";
	_html += "<a href='ftp://172.16.100.26/cms/u/cms/www/201106/29123427t5v9.jpg'>";
//	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "</a>";
	_html += "</li>";
	_html += "<a href='images/111.jpg'>";
//	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "<img title='A title for this' longdesc='This is a nice, and incredibly descriptive, description of the image 10.jpg' src='images/thumbs/1252795702d0c45e19e36e09889b8e6bc86a9ce729.jpg' class='image'>";
	_html += "</a>";
	_html += "</li>";
	response.setContentType("text/html;charset=UTF-8");
	response.setCharacterEncoding("utf-8");
	out.println(_html);
	out.flush();
	//out.close();
%>
  