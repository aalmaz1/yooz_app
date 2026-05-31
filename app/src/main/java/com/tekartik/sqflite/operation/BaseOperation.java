package com.tekartik.sqflite.operation;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseOperation extends BaseReadOperation {
    @Override // com.tekartik.sqflite.operation.BaseReadOperation
    protected abstract OperationResult getOperationResult();

    @Override // com.tekartik.sqflite.operation.OperationResult
    public void success(Object obj) {
        getOperationResult().success(obj);
    }

    @Override // com.tekartik.sqflite.operation.OperationResult
    public void error(String str, String str2, Object obj) {
        getOperationResult().error(str, str2, obj);
    }
}
