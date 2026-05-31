package com.tekartik.sqflite.operation;

import com.tekartik.sqflite.SqlCommand;

/* JADX INFO: loaded from: classes2.dex */
public interface Operation extends OperationResult {
    <T> T getArgument(String str);

    boolean getContinueOnError();

    Boolean getInTransactionChange();

    String getMethod();

    boolean getNoResult();

    SqlCommand getSqlCommand();

    Integer getTransactionId();

    boolean hasArgument(String str);

    boolean hasNullTransactionId();
}
