<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<html>
  <head>
      <style>
      .panel{
          width:500;
          height:auto;
      }
      fieldset{
          border:#f25930 solid 1px;
          font-size:12px;
          color:#f25930;
      }
      fieldset p{
          text-align:left;
          margin:5px 10px;
          padding:0;
      }
      fieldset legend{
          font-size:14px;
          font-weight:bold;
          color:#f25930;
      }
      </style>

  </head>
  <body bgcolor="">
    <table width="100%" cellpadding="0" cellspacing="0" height="100%" border="0" align="center">
    <tr>
    <td align="center" valign="middle" style="">
        <%--<div><img src="${pageContext.request.contextPath}/images/desktop.png"/></div>--%>
        <div class="panel">
            <fieldset>
                <legend><b>统一用户管理系统</b></legend>
                <img vspace="20" src="${pageContext.request.contextPath}/images/desktop.png" />
            </fieldset>
            <br>
            <fieldset>
                <legend><b>Introduction:</b></legend>
                <p id="show_desc">
                	统一用户管理系统 V2.0
                </p>
            </fieldset>
        </div>
    </td>
    </tr>
    </table>
  </body>
</html>
