package de.nimple.services.nimplecode;

import de.nimple.services.nimplecode.dto.NimpleCode;

/**
 * Created by bjohn on 23/09/14.
 */
public interface NimpleCodeService {

	public abstract void save(NimpleCode code);

	public abstract NimpleCode load();

}
