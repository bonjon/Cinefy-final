package logic.observer;

import java.io.IOException;
import java.util.Collections;

import logic.exceptions.AdvancedNotFoundException;
import logic.viewfxml.AskBeginnerBoundary;

/*
 * Classe ConcreteObserver del pattern Observer, memorizza 
 * un riferimento a un oggetto ConcreteSubject, contiene
 * informazioni che devono essere costantemente sincronizzate con lo
 * stato del Subject ed implementa l’interfaccia di notifica 
 * di Observer per mantenere il proprio stato consistente con quello del Subject.
 */

public class TopRatedPanel implements Observer {

	private ObservableAdTopList observable;
	private AskBeginnerBoundary abb;

	public TopRatedPanel(ObservableAdTopList o, AskBeginnerBoundary parent) {
		this.observable = o;
		this.abb = parent;
	}

	@Override
	public void update() throws IOException, AdvancedNotFoundException {
		if (!observable.getTop().isEmpty()) {
			abb.topAdvanced.getItems().addAll(observable.getTop());
		} else
			abb.topAdvanced.getItems().addAll(Collections.emptyList());
	}

}
