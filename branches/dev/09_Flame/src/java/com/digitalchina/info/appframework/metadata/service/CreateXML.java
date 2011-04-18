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
        //����һ���ӵ�   
        Element root = document.addElement("root");   
        //����root��һ���ӵ�   
        Element category = root.addElement("category");   
        //����category��һ���ӵ�   
        Element id = category.addElement("id");   
        //����id����Ĳ���ֵ   
        id.addAttribute("name", "id");   
        //����id�����ֵ   
        id.addText("1");   
        return document;   
    }   
       
    /**  
     * д��xml�ļ���ַ  
     * @param document ����Ҫд�������  
     * @param outFile �ļ���ŵĵ�ַ  
     */  
    public static void writeDocument(Document document, String outFile){   
        try{   
            //��ȡ�ļ�   
            FileWriter fileWriter = new FileWriter(outFile);   
            //�����ļ�����   
            OutputFormat xmlFormat = new OutputFormat();   
            xmlFormat.setEncoding("GB2312");   
            //����д�ļ�����   
            XMLWriter xmlWriter = new XMLWriter(fileWriter,xmlFormat);   
            //д���ļ�   
            xmlWriter.write(document);   
            //�ر�   
            xmlWriter.close();   
        }catch(IOException e){   
            System.out.println("�ļ�û���ҵ�");   
            e.printStackTrace();   
        }   
    }   
       
    public static void main(String[] args){   
        if (args.length == 1){   
            System.out.println("�������ļ���ŵ�ַ");   
            return;   
        }   
        CreateXML.writeDocument(CreateXML.getDocument(), "d:/db/ax.xml");   
    }   

}
