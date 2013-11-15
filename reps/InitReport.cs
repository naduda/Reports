using System;
using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using System.Data; 
using System.Data.SqlClient;
using Simple.Reports;
//using System.Windows.Forms;
using UkrEni.DBTool;
using UkrEni.NodePropertys;

namespace Simple
{
	/// <summary>
	/// Summary description for SettingReport.
	/// </summary>
	public class InitReports
	{
		private ConnectionInfo crConnectionInfo;
		private TableLogOnInfo crTableLogOnInfo;
		private Database crDatabase;
		private Tables crTables;
		public ReportDocument Otchreport;
        
		
		
		//int IdNode;
		int IdMeter;
		int IdAccount;
		int DecimalPoint = 3;
		int FontSize = 12;
		string DTBeg;
		string DTEnd;
		string NameObj;
		int typeTree;
		string listobject = "";
        string Notes = "";
        int StartHour =0;
        DateTime DateTimeBeg;
        DateTime DateTimeEnd;
		//Строка входящих параметров
		//0-DTBeg
		//1-DTEnd
		//2-NameObj
		//3-RazmA
		//4-RazmR
        public InitReports(string NameReport, NodeProp nodeProp, string listPars, string notes, DBToolKit dbWork)
		{
            Notes = notes;
			//IdNode = nodeProp.IdParent;
			IdMeter= nodeProp.IdObject;
			IdAccount=nodeProp.IdObject;
			string[] ListPars = listPars.Split('#');
			DTBeg = ListPars[0];
            DateTimeBeg = Convert.ToDateTime(DTBeg);
			DTEnd = ListPars[1];
            DateTimeEnd = Convert.ToDateTime(DTEnd);
			NameObj = ListPars[2];
			typeTree = Convert.ToInt32( ListPars[3]);
			DecimalPoint = Convert.ToInt32( ListPars[4]);
			FontSize  = Convert.ToInt32( ListPars[5]);
            try
            {
                StartHour = Convert.ToInt32(ListPars[6]);
            }
            catch { }
           // SetStartHour(0);
            //if(ListPars.Length==7)
            //listobject = ListPars[6];
		//	RazmR = ListPars[4];

			crConnectionInfo = new ConnectionInfo();
            crConnectionInfo.ServerName = dbWork.ReturnIDFromConnString("datasource");
            crConnectionInfo.DatabaseName = dbWork.ReturnIDFromConnString("initialcatalog");
            crConnectionInfo.UserID = dbWork.ReturnIDFromConnString("userid");
            crConnectionInfo.Password = dbWork.ReturnIDFromConnString("password");
			
						
			//DataSet ds = new DataSet();
			//SqlDataAdapter da;
			
			string query = "";
			string Capt;
            string Razm = "";
			switch(NameReport)	
			{
				case "График часовой":

					
                    if (NameObj.ToLower().IndexOf("небаланс") != -1)
                    {
                        query = String.Format("select * from fGetBalanceForGroupByProfileByDeltaByOutDelta({0}, {1}, \'{2}\', \'{3}\', \'{4}\')", nodeProp.IdObject,
                                    60, "-", DTBeg, DTEnd);
                        Otchreport = new Simple.Reports.SumGroupFrofileDelta();
                        Razm = "";
                    }
                    else
                    {
                        query = "select * from fGetSumGroup(" + nodeProp.IdObject + ", 17, " + (int)nodeProp.IdType + ", 1" +
                            ", \'" + DTBeg + "\', " + "\'" + DTEnd + "\'" + ")";
                        Otchreport = new Simple.Reports.SumGroupFrofile();    
                        SetTotalVis("true");
                        Razm =", кВт*ч/кВар*ч";
                    }

                    if (DateTime.Parse(DTBeg).AddDays(1) == DateTime.Parse(DTEnd))
                    {
                        Capt = NameObj + "\r\n" + "Часовой график" + Razm + " за " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy");
                    }
                    else
                    {
                        Capt = NameObj + "\r\n" + "Часовой график" + Razm + " с " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + DateTime.Parse(DTEnd).ToString("dd MMMM yyyy");
                    }

					SetDecimalPoint();
					SetFontSize();
					SetTitle(Capt);
					break;
				case "График получасовой":

					if(DateTime.Parse(DTBeg).AddDays(1)==DateTime.Parse(DTEnd))
					{
						Capt = NameObj+"\r\n"+ "Получасовой график, кВт/кВар за " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy");
					}
					else
					{
                        Capt = NameObj + "\r\n" + "Получасовой график, кВт/кВар c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + DateTime.Parse(DTEnd).ToString("dd MMMM yyyy");
					}
					query = "select * from fGetSumGroup(" +nodeProp.IdObject+", 1730, "+(int)nodeProp.IdType+", 1"+
						", \'"+DTBeg+"\', "+"\'"+DTEnd+"\'"+")";	
					Otchreport = new Simple.Reports.SumGroupFrofile();
					SetTitle(Capt);
					SetDecimalPoint();
					SetFontSize();
					SetTotalVis("false");
					break;
				case "График суточный -10 дней":
					DTEnd = DateTime.Parse(DTBeg).AddDays(10).ToString();
                    Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + DateTime.Parse(DTEnd).ToString("dd MMMM yyyy");
					
					query = "select * from fGetSumGroup(" +nodeProp.IdObject+", 12, "+(int)nodeProp.IdType+", 1"+
						", \'"+DTBeg+"\', "+"\'"+DTEnd+"\'"+")";	
					Otchreport = new Simple.Reports.SumGroupFrofile();
					SetTotalVis("true");
					SetDecimalPoint();
					SetFontSize();
					SetTitle(Capt);
					break;
				case "График суточный -20 дней":
					DTEnd = DateTime.Parse(DTBeg).AddDays(20).ToString();
                    Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + DateTime.Parse(DTEnd).ToString("dd MMMM yyyy");
					
					query = "select * from fGetSumGroup(" +nodeProp.IdObject+", 12, "+(int)nodeProp.IdType+", 1"+
						", \'"+DTBeg+"\', "+"\'"+DTEnd+"\'"+")";	
					Otchreport = new Simple.Reports.SumGroupFrofile();
					SetTitle(Capt);
					SetDecimalPoint();
					SetFontSize();
					SetTotalVis("true");
					break;
				case "График суточный -месяц":
					DTEnd = DateTime.Parse(DTBeg).AddMonths(1).ToString();
					if(DateTime.Parse(DTBeg).Day>=28)
                        Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч за " + DateTime.Parse(DTEnd).ToString("MMMM yyyy");
					else
                        Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч за " + DateTime.Parse(DTBeg).ToString("MMMM yyyy");
					query = "select * from fGetSumGroup(" +nodeProp.IdObject+", 12, "+(int)nodeProp.IdType+", 1"+
						", \'"+DTBeg+"\', "+"\'"+DTEnd+"\'"+")";	
					Otchreport = new Simple.Reports.SumGroupFrofile();
					SetTotalVis("true");
					SetDecimalPoint();
					SetFontSize();
					SetTitle(Capt);
					break;
				case "График суточный(SUM) -месяц":
					DTEnd = DateTime.Parse(DTBeg).AddMonths(1).ToString();
					if(DateTime.Parse(DTBeg).Day>=28)
                        Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч накопительный за " + DateTime.Parse(DTEnd).ToString("MMMM yyyy");
					else
                        Capt = NameObj + "\r\n" + "Суточный график, кВт*ч/кВар*ч накопительный за " + DateTime.Parse(DTBeg).ToString("MMMM yyyy");
					query = "select * from fGetSumGroup(" +nodeProp.IdObject+", 12, "+(int)nodeProp.IdType+", 11"+
						", \'"+DTBeg+"\', "+"\'"+DTEnd+"\'"+")";	
					Otchreport = new Simple.Reports.SumGroupFrofile();
					SetTitle(Capt);
					SetDecimalPoint();
					SetFontSize();
					SetTotalVis("false");
					break;

                case "Основной-дублирующий":
                    switch (nodeProp.IdType)
                    {
                        case TypeObject.account:
                        case TypeObject.accountOV:
                        case TypeObject.accountTech:
                            //SetRazmA("%");
                            //SetRazmR("%");
                            query = String.Format("select * from  MRDMGetVerificationTotal({0} ,\'{1}\', \'{2}\', \'{3}\')",
                                nodeProp.IdObject, nodeProp.IdType.ToString(), DTBeg, DTEnd);
                        break;
                    }
                    Otchreport = new Simple.Reports.Verification();
                    SetTitle(String.Format("Верификация учета {0}\r\nОсновной-Дублирующий", NameObj));
                    //SetNotes(Notes);
                    SetFontSize();
                    SetDecimalPoint();
                    //SetDTBeg();
                    break;
				case"Профиль нагрузки получасовой":
					switch(nodeProp.IdType)
					{
                        case TypeObject.account:
                        case TypeObject.virtualAccount:
                        case TypeObject.accountOV:
                        case TypeObject.accountTech:
                        case TypeObject.oik:
                        case TypeObject.itec:
                        case TypeObject.oppositeLine:
                        case TypeObject.manual:
							SetRazmA("кВт");
							SetRazmR("кВар");
							query = String.Format("select * from  MRDMGetAcProfileHalfHour({0} ,\'{1}\', \'{2}\', \'{3}\')",
                                nodeProp.IdObject, nodeProp.IdType.ToString(), DateTimeBeg.AddHours(StartHour), DateTimeEnd.AddHours(StartHour));
							break;
						case TypeObject.meter:
                        case TypeObject.meterReserve:
                            switch (typeTree)
                            {
                                case 0:
                                    SetRazmA("Вт");
                                    SetRazmR("Вар");
                                    query = "select *, 192 as \'quality\' from  MRDMGetProfileHalfHour(" + nodeProp.IdParent + ", " + nodeProp.IdObject +
                                        ", \'" + DateTimeBeg.AddHours(StartHour) + "\', \'" + DateTimeEnd.AddHours(StartHour) + "\')";
                                    break;
                                case 1:
                                    SetRazmA("кВт");
                                    SetRazmR("кВар");
                                    query = String.Format("select * from  MRDMGetAcProfileHalfHour({0} , \'{1}\', \'{2}\', \'{3}\')",
                                        nodeProp.IdObject, nodeProp.IdType.ToString(), DateTimeBeg.AddHours(StartHour), DateTimeEnd.AddHours(StartHour));
                                    break;
                            }
							
							break;
					}
					Otchreport = new Simple.Reports.Profnag_sut30M();
					SetTitle(NameObj);
                    SetNotes(Notes);
                    SetStartHour(StartHour);
					SetFontSize();
					SetDTBeg();									
					break;
				case"Профиль нагрузки часовой":
					switch(nodeProp.IdType)
					{
						case TypeObject.account:
						case TypeObject.virtualAccount:
                        case TypeObject.accountOV:
                        case TypeObject.accountTech:
                        case TypeObject.oik:
                        case TypeObject.itec:
                        case TypeObject.oppositeLine:
                        case TypeObject.manual:
							SetRazmA("кВт");
							SetRazmR("кВар");
                            query = String.Format("select * from  MRDMGetAcProfileHour({0} , \'{1}\', \'{2}\', \'{3}\')",
                                nodeProp.IdObject, nodeProp.IdType.ToString(), DateTimeBeg.AddHours(StartHour), DateTimeEnd.AddHours(StartHour));
							break;
						case TypeObject.meter:
                        case TypeObject.meterReserve:
                            switch (typeTree)
                            {
                                case 0:
                                    SetRazmA("Вт");
                                    SetRazmR("Вар");
                                    query = "select * , 192 as \'quality\'  from  MRDMGetProfileHour (" + nodeProp.IdParent + ", " + nodeProp.IdObject +
                                        ", \'" + DateTimeBeg.AddHours(StartHour) + "\', \'" + DateTimeEnd.AddHours(StartHour) + "\')";                                
                                    break;
                                case 1:
                                    SetRazmA("кВт");
                                    SetRazmR("кВар");
                                    query = String.Format("select * from  MRDMGetAcProfileHour({0} , \'{1}\', \'{2}\', \'{3}\')",
                                        nodeProp.IdObject, nodeProp.IdType.ToString(), DateTimeBeg.AddHours(StartHour), DateTimeEnd.AddHours(StartHour));
                                    break;
                            }
							break;
					}
					Otchreport = new Simple.Reports.Profnag_sut60M();
                    SetNotes(Notes);
					SetTitle(NameObj);
					SetFontSize();
                    SetStartHour(StartHour);
					SetDTBeg();
					break;
				case"Электроэнергия за сутки":
				case"Электроэнергия за 10 дней":
				case"Электроэнергия за 20 дней":
				case"Электроэнергия за месяц":
				switch(NameReport)	
				{
					case"Электроэнергия за сутки":
						DTEnd = DateTime.Parse(DTBeg).AddDays(1).ToString();
						break;
					case"Электроэнергия за 10 дней":
						DTEnd = DateTime.Parse(DTBeg).AddDays(10).ToString();
						break;
					case"Электроэнергия за 20 дней":
						DTEnd = DateTime.Parse(DTBeg).AddDays(20).ToString();
						break;
					case"Электроэнергия за месяц":
						DTEnd = DateTime.Parse(DTBeg).AddMonths(1).ToString();
						break;
				}
					
					SetDTBeg();
					SetTitle(NameObj);
					SetDTEnd(DTEnd);
					SetDecimalPoint();
					SetFontSize();
					switch(nodeProp.IdType)
					{
                        case TypeObject.itec:
                            throw new Exception("Данный отчет не поддерживается - Подсистема отчетности");
                        case TypeObject.group:

                            switch (typeTree)
                            {
                                case 0:
                                    Otchreport = new Simple.Reports.PotrebEnergy();
                                    query = String.Format ("select * from  fGetEnergyByGrouping ({0},\'{1}\', \'{2}\', \'{3}\', \'{4}\')",
                                        nodeProp.IdObject, DTBeg , DTEnd , "кВт*ч", "кВар*ч");
                                    break;
                                case 1:
                                    Otchreport = new Simple.Reports.Potreb_account();
                                    query = String.Format("select * from  MRDMGetEnergyAccountByGrouping ({0}, \'{1}\', \'{2}\', \'{3}\', \'{4}\') order by Capt",
                                                             nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
                                    break;
                            }
                            
                            break;
                           
						case TypeObject.account:
						case TypeObject.virtualAccount:
                        case TypeObject.accountOV:
                        case TypeObject.accountTech:
                        case TypeObject.oppositeLine:
							Otchreport = new Simple.Reports.Potreb_account();
                            query = String.Format("select * from  MRDMGetEnergyAccount ({0}, {1}, \'{2}\', \'{3}\', \'{4}\', \'{5}\') order by Capt",
                                                    "null", nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
			
							break;
                        case TypeObject.oik:
                            Otchreport = new Simple.Reports.Potreb_account();
                            query = String.Format("select * from  MRDMGetEnergyAccount ({0}, {1}, \'{2}\', \'{3}\', \'{4}\', \'{5}\') order by Capt",
                                                    -1, nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
                            break;
                        case TypeObject.manual:
                            Otchreport = new Simple.Reports.Potreb_account();
                            query = String.Format("select * from  MRDMGetEnergyAccount ({0}, {1}, \'{2}\', \'{3}\', \'{4}\', \'{5}\') order by Capt",
                                                    -2, nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
                            break;
						case TypeObject.meter:
                            Otchreport = new Simple.Reports.PotrebEnergy();
                            //query = "select * from  fGetEnergy (null , null, " + nodeProp.IdObject + ", \'Meter\' " +
                            //    ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";   

                            switch (typeTree)
                            {
                                case 0:
//                                    Otchreport = new Simple.Reports.PotrebEnergy();
                                    query = "select * from  fGetEnergy (" + nodeProp.IdParent + ", " + nodeProp.IdObject + ", null, \'Meter\' " +
                                        ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";
                                    break;
                                case 1:
                                    query = "select * from  fGetEnergy (null , null,  " + nodeProp.IdObject + ", \'Meter\' " +
                                        ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";
                                    //Otchreport = new Simple.Reports.Potreb_account();
                                    //query = "select * from  fGetEnergy (" + nodeProp.IdParent + ", " + nodeProp.IdObject + ", null, \'Meter\' " +
                                    //    ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";
                                    //query = String.Format("select * from  MRDMGetEnergyAccount ({0}, {1}, \'{2}\', \'{3}\', \'{4}\', \'{5}\') order by Capt",
                                    //                        (int)nodeProp.IdType * -1, nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
                                    break;
                            }
							
							break;
                        case TypeObject.meterReserve:
                            Otchreport = new Simple.Reports.PotrebEnergy();
                            query = "select * from  fGetEnergy (null , null, " + nodeProp.IdObject + ", \'MeterReserve\' " +
                                ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";   
                            
                            //switch (typeTree)
                            //{
                            //    case 0:
                            //        Otchreport = new Simple.Reports.PotrebEnergy();
                            //        query = "select * from  fGetEnergy (" + nodeProp.IdParent + ", " + nodeProp.IdObject + ", \'Meter\' " +
                            //            ", \'" + DTBeg + "\', \'" + DTEnd + "\', \'кВт*ч\', \'кВар*ч\')";
                            //        break;
                            //    case 1:
                            //        Otchreport = new Simple.Reports.Potreb_account();
                            //        query = String.Format("select * from  MRDMGetEnergyAccount ({0}, {1}, \'{2}\', \'{3}\', \'{4}\', \'{5}\') order by Capt",
                            //                                (int)nodeProp.IdType * -1, nodeProp.IdObject, DTBeg, DTEnd, "кВт*ч", "кВар*ч");
                            //        break;
                            //}

                            break;

						case TypeObject.subst:
							switch(typeTree)
							{
								case 0:
									Otchreport = new Simple.Reports.PotrebEnergy();
									query = "select * from  fGetEnergy ("+nodeProp.IdObject+", "+" null, null,\'Meter\' "+
										", \'"+	DTBeg+"\', \'"+DTEnd+"\', \'кВт*ч\', \'кВар*ч\')";		
									break;
								case 1:Otchreport = new Simple.Reports.Potreb_account();
                                    
                                    query = "select * from  MRDMGetEnergyAccount (" + nodeProp.IdObject +
										", null, \'"+	DTBeg+"\', \'"+DTEnd+"\', \'кВт*ч\', \'кВар*ч\')";
									break;
							}
							break;
					}
			
					break;
				case"Показания":
					switch(nodeProp.IdType)
					{
						case TypeObject.meter:
                        case TypeObject.meterReserve:
/*							if(listobject!="")
							{
								query = "select * from  fGetDataPokazannew (\'"+listobject +"\', \'"+ DTBeg +"\')";
							}else */
							query = "select * from  fGetDataPokazan ("+nodeProp.IdParent+", "+nodeProp.IdObject+
								", \'"+ DTBeg +"\')";
							break;
						case TypeObject.subst:
							query = "select * from  fGetDataPokazan ("+nodeProp.IdObject+", null, \'"+ DTBeg +"\')";
							break;
                        case TypeObject.group:
                            query = "select * from  fGetDataPokazanByGrouping ("+nodeProp.IdObject+", \'"+ DTBeg +"\')";
                            
                            break;

					}
				
					Otchreport = new Simple.Reports.Pokazn();
					SetDTBeg();
					SetDecimalPoint();
					SetFontSize();
					SetTitle(NameObj);
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");					
					break;
				case"Показания 5 минутные":
				switch(nodeProp.IdType)
				{
					case TypeObject.meter:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdParent+", "+nodeProp.IdObject+
							", \'"+DTBeg+"\', 5)";
						break;
					case TypeObject.subst:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdObject+", null, \'"+DTBeg+"\', 5)";
						break;
				}

					Otchreport = new Simple.Reports.Potochni_5();
					SetTitle(NameObj);
					SetDTBeg();
					SetDecimalPoint();
					SetFontSize();
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");
					break;
				case"Показания 30 минутные":
				switch(nodeProp.IdType)
				{
					case TypeObject.meter:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdParent+", "+nodeProp.IdObject+
							", \'"+DTBeg+"\', 30)";
						break;
					case TypeObject.subst:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdObject+", null, \'"+DTBeg+"\', 30)";
						break;
				}

					Otchreport = new Simple.Reports.Potochni_5();
					SetTitle(NameObj);
					SetDTBeg();
					SetFontSize();
					SetDecimalPoint();
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");
					break;
				case"Показания 60 минутные":

				switch(nodeProp.IdType)
				{
					case TypeObject.meter:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdParent+", "+nodeProp.IdObject+
							", \'"+DTBeg+"\', 60)";
						break;
					case TypeObject.subst:
						query = "select * from  fGetCurrentProfile ("+nodeProp.IdObject+", null, \'"+DTBeg+"\', 60)";
						break;
				}

					Otchreport = new Simple.Reports.Potochni_5();
					SetTitle(NameObj);
					SetDTBeg();
					SetFontSize();
					SetDecimalPoint();
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");
					break;

				case"Протокол замеров- 30 мин":
				switch(nodeProp.IdType)
				{
					case TypeObject.meter:
						query = "select * from  fGetProtocole ("+nodeProp.IdParent+", "+nodeProp.IdObject+
							", \'"+DTBeg+"\', 30)";
						break;
					case TypeObject.subst:
						query = "select * from  fGetProtocole ("+nodeProp.IdObject+", null, \'"+DTBeg+"\', 30)";
						break;
				}

					Otchreport = new Simple.Reports.Potochni_5();
					SetTitle(NameObj);
					SetDTBeg();
					SetFontSize();
					SetDecimalPoint();
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");
					break;
				case"Протокол замеров- час":
				switch(nodeProp.IdType)
				{
					case TypeObject.meter:
						query = "select * from  fGetProtocole ("+nodeProp.IdParent+", "+nodeProp.IdObject+
							", \'"+DTBeg+"\', 60)";
						break;
					case TypeObject.subst:
						query = "select * from  fGetProtocole ("+nodeProp.IdObject+", null, \'"+DTBeg+"\', 60)";
						break;
				}

					Otchreport = new Simple.Reports.Potochni_5();
					SetTitle(NameObj);
					SetDTBeg();
					SetDecimalPoint();
					SetFontSize();
					SetRazmA("кВт*ч");
					SetRazmR("кВар*ч");
					break;

				case"Детальный отчет за сутки":
				case"Детальный отчет за 10 дней":
				case"Детальный отчет за 20 дней":
				case"Детальный отчет за месяц":	
					switch(NameReport)	
					{

						case"Детальный отчет за сутки":
							DTEnd = DateTime.Parse(DTBeg).AddDays(1).ToString();
							break;
						case"Детальный отчет за 10 дней":
							DTEnd = DateTime.Parse(DTBeg).AddDays(10).ToString();
							break;
						case"Детальный отчет за 20 дней":
							DTEnd = DateTime.Parse(DTBeg).AddDays(20).ToString();
							break;
						case"Детальный отчет за месяц":
							DTEnd = DateTime.Parse(DTBeg).AddMonths(1).ToString();
							break;
					}
                    Otchreport = new Simple.Reports.BalansEn();
                                        
					SetDTBeg();
					SetDecimalPoint();
					SetFontSize();
					SetDTEnd(DTEnd);
					SetRazmA("кВт/кВар*ч");
					SetNameobj();
					query = "select * from  fGetBalanceGroupByEnergy("+nodeProp.IdObject+", \'"+	DTBeg+"\',"+"\'"+DTEnd+"\')";
					break;
				case"Разница по дифтарифам за сутки":
				case"Разница по дифтарифам за месяц":
                    query = String.Format("Select nfactory from meters where idnode={0} and idmeter = {1}", nodeProp.IdParent , nodeProp.IdObject);
                    NameObj = String.Format("{0} ({1})", NameObj, dbWork.SelectScalar(query));
					switch (NameReport)
					{
						case"Разница по дифтарифам за сутки":
							DTEnd = DateTime.Parse(DTBeg).AddDays(1).ToString();
							Capt = NameObj+"\r\n"+ "Разница по дифтарифам за " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy");
							break;
						case"Разница по дифтарифам за месяц":
							DateTime dtEnd = DateTime.Parse(DTBeg).AddMonths(1);
							if(dtEnd>DateTime.Now.Date)
							{
								dtEnd = DateTime.Now.Date; 
								Capt = NameObj+"\r\n"+ "Разница по дифтарифам c " +DateTime.Parse(DTBeg).ToString("dd MMMM yyyy")+" по "+ dtEnd.ToString("dd MMMM yyyy");
							}
							else
							{
								if(DateTime.Parse(DTBeg).Day==1)
									Capt = NameObj+"\r\n"+ "Разница по дифтарифам за " +DateTime.Parse(DTBeg).ToString("MMMM yyyy");
								else
                                    Capt = NameObj + "\r\n" + "Разница по дифтарифам c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + dtEnd.ToString("dd MMMM yyyy");
							}
							DTEnd = dtEnd.ToString();
							break;
						default:Capt = NameObj+"\r\n"+ "Разница по дифтарифам c " +DateTime.Parse(DTBeg).ToString("dd MMMM yyyy")+" по "+ DateTime.Parse(DTEnd).ToString("dd MMMM yyyy");
							break;
					}
											
						
					
					query = "select * from  fGetPokaznByTarif ("+nodeProp.IdParent+","+ nodeProp.IdObject+", \'"+	DTBeg+"\',"+"\'"+DTEnd+"\')";
					Otchreport = new Simple.Reports.PotrebTarif();
					SetTitle(Capt);
					SetDecimalPoint();
					SetFontSize();
					break;
                case "Электроэнергия по дифтарифам за сутки":
                case "Электроэнергия по дифтарифам за месяц":
                case "Электроэнергия по дифтарифам":
                    //query = String.Format("Select nfactory from meters where idnode={0} and idmeter = {1}", nodeProp.IdParent, nodeProp.IdObject);
                    //NameObj = String.Format("{0} ({1})", NameObj, dbWork.SelectScalar(query));
                    switch (NameReport)
                    {
                        case "Электроэнергия по дифтарифам за сутки":
                            DTEnd = DateTime.Parse(DTBeg).AddDays(1).ToString();
                            Capt = NameObj + "\r\n" + "Электроэнергия по дифтарифам за " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy");
                            break;
                        case "Электроэнергия по дифтарифам за месяц":
                            DateTime dtEnd = DateTime.Parse(DTBeg).AddMonths(1);
                            if (dtEnd > DateTime.Now.Date)
                            {
                                dtEnd = DateTime.Now.Date;
                                Capt = NameObj + "\r\n" + "Электроэнергия по дифтарифам c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + dtEnd.ToString("dd MMMM yyyy");
                            }
                            else
                            {
                                if (DateTime.Parse(DTBeg).Day == 1)
                                    Capt = NameObj + "\r\n" + "Электроэнергия по дифтарифам за " + DateTime.Parse(DTBeg).ToString("MMMM yyyy");
                                else
                                    Capt = NameObj + "\r\n" + "Электроэнергия по дифтарифам c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy") + " по " + dtEnd.ToString("dd MMMM yyyy");
                            }
                            DTEnd = dtEnd.ToString();
                            break;
                        default: Capt = NameObj + "\r\n" + "Электроэнергия по дифтарифам c " + DateTime.Parse(DTBeg).ToString("dd MMMM yyyy HH:mm") + " по " + DateTime.Parse(DTEnd).ToString("dd MMMM yyyy HH:mm");
                            break;
                    }
                    //fGetPokaznAndEnergyByTarif (24432974, '25-07-2010',  '26-07-2010')
                    query = "select * from  fGetPokaznAndEnergyByTarif (" + nodeProp.IdObject + ", \'" + DTBeg + "\'," + "\'" + DTEnd + "\')";
                    Otchreport = new Simple.Reports.EnergyByTarif();
                    SetTitle(Capt);
                    SetDecimalPoint();
                    SetFontSize();
                    break;
				default:
					throw new Exception ("Нет такого отчета");
			}
            dbWork.ExecuteTimeOut = 900;
            
            //da = dbWork.SelectSqlDA(query);
            //da.Fill(ds);
            DataTable dt = dbWork.SelectTable(query);

            Otchreport.Database.Tables[0].SetDataSource(dt);
            
			crDatabase = Otchreport.Database;
			crTables = crDatabase.Tables;

			foreach (CrystalDecisions.CrystalReports.Engine.Table crTable in crTables)
			{       
				crTableLogOnInfo = crTable.LogOnInfo;
				crTableLogOnInfo.ConnectionInfo = crConnectionInfo;
				crTable.ApplyLogOnInfo(crTableLogOnInfo);
			}
		}

        private void SetStartHour(int startHour)
        {
            ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
            discreteVal.Value = startHour;
            ParameterField paramField = new ParameterField();
            paramField.ParameterFieldName = "starthour";
            paramField.CurrentValues.Add(discreteVal);
            paramFields.Add(paramField);
        }

		public ParameterFields paramFields = new ParameterFields ();
		
		private void SetTotalVis(string TotalVis)
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=TotalVis;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "TotalVis";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetDecimalPoint()
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=DecimalPoint ;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "point";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetFontSize()
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=FontSize ;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "FontSize";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetTitle(string Title)
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=Title;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "Title";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

        private void SetNotes(string Notes)
        {
            ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
            discreteVal.Value = Notes;
            ParameterField paramField = new ParameterField();
            paramField.ParameterFieldName = "Notes";
            paramField.CurrentValues.Add(discreteVal);
            paramFields.Add(paramField);
        }

		private void SetNameobj()
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=NameObj;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "NameObj";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetDTBeg()
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=DTBeg;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "DateT";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}
	
		private void SetDTEnd(string strDTEnd)
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=strDTEnd;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "DateTEnd";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetRazmA(string razm)
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=razm;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "RazmA";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

		private void SetRazmR(string razm)
		{
			ParameterDiscreteValue discreteVal = new ParameterDiscreteValue();
			discreteVal.Value=razm;
			ParameterField paramField = new ParameterField ();
			paramField.ParameterFieldName = "RazmR";
			paramField.CurrentValues.Add (discreteVal);
			paramFields.Add (paramField);
		}

	}
}
