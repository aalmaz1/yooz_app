package com.tekartik.sqflite.operation;

import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class BatchOperation extends BaseOperation {
    final Map<String, Object> map;
    final boolean noResult;
    final BatchOperationResult operationResult = new BatchOperationResult();

    public BatchOperation(Map<String, Object> map, boolean z) {
        this.map = map;
        this.noResult = z;
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public String getMethod() {
        return (String) this.map.get(Constant.PARAM_METHOD);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public <T> T getArgument(String str) {
        return (T) this.map.get(str);
    }

    @Override // com.tekartik.sqflite.operation.Operation
    public boolean hasArgument(String str) {
        return this.map.containsKey(str);
    }

    @Override // com.tekartik.sqflite.operation.BaseOperation, com.tekartik.sqflite.operation.BaseReadOperation
    public OperationResult getOperationResult() {
        return this.operationResult;
    }

    public Map<String, Object> getOperationSuccessResult() {
        HashMap map = new HashMap();
        map.put(Constant.PARAM_RESULT, this.operationResult.result);
        return map;
    }

    public Map<String, Object> getOperationError() {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map2.put(Constant.PARAM_ERROR_CODE, this.operationResult.errorCode);
        map2.put(Constant.PARAM_ERROR_MESSAGE, this.operationResult.errorMessage);
        map2.put("data", this.operationResult.errorData);
        map.put(Constant.PARAM_ERROR, map2);
        return map;
    }

    public void handleError(MethodChannel.Result result) {
        result.error(this.operationResult.errorCode, this.operationResult.errorMessage, this.operationResult.errorData);
    }

    @Override // com.tekartik.sqflite.operation.BaseReadOperation, com.tekartik.sqflite.operation.Operation
    public boolean getNoResult() {
        return this.noResult;
    }

    public void handleSuccess(List<Map<String, Object>> list) {
        if (getNoResult()) {
            return;
        }
        list.add(getOperationSuccessResult());
    }

    public void handleErrorContinue(List<Map<String, Object>> list) {
        if (getNoResult()) {
            return;
        }
        list.add(getOperationError());
    }

    public class BatchOperationResult implements OperationResult {
        String errorCode;
        Object errorData;
        String errorMessage;
        Object result;

        public BatchOperationResult() {
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void success(Object obj) {
            this.result = obj;
        }

        @Override // com.tekartik.sqflite.operation.OperationResult
        public void error(String str, String str2, Object obj) {
            this.errorCode = str;
            this.errorMessage = str2;
            this.errorData = obj;
        }
    }
}
