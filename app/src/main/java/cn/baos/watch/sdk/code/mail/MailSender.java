package cn.baos.watch.sdk.code.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/* JADX INFO: loaded from: classes.dex */
public class MailSender {
    public void sendTextMail(MailInfo mailInfo) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(mailInfo.getProperties(), mailInfo.isValidate() ? new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()) : null));
        mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getToAddress()));
        mimeMessage.setSubject(mailInfo.getSubject());
        mimeMessage.setSentDate(new Date());
        mimeMessage.setText(mailInfo.getContent());
        try {
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendHtmlMail(MailInfo mailInfo) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(mailInfo.getProperties(), mailInfo.isValidate() ? new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()) : null));
        mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getToAddress()));
        mimeMessage.setSubject(mailInfo.getSubject());
        mimeMessage.setSentDate(new Date());
        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        mimeMultipart.addBodyPart(mimeBodyPart);
        mimeMessage.setContent(mimeMultipart);
        Transport.send(mimeMessage);
    }

    public void sendFileMail(MailInfo mailInfo, File file) throws MessagingException, UnsupportedEncodingException {
        Transport.send(createAttachmentMail(mailInfo, file));
    }

    private Message createAttachmentMail(final MailInfo mailInfo, File file) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(mailInfo.getProperties(), new Authenticator() { // from class: cn.baos.watch.sdk.code.mail.MailSender.1
            @Override // javax.mail.Authenticator
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailInfo.getUserName(), mailInfo.getPassword());
            }
        }));
        mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getToAddress()));
        mimeMessage.setSubject(mailInfo.getSubject());
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailInfo.getContent(), "text/html;charset=UTF-8");
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(mimeBodyPart);
        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
        DataHandler dataHandler = new DataHandler(new FileDataSource(file));
        mimeBodyPart2.setDataHandler(dataHandler);
        mimeBodyPart2.setFileName(MimeUtility.encodeText(dataHandler.getName()));
        mimeMultipart.addBodyPart(mimeBodyPart2);
        mimeMultipart.setSubType("mixed");
        mimeMessage.setContent(mimeMultipart);
        mimeMessage.saveChanges();
        return mimeMessage;
    }
}
