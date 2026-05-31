package com.tekartik.sqflite.operation;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/* JADX INFO: loaded from: classes2.dex */
public class MethodCallOperation extends BaseOperation {
    final MethodCall methodCall;
    public final Result result;

    public MethodCallOperation(MethodCall methodCall, MethodChannel.Result result) {
        this.methodCall = methodCall;
        this.result = new Result(result);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public String getMethod() {
        return this.methodCall.method;
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public <T> T getArgument(String str) {
        return (T) this.methodCall.argument(str);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean hasArgument(String str) {
        return this.methodCall.hasArgument(str);
    }

    @Override // com.tekartik.sqflite.operation.BaseOperation, com.tekartik.sqflite.operation.BaseReadOperation
    public OperationResult getOperationResult() {
        return this.result;
    }

    class Result implements OperationResult {
        final MethodChannel.Result result;

        Result(MethodChannel.Result result) {
            this.result = result;
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void success(Object obj) {
            this.result.success(obj);
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void error(String str, String str2, Object obj) {
            this.result.error(str, str2, obj);
        }
    }
}
