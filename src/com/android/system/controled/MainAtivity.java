package com.android.system.controled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class MainAtivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		// �豸��ȫ������� 2.2֮ǰ�İ汾��û�ж��Ⱪ¶�� ֻ��ͨ�����似����ȡ
//		DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//		// ����Ȩ��
//		ComponentName componentName = new ComponentName(MainAtivity.this, MainDeviceAdminReceiver.class);
//		// �жϸ�����Ƿ���ϵͳ����Ա��Ȩ��
//		boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
//		if (isAdminActive) {

			startActivity(new Intent(Settings.ACTION_SETTINGS));

//			// devicePolicyManager.lockNow(); // ����
//			// devicePolicyManager.resetPassword("123", 0); // ������������
//			// devicePolicyManager.wipeData(0); �ָ��������� (�����Ҳ�Ҫ������ϲ���) ģ������֧�ָò���
//		} else {
//			Intent intent = new Intent();
//			// ָ����������
//			intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//			// ָ�����ĸ������Ȩ
//			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//			startActivity(intent);
//		}

		finish();
	}

}
