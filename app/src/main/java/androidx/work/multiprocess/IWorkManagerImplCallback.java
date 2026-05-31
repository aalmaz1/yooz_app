package androidx.work.multiprocess;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public interface IWorkManagerImplCallback extends IInterface {
    public static final String DESCRIPTOR = "androidx.work.multiprocess.IWorkManagerImplCallback";

    public static class Default implements IWorkManagerImplCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.work.multiprocess.IWorkManagerImplCallback
        public void onFailure(String error) throws RemoteException {
        }

        @Override // androidx.work.multiprocess.IWorkManagerImplCallback
        public void onSuccess(byte[] response) throws RemoteException {
        }
    }

    void onFailure(String error) throws RemoteException;

    void onSuccess(byte[] response) throws RemoteException;

    public static abstract class Stub extends Binder implements IWorkManagerImplCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IWorkManagerImplCallback.DESCRIPTOR);
        }

        public static IWorkManagerImplCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IWorkManagerImplCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWorkManagerImplCallback)) {
                return (IWorkManagerImplCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IWorkManagerImplCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWorkManagerImplCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.createByteArray());
            } else if (code == 2) {
                onFailure(data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IWorkManagerImplCallback {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return IWorkManagerImplCallback.DESCRIPTOR;
            }

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // androidx.work.multiprocess.IWorkManagerImplCallback
            public void onSuccess(byte[] response) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWorkManagerImplCallback.DESCRIPTOR);
                    parcelObtain.writeByteArray(response);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.work.multiprocess.IWorkManagerImplCallback
            public void onFailure(String error) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IWorkManagerImplCallback.DESCRIPTOR);
                    parcelObtain.writeString(error);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
