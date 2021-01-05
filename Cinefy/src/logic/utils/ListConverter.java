package logic.utils;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AdvancedUserBean;

public class ListConverter {
	
	//classe che si occupa di trasformare liste di oggetti in stringhe in formato "elenco"
	
	private ListConverter() {
		
	}
	
	public static String converter(List<AdvancedUserBean> adv) {
		List<String> list = new ArrayList<>();
		int i=0;
		int y=0;
		StringBuilder result;
		String finalResult;
		
		result = new StringBuilder("");
		
		while(i<adv.size()) {
			list.add(adv.get(i).getUsername());
			i++;
		}
		if(!list.isEmpty()) {
			while(y<list.size()) {
				if(result.toString().equals("")) {
					result.append(list.get(y));
				}
				else {
					result.append(", ");
					result.append(list.get(y));
				}
				y++;
			}
		}
		
		finalResult=result.toString();
		return finalResult;
	}

}
