package ua.pr.reports.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import ua.pr.model.orm.Account;
import ua.pr.model.orm.Meter;
import ua.pr.model.orm.Substation;

public class Parameters {
	private static DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	private JDatePickerImpl dtBeg;
	private JDatePickerImpl dtEnd;
	private Object curNode;
	
	public Parameters(JDatePickerImpl dtBeg, JDatePickerImpl dtEnd, Object curNode) {
		this.dtBeg = dtBeg;
		this.dtEnd = dtEnd;
		this.curNode = curNode;
	}
	
	public void setParameters(Hashtable<String, String> pars, String params) {
		String[] arr = params.split(";");
		for (String val : arr) {
			if (val.equals("p_dtBeg")) {
				pars.put("p_dtBeg", df.format(dtBeg.getDate()));
			} else if (val.equals("p_dtEnd")) {
				pars.put("p_dtEnd", df.format(dtEnd.getDate()));
			} else if (val.equals("p_idAccount")) {
				if (curNode.getClass() == Account.class) {
					pars.put("p_idAccount", new Integer(((Account)curNode).getIdAccount()).toString());
				} else if (curNode.getClass() == Meter.class) {
					pars.put("p_idAccount", new Integer(((Meter)curNode).getLinkAccount().getIdAccount()).toString());
				}
			} else if (val.equals("p_Capt")) {
				if (curNode.getClass() == Account.class) {
					pars.put("p_Capt",((Account)curNode).getCapt());
				} else if (curNode.getClass() == Meter.class) {
					pars.put("p_Capt", ((Meter)curNode).getCapt());
				} else if (curNode.getClass() == Substation.class) {
					pars.put("p_Capt", ((Substation)curNode).getName());
				}
			} else if (val.equals("p_idNode")) {
				if (curNode.getClass() == Meter.class) {
					pars.put("p_idNode", new Integer(((Meter)curNode).getId().getIdNode()).toString());
				}
			} else if (val.equals("p_idMeter")) {
				if (curNode.getClass() == Meter.class) {
					pars.put("p_idMeter", new Integer(((Meter)curNode).getId().getIdMeter()).toString());
				}
			} else if (val.equals("p_nFactory")) {
				if (curNode.getClass() == Meter.class) {
					pars.put("p_nFactory",((Meter)curNode).getNFactory());
				} else if (curNode.getClass() == Account.class) {
					pars.put("p_nFactory", ((Account)curNode).getLinkAccounts().get(0).getMeters().get(0).getNFactory());
				} 
			} else if (val.equals("p_idSubstation")) {
				if (curNode.getClass() == Substation.class) {
					pars.put("p_idSubstation", new Integer(((Substation)curNode).getIdSubstation()).toString());
				}
			} 
		}		
	}
}
