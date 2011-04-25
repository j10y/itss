package com.digitalchina.info.appframework.metadata.service;
import java.io.FileWriter;   
import java.io.IOException;   
  
import org.dom4j.Document;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
import org.dom4j.io.OutputFormat;   
import org.dom4j.io.XMLWriter;  

public class CreateXML {
	public static Document getDocument(){   
        Document document = DocumentHelper.createDocument();   
        //生成一个接点   
        Element root = document.addElement("root");   
        //生成root的一个接点   
        Element category = root.addElement("category");   
        //生产category的一个接点   
        Element id = category.addElement("id");   
        //生成id里面的参数值   
        id.addAttribute("name", "id");   
        //生成id里面的值   
        id.addText("1");   
        return document;   
    }   
       
    /**  
     * 写入xml文件地址  
     * @param document 所属要写入的内容  
     * @param outFile 文件存放的地址  
     */  
    public static void writeDocument(Document document, String outFile){   
        try{   
            //读取文件   
            FileWriter fileWriter = new FileWriter(outFile);   
            //设置文件编码   
            OutputFormat xmlFormat = new OutputFormat();   
            xmlFormat.setEncoding("GB2312");   
            //创建写文件方法   
            XMLWriter xmlWriter = new XMLWriter(fileWriter,xmlFormat);   
            //写入文件   
            xmlWriter.write(document);   
            //关闭   
            xmlWriter.close();   
        }catch(IOException e){   
            System.out.println("文件没有找到");   
            e.printStackTrace();   
        }   
    }   
       
    public static void main(String[] args){   
        if (args.length == 1){   
            System.out.println("请输入文件存放地址");   
            return;   
        }   
        CreateXML.writeDocument(CreateXML.getDocument(), "d:/db/ax.xml");   
    }   

}
