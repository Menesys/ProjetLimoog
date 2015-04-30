package com.example.projetlimoog.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
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
import com.example.projetlimoog.model.Note;
import com.example.projetlimoog.model.Usager;

public class CustomAdapter extends BaseAdapter {

	private List<Competence> listeComp;
	private Evaluer eval;
	private LayoutInflater mInflater;
	private Map<Competence, CheckBoxEvaluationListener> listeners = new HashMap<Competence, CheckBoxEvaluationListener>();

	public CustomAdapter(Evaluer c, List<Competence> lcomp) {
		eval = c;
		listeComp = lcomp;
		mInflater = LayoutInflater.from(eval);
	}

	@Override
	public int getCount() {
		return listeComp.size();
	}

	@Override
	public Object getItem(int pos) {
		return listeComp.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return listeComp.get(pos).getiId();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {

		LinearLayout layoutItem;

		if (convertView == null) {
			layoutItem = (LinearLayout) mInflater.inflate(
					R.layout.evaluercheckboxes, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		// LG modif

		// Poser le flag indiquant qu'on est en train de charger
		eval.lLoading = true;

		// Retrouver l'objet compétence
		Competence loCompétence = listeComp.get(pos);

		// TextView : retrouver l'objet
		TextView compName = (TextView) layoutItem.findViewById(R.id.compName);
		// TextView : affecter le texte : nom de la compétence
		compName.setText(loCompétence.getcNom().toString());

		// Radiogroup : retrouver l'objet
		RadioGroup group = (RadioGroup) layoutItem
				.findViewById(R.id.radioGroup1);
		// RadioGroup : retrouver la note
		Usager loUser = eval.currentUser;
		Evaluation loEval = null;
		Note loNote = null;
		if (loUser != null) {
			loEval = eval.eval;
			loNote = loUser.getNoteByCompetenceAndEvaluation(loCompétence,
					loEval); // si Note n déjà existante
		}

		// RadioGroup : cocher selon la note
		if (loNote == null) {
			if (loUser == null) {
				Log.v("CustomAdapter.getView", pos + " loUser est Null");
			} else {
				Log.v("CustomAdapter.getView", pos + " " + loUser.getcPrenom()
						+ " " + loCompétence.getcNom() + " loNote est Null");
			}
			group.check(-1);
		} else {
			Log.v("CustomAdapter.getView",
					pos + " " + loUser.getcPrenom() + " "
							+ loCompétence.getcNom() + " loNote est "
							+ loNote.getiNiveau());

			switch (loNote.getiNiveau()) { // On check le radiobutton
											// correspondant
			case 1:
				group.check(R.id.NA);
				break;
			case 2:
				group.check(R.id.P);
				break;
			case 3:
				group.check(R.id.B);
				break;
			case 4:
				group.check(R.id.TB);
				break;
			default:
				group.check(-1);
			}

		}

		// Enlever le flag indiquant qu'on est en train de charger
		eval.lLoading = false;

		// LG Fin

		// Attacher les listeners sur les items de la liste
		CheckBoxEvaluationListener listener = listeners.get(listeComp.get(pos));
		if (listener == null) {
			listener = new CheckBoxEvaluationListener(listeComp.get(pos), eval,
					group, compName);
			listeners.put(listeComp.get(pos), listener);
			// Toast.makeText(eval.getApplicationContext(),
			// listeners.size()+" size", Toast.LENGTH_SHORT).show();
		}

		group.setOnCheckedChangeListener(listener);

		return layoutItem;
	}

	public void clearCheck() {
		for (int i = 0; i < listeComp.size(); i++) { /*
													 * CheckBoxEvaluationListener
													 * c : listeners
													 */
			listeners.get(listeComp.get(i)).clear();
		}
	}

	public void updateNote() {
		notifyDataSetChanged();
	}

}
