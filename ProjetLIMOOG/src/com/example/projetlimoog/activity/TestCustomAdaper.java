package com.example.projetlimoog.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projetlimoog.R;
import com.example.projetlimoog.model.Competence;
import com.example.projetlimoog.model.Evaluation;
import com.example.projetlimoog.model.Usager;

public class TestCustomAdaper extends BaseAdapter {
	private List<Competence> list;
	private Map<Competence, TestListener> map=new HashMap<Competence, TestListener>();
	private LayoutInflater inflater;
	private Evaluation evaluation;
	public TestCustomAdaper(Context context, List<Competence> comps,Evaluation e,Usager u) {
		super();
		evaluation=e;
		inflater = LayoutInflater.from(context);
		list=comps;
		for(Competence c:comps){
			map.put(c, new TestListener(evaluation,c,u));
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int id) {
		return list.get(id);
	}

	@Override
	public long getItemId(int id) {
		return list.get(id).getiId();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		
		if (convertView == null) {
			layoutItem = (LinearLayout) inflater.inflate(
					R.layout.evaluercheckboxes, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		//TextView
		TextView compName = (TextView) layoutItem.findViewById(R.id.compName);
		compName.setText(list.get(pos).getcNom().toString());

		//Radiogroup
		RadioGroup group = (RadioGroup) layoutItem.findViewById(R.id.radioGroup1);
		TestListener l=map.get(list.get(pos));
		group.setOnCheckedChangeListener(l);
		l.setGroup(group);
		group.clearCheck();
		l.updateCheck();
		return layoutItem;
	}
//
//	public void updateUser(Usager user) {
//		for(Competence c:list){
//			map.get(c).setUser(user);
//		}
//		
//	}
//
//	public void clearCheck() {
//		for(Competence c:list){
//			map.get(c).clearCheck();
//		}
//		
//	}
//
//	public void updateCheck() {
//		for(Competence c:list){
//			map.get(c).updateCheck();
//		}
//		
//	}

}
