/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package net.truelicense.v2.commons.auth;

import net.truelicense.api.auth.RepositoryController;
import net.truelicense.api.auth.RepositoryIntegrityException;
import net.truelicense.api.codec.Codec;
import net.truelicense.api.codec.Decoder;
import net.truelicense.spi.io.MemoryStore;

import java.security.Signature;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.Objects.requireNonNull;
import static net.truelicense.spi.codec.Codecs.charset;

/**
 * A repository controller for use with V2 format license keys.
 *
 * @author Christian Schlichtherle
 */
final class V2RepositoryController implements RepositoryController {

    private final V2RepositoryModel model;
    private final Codec codec;

    V2RepositoryController(final V2RepositoryModel model, final Codec codec) {
        this.model = requireNonNull(model);
        this.codec = requireNonNull(codec);
    }

    @Override
    public final Decoder sign(final Signature engine, final Object artifact) throws Exception {
        final MemoryStore store = new MemoryStore();
        codec.encoder(store).encode(artifact);
        final byte[] artifactData = store.data();
        engine.update(artifactData);
        final byte[] signatureData = engine.sign();

        final String encodedArtifact = body(codec, artifactData);
        final String encodedSignature = getEncoder().encodeToString(signatureData);
        final String signatureAlgorithm = engine.getAlgorithm();

        model.setArtifact(encodedArtifact);
        model.setSignature(encodedSignature);
        model.setAlgorithm(signatureAlgorithm);

        return codec.decoder(store);
    }

    private static String body(Codec codec, byte[] artifact) {
        return charset(codec)
                .map(charset -> new String(artifact, charset))
                .orElseGet(() -> getEncoder().encodeToString(artifact));
    }

    @Override
    public final Decoder verify(final Signature engine) throws Exception {
        if (!engine.getAlgorithm().equalsIgnoreCase(model.getAlgorithm()))
            throw new IllegalArgumentException();
        final byte[] artifactData = data(codec, model.getArtifact());
        engine.update(artifactData);
        if (!engine.verify(getDecoder().decode(model.getSignature())))
            throw new RepositoryIntegrityException();
        return codec.decoder(new MemoryStore().data(artifactData));
    }

    private static byte[] data(Codec codec, String body) {
        return charset(codec)
                .map(body::getBytes)
                .orElseGet(() -> getDecoder().decode(body));
    }
}
