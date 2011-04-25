package com.digitalchina.info.framework.util.asm;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.WordUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.digitalchina.info.framework.util.Assistant;


/**
 * POJO���ɹ���
 * @author courser.tijichen
 */
public class POBuildUtil extends ClassLoader implements Opcodes {
	
	/**
	 * ���ݴ����������CLASS�ļ�
	 *
	 * @param ����
	 * @param ����·��
	 * @param ��������
	 * @return Class
	 */
	public  Class build(String clsname,String savepath,Collection properties)
	{
		Class cls = null;
		try
		{
			//String baseObjectInternalName = Type.getType(com.digitalchina.info.framework.dao.BaseObject.class).getInternalName();
			/*ClassPrinter cp=new ClassPrinter();
			ClassReader reader = new ClassReader("com.digitalchina.info.framework.dao.BaseObject");
			reader.accept(cp, 0);)*/
			String classname = BuildUtil.transferClassName(clsname);
			ClassWriter cw = new ClassWriter(false);   
			//�������캯�� //java/lang/Object  //com/digitalchina/info/framework/dao/BaseObject
			cw.visit(V1_1, ACC_PUBLIC, classname, null, "java/lang/Object", null);   
			MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);   
			mw.visitVarInsn(ALOAD, 0);   
			mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");   
			mw.visitInsn(RETURN);   
			mw.visitMaxs(1, 1);   
			mw.visitEnd();
			
			BuildProperty property = null;
			String propertytype = null;
			String propertyname = null;;
			//��������
			Iterator iterator = properties.iterator();
			while (iterator.hasNext())
			{
				//�������Զ�Ӧ�������
				property = (BuildProperty)iterator.next();
				propertytype = BuildUtil.transferClassName(property.getType());
				propertyname = WordUtils.capitalize(property.getName());
				cw.visitField(ACC_PRIVATE, property.getName(), "L"+propertytype+";", null, null).visitEnd();
				//����get����
			    mw = cw.visitMethod(ACC_PUBLIC, "get"+propertyname, "()L"+propertytype+";", null, 
	    		null); 
			    mw.visitCode(); 
			    mw.visitVarInsn(ALOAD, 0); 
			    mw 
	    		.visitFieldInsn(GETFIELD, classname, property.getName(), 
	    		"L"+propertytype+";"); 
			    mw.visitInsn(ARETURN); 
			    mw.visitMaxs(1, 1); 
			    mw.visitEnd(); 
			    //����set����
			    mw = cw.visitMethod(ACC_PUBLIC, "set"+propertyname, "(L"+propertytype+";)V", 
			    		null, null); 
			    mw.visitCode(); 
			    mw.visitVarInsn(ALOAD, 0); 
			    mw.visitVarInsn(ALOAD, 1); 
			    mw 
	    		.visitFieldInsn(PUTFIELD, classname, property.getName(), 
	    		"L"+propertytype+";"); 
			    mw.visitMaxs(2, 2); 
			    mw.visitInsn(RETURN); 
			    mw.visitEnd(); 
			}
			
			cw.visitEnd();

		    byte[] code = cw.toByteArray(); 
			if (savepath!=null)
			{  
				Assistant.createNewFile(savepath);
			    FileOutputStream fos = new FileOutputStream(savepath);   
			    fos.write(code);   
			    fos.close();   
			}
			cls = this.defineClass(clsname, code, 0, code.length);
			return cls;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return cls;
	}
}
