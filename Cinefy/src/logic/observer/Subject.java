package logic.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.AdvancedNotFoundException;

/*
 * Classe Subject del patter Observer, conosce i propri Observer.
 * Un numero qualsiasi di oggetti Observer può osservare un oggetto.
 */

public abstract class Subject {

	private List<Observer> observers;

	protected Subject() {
		this.observers = new ArrayList<>();
	}

	public void attach(Observer o) {
		observers.add(o);
	}

	public void detach(Observer o) {
		observers.remove(o);
	}

	public void notifyObservers() throws IOException, AdvancedNotFoundException {
		for (Observer o : observers)
			o.update();
	}
}
