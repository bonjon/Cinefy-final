package logic.observer;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AdvancedUserBean;

/*
 * Classe ConcreteSubject del pattern Observer che 
 * contiene lo stato a cui gli oggetti ConcreteObserver 
 * sono interessati.
 */

public class ObservableAdTopList extends Subject {
	
	private List<AdvancedUserBean> topRated;
	
	public ObservableAdTopList(List<AdvancedUserBean> l) {
		this.topRated = l;
	}
	
	public ObservableAdTopList() {
		this.topRated = new ArrayList<>();
	}
	
	public void add(AdvancedUserBean ab) {
		topRated.add(ab);
	}
	
	public void remove(AdvancedUserBean ab) {
		topRated.remove(ab);
	}
	
	public void size() {
		topRated.size();
	}
	
	public List<AdvancedUserBean> getTop(){
		return topRated;
	}
	
	public void setTop(List<AdvancedUserBean> lb) {
		this.topRated = lb;
	}
}
