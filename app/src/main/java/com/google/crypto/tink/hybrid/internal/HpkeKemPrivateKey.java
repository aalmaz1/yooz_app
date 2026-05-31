package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
public interface HpkeKemPrivateKey {
    Bytes getSerializedPrivate();

    Bytes getSerializedPublic();
}
