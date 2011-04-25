package fastSign.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HtmlMessageSender {

	String protocol = "smtp";
	String from = "guangshunan0813@163.com";
	String to = "guangshunan0813@163.com";
	String subject = "test";
	String body = "test!!!";
	
	public static void main(String[] args) throws Exception{
		
		String server = "smtp.163.com";
		String user = "guangshunan0813";
		String password = "4170061";
		HtmlMessageSender sender = new HtmlMessageSender();
		Session session = sender.createSession();
		MimeMessage message = sender.createMessage(session);
		Transport transport = session.getTransport();
		transport.connect(server, user, password);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}
	/**
	 * 创建session
	 * @return
	 */
	public Session createSession(){
		
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", protocol);
		props.setProperty("mail.smtp.auth", "true");//设置了协议和认证
		Session session = Session.getInstance(props);
		session.setDebug(true);
		return session;
	}
	/**
	 * 创建邮件内容
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public MimeMessage createMessage(Session session )throws Exception{
		
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(from));
		mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(body);
		mimeMessage.saveChanges();
		return mimeMessage;
	}
}
