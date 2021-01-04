package logic.utils;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AdvancedUserBean;

public class ListConverter {
	
	//classe che si occupa di trasformare liste di oggetti in stringhe in formato "elenco"
	
	
	public static String converter(List<AdvancedUserBean> adv) {
		List<String> list = new ArrayList<>();
		int i=0;
		int y=0;
		String result="";
		
		
		
		while(i<adv.size()) {
			list.add(adv.get(i).getUsername());
			i++;
		}
		if(list.isEmpty()) {
			result="";
		}
		else {
			while(y<list.size()) {
				if(result.isEmpty()) {
					result=list.get(y);
				}
				else {
					result=result+", ";
					String elem = list.get(y);
					result=result+elem;
				}
				y++;
			}
		}
		
		
		return result;
	}

}
