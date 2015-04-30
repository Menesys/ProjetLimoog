package com.example.projetlimoog.activity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;

public class CustomAdapterBilan extends BaseAdapter {

	private Context mContext;
	private List<Competence> listeCompetence;
	private LayoutInflater mInflater;
	
	public CustomAdapterBilan(List<Competence> licomp, Context c) {
		mContext = c;
		listeCompetence = licomp;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return listeCompetence.size();
	}

	@Override
	public Object getItem(int position) {
		return listeCompetence.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		if (convertView == null) {
			layoutItem = (LinearLayout) mInflater.inflate(
					R.layout.bilanboxlayout, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}
		
		TextView nomComp = (TextView)layoutItem.findViewById(R.id.nomComp1);
		nomComp.setText(listeCompetence.get(position).getcNom().toString());
		
		TextView tb = (TextView)layoutItem.findViewById(R.id.tb2);
		TextView b = (TextView)layoutItem.findViewById(R.id.textView3);
		TextView p = (TextView)layoutItem.findViewById(R.id.textView4);
		TextView na = (TextView)layoutItem.findViewById(R.id.textView5);
		
		return layoutItem;
	}

}
