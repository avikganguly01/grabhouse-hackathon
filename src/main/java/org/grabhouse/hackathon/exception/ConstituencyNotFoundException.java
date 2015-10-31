package org.grabhouse.hackathon.exception;

import org.grabhouse.hackathon.core.exception.AbstractResourceNotFoundException;

public class ConstituencyNotFoundException extends AbstractResourceNotFoundException {
	
	public ConstituencyNotFoundException() {
        super("error.msg.constituency.not.found", "Cannot find constituency");
    }

}
