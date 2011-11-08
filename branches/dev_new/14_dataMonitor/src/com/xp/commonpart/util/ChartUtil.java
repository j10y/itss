package com.xp.commonpart.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

public final class ChartUtil {
	public static List getColor(){
		List list=new ArrayList();
		
		list.add("DC143C");
		list.add("FF6347");
		list.add("FFFF00");
		list.add("00FFFF");
		
		list.add("1D8BD1");
		list.add("fff9a7");
		list.add("F1683C");
		list.add("2AD62A");
		list.add("DBDC25");
		list.add("DBFF25");
		list.add("DBEE25");
		list.add("DBCC25");
		
		return list;
	}
	
	public static String getDataSetByPar(MainTableChart maintablechart,List resoultListchart){
		//String parm="<chart palette='2' ";
//		if(maintablechart.getCharttitle()!=null){
//			parm+="caption='"+maintablechart.getCharttitle()+"'";
//		}
//		parm+="baseFontSize='12'  rotateNames='0' slantLabels='1'  shownames='1' showvalues='0' ";
//		if(maintablechart.getAxisNameX()!=null){
//			parm+="xAxisName='"+maintablechart.getAxisNameX()+"' ";
//		}
//		if(maintablechart.getAxisNameY()!=null){
//			parm+="yAxisName='"+maintablechart.getAxisNameY()+"' ";
//		}
		String parm="<chart palette='2' caption='' shownames='1' showvalues='0' numberPrefix='' useRoundEdges='1' legendBorderAlpha='0' >";
		HashMap chartmap=new HashMap();
		List<String> seriesSet=new ArrayList();
		HashSet axisvaXlue=new HashSet();
		for(Object obj:resoultListchart){
			ListOrderedMap map=(ListOrderedMap) obj;
			String[] seriesNames=maintablechart.getSeriesName().split(",");
			String[] axisvalueYs=maintablechart.getAxisvalueY().split(",");
			for(int i=0;i<seriesNames.length;i++){
				Object sn=map.get(seriesNames[i]);
				Object avx=map.get(maintablechart.getAxisvalueX());
				Object avy=map.get(axisvalueYs[i]);
				boolean flag=false;
				for(String se:seriesSet){
					if(se.equals(sn.toString())){
						flag=true;
					}
				}
				if(flag==false){
					seriesSet.add(sn.toString());
				}
				if(avx==null){
					avx="其他";
				}
				axisvaXlue.add(avx);
				Double avyold=0d;
				if(chartmap.get(sn+""+avx)!=null){
					avyold=Double.parseDouble(chartmap.get(sn+""+avx).toString())+Double.parseDouble(avy.toString());
					chartmap.put(sn+""+avx, avyold.toString());
				}else{
					chartmap.put(sn+""+avx, avy==null?0:avy.toString());
				}
			}
		}
		List alist=new ArrayList();
		for(Object ob:axisvaXlue){
			alist.add(ob);
		}
		Collections.sort(alist);
		String categories="<categories>";
		for(Object ob:alist){
			categories=categories+"<category label='"+ob.toString() +"' />";
		}
		categories+="</categories>";
		String dataset="";
		int i=0;
		for(Object obj:seriesSet){
			String colors="";
			if(obj.toString().equals("紧急")){
				colors="DC143C";
			}else if(obj.toString().equals("主要")){
				colors="FF6347";
			}else if(obj.toString().equals("次要")){
				colors="FFFF00";
			}else if(obj.toString().equals("警告")){
				colors="00FFFF";
			}else{
				colors=ColorUtil.getColor().get(i).toString();
			}
			dataset=dataset+"<dataset  lineThickness='3' seriesName='"+obj+"' color='"+colors+"' showValues='0'  parentYAxis='S' >";
			i++;
			for(Object ob:alist){
				dataset+="<set  value='"+(chartmap.get(obj+""+ob)==null?0:chartmap.get(obj+""+ob))+"'  />";
			}
			dataset+="</dataset>";
		}
		parm+=categories+dataset+"</chart>";
		return parm;
	}
}
