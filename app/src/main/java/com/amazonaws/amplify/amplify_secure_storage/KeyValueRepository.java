package com.amazonaws.amplify.amplify_secure_storage;

import kotlin.Metadata;

/* JADX INFO: compiled from: KeyValueRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0006H&¨\u0006\n"}, d2 = {"Lcom/amazonaws/amplify/amplify_secure_storage/KeyValueRepository;", "", "get", "", "dataKey", "put", "", "value", "remove", "removeAll", "amplify_secure_storage_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KeyValueRepository {
    String get(String dataKey);

    void put(String dataKey, String value);

    void remove(String dataKey);

    void removeAll();
}
