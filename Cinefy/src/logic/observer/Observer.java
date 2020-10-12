package logic.observer;

import java.io.IOException;

import logic.exceptions.AdvancedNotFoundException;

/*
 * Interfaccia Observer del pattern Observer che serve a notificare 
 * agli oggetti i cambiamenti.
 */

public interface Observer {

	public void update() throws IOException, AdvancedNotFoundException;
}
