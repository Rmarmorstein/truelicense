/*
 * Copyright (C) 2005-2015 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */
package net.java.truelicense.core.auth;

/**
 * Defines authentication parameters for signing or verifying a
 * {@link RepositoryModel} by an {@link Authentication}.
 *
 * @author Christian Schlichtherle
 */
public interface AuthenticationParameters {

    /**
     * Returns {@code true} if and only if these parameters can get used to
     * sign a {@link RepositoryModel}.
     *
     * @deprecated Since TrueLicense 2.4, this method is not used anymore.
     */
    boolean forSigning();
}
