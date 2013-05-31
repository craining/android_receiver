package com.alipay.safe.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactsUtil {

	// public static String getNameFormNum(Context con, String num) {
	// String name = num;
	// // 获得所有的联系人
	// Cursor cur = con.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
	// null);
	// // 循环遍历
	// if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
	// int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
	// int displayNameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
	// do {
	// // 获得联系人的ID号
	// String contactId = cur.getString(idColumn);
	// // 获得联系人姓名
	// String disPlayName = cur.getString(displayNameColumn);
	// // 查看该联系人有多少个电话号码。如果没有这返回值为0
	// int phoneCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
	// if (phoneCount > 0) {
	// // 获得联系人的电话号码
	// Cursor phones = con.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	// null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
	// if (phones != null && phones.getCount() > 0) {
	// phones.moveToFirst();
	// do {
	// // 遍历所有的电话号码
	// String phoneNumber =
	// phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	// if (phoneNumber.contains(num) || num.contains(phoneNumber)) {
	// return disPlayName + ":" + name;
	// }
	// } while (phones.moveToNext());
	// } else {
	// LogOut.e("Contact", "contact is null");
	// }
	// }
	// } while (cur.moveToNext());
	// }
	//
	// return name;
	// }

	/**
	 * 根据号码获得联系人姓名
	 * 
	 * @Description:
	 * @param con
	 * @param number
	 * @return
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-29
	 */
	public static String getNameFromPhone(Context con, String number) {
		String name = number;
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
		Cursor cursor = con.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String newNumber = "";
			do {
				newNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				if (newNumber.contains(number) || number.contains(newNumber)) {
					name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)) + ":" + newNumber;
					break;
				}
			} while (cursor.moveToNext());
			cursor.close();
		}

		return name;
	}

	/**
	 * 获得所有联系人
	 * @Description:
	 * @param con
	 * @return
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-5-31
	 */
	public static ArrayList<ContactBean> getAllContacts(Context con) {
		ArrayList<ContactBean> arrayContacts = new ArrayList<ContactBean>();
		ContactBean c;
		
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
		Cursor cursor = con.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			do {
				c = new ContactBean();
				c.name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
				c.number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				arrayContacts.add(c);

			} while (cursor.moveToNext());
			cursor.close();
		}

		return arrayContacts;
	}

 
	/**
	 * 联系人封装
	 * @Description:
	 * @author:zhuanggy  
	 * @see:   
	 * @since:      
	 * @copyright © 35.com
	 * @Date:2013-5-31
	 */
	public static class ContactBean {
		public String name;
		public String number;
	}
}
