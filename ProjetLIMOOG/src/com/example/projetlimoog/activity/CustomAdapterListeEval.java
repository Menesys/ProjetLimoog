package com.example.projetlimoog.activity;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Evaluation;

public class CustomAdapterListeEval extends BaseAdapter {

	private Context mContext;
	private List<Evaluation> listeEval;
	private LayoutInflater mInflater;

	public CustomAdapterListeEval(List<Evaluation> liev, Context c) {
		mContext = c;
		listeEval = liev;
		mInflater = LayoutInflater.from(mContext);
		Collections.sort(listeEval);
	}

	@Override
	public int getCount() {
		return listeEval.size();
	}

	@Override
	public Object getItem(int position) {
		return listeEval.get(position);
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
					R.layout.listeevaldate, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}
		
		TextView nomEval = (TextView)layoutItem.findViewById(R.id.nomEval1);
		TextView dateEval = (TextView)layoutItem.findViewById(R.id.dateEval2);
		
		nomEval.setText(listeEval.get(position).getsNom().toString());
		dateEval.setText(listeEval.get(position).giveNiceDate().toString());	
		
		return layoutItem;
	}
}
