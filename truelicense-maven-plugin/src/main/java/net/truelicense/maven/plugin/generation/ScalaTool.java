/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package net.truelicense.maven.plugin.generation;

import net.truelicense.obfuscate.ObfuscatedString;

/**
 * An immutable Velocity tool for use with Scala template files.
 *
 * @author Christian Schlichtherle
 */
public final class ScalaTool {

    public String obfuscatedString(String s) {
        return ObfuscatedString
                .obfuscate(s)
                .replace("new long[] { ", "Array[Long](")
                .replace(" }).toString()", "))");
    }
}
