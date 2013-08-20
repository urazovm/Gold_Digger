package com.billme.widget;

import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.MainService;
import com.billme.ui.R;
import com.billme.util.FileUtil;
import com.futurePayment.model.CommentInfo;
import com.futurePayment.model.ImageHelper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySocietyAdapter extends BaseAdapter {
	private Context context = null;
	private ArrayList<CommentInfo> list;
	private LayoutInflater inflater = null;

	/**
	 * 
	 * @param context
	 *            the context of current activity
	 * @param list
	 *            every hashmap have five
	 *            keys:"head":int/string,"name":string,"enterprise"
	 *            :string,"content"
	 *            :string,"photo":string,"money":double,"time":string
	 */
	public MySocietyAdapter(Context context,
			ArrayList<CommentInfo> list) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		CommentInfo ci = list.get(position);
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_society_list_item, null);
		}
		viewHolder.head = (ImageView) convertView
				.findViewById(R.id.iv_societylistitem_head);
		viewHolder.name = (TextView) convertView
				.findViewById(R.id.tv_societylistitem_name);
		viewHolder.enterprise = (TextView) convertView
				.findViewById(R.id.tv_societylistitem_enterprise);
		viewHolder.content = (TextView) convertView
				.findViewById(R.id.tv_societylistitem_content);
		viewHolder.photo = (ImageView) convertView
				.findViewById(R.id.iv_societylistitem_photo);
		viewHolder.money = (TextView) convertView
				.findViewById(R.id.tv_societylistitem_money);
		viewHolder.time = (TextView) convertView
				.findViewById(R.id.tv_societylistitem_time);
		if (ci != null) {			
			viewHolder.name.setText(ci.getName());
			viewHolder.enterprise.setText(ci.getEnterpriseName());
			viewHolder.content.setText(ci.getContent());
			viewHolder.money.setText("����������" + ci.getMoney() + "Ԫ");
			viewHolder.time.setText(ci.getTime());
			
			String str = (String) ci.getHead();
				if (str.startsWith("http://")) {
					// �������ȡ
					ImageHelper imageHelper = MainService.getImageHelper();
					Drawable temp = imageHelper.loadDrawable(str,
							viewHolder.head);
					if (temp != null)
						viewHolder.head.setImageDrawable(temp);
				} 
//				else {
//					// ��sd����ȡ
//					FileUtil fu = new FileUtil(MainService.getUser().getName());
//					viewHolder.icon.setImageDrawable(fu
//							.readImageFromSD((String) map.get("icon")));
//				}
//				viewHolder.text.setText((String) map.get("text"));
//				viewHolder.end.setImageDrawable(context.getResources()
//						.getDrawable((Integer) (map.get("end"))));				
				String str1 = (String) ci.getPhoto();
				if (str.startsWith("http://")) {
					// �������ȡ
					ImageHelper imageHelper = MainService.getImageHelper();
					Drawable temp = imageHelper.loadDrawable(str1,
							viewHolder.photo);
					if (temp != null)
						viewHolder.photo.setImageDrawable(temp);
				} 
		}
		return convertView;
	}

	private class ViewHolder {
		public ImageView head = null;
		public TextView name = null;
		public TextView enterprise = null;
		public TextView content = null;
		public ImageView photo = null;
		public TextView money = null;
		public TextView time = null;
	}

}