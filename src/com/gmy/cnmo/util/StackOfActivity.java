package com.gmy.cnmo.util;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;

public class StackOfActivity extends Application {
	// ����list��������ÿһ��activity�ǹؼ�
	private List<Activity> mList = new LinkedList<Activity>();
	// Ϊ��ʵ��ÿ��ʹ�ø���ʱ�������µĶ���������ľ�̬����
	private static StackOfActivity instance;

	// ���췽��
	private StackOfActivity() {
	}

	// ʵ����һ��
	public synchronized static StackOfActivity getInstance() {
		if (null == instance) {
			instance = new StackOfActivity();
		}
		return instance;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	/**
	 * ��õ�ǰactivity
	 * 
	 * @return
	 */
	public Activity currentActivity() {
		boolean bool = mList.isEmpty();
		Activity localActivity = null;
		if (!bool)
			localActivity = (Activity) mList.get(mList.size() - 1);
		return localActivity;
	}

	// �ر�ÿһ��list�ڵ�activity
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	// ɱ����
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
