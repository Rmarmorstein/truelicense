/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package net.truelicense.v2.commons.comp;

import net.truelicense.api.io.Sink;
import net.truelicense.api.io.Source;
import net.truelicense.api.io.Store;
import net.truelicense.api.io.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * The compression for V2 format license keys.
 * This class is immutable.
 *
 * @author Christian Schlichtherle
 */
public final class V2Compression implements Transformation {

    @Override public Sink apply(final Sink sink) {
        return () -> new DeflaterOutputStream(
                sink.output(),
                new Deflater(Deflater.BEST_COMPRESSION),
                Store.BUFSIZE) {

            boolean closed;

            @Override
            public void close() throws IOException {
                if (!closed) {
                    closed = true;
                    try {
                        try { finish(); }
                        finally { def.end(); }
                    } finally {
                        super.close();
                    }
                }
            }
        };
    }

    @Override public Source unapply(final Source source) {
        return () -> new InflaterInputStream(
                source.input(),
                new Inflater(),
                Store.BUFSIZE) {

            boolean closed;

            @Override
            public void close() throws IOException {
                if (!closed) {
                    closed = true;
                    try { inf.end(); }
                    finally { super.close(); }
                }
            }
        };
    }
}
