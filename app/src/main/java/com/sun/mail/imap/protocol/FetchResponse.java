package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.util.ASCIIUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class FetchResponse extends IMAPResponse {
    private static final char[] HEADER = {'.', 'H', 'E', 'A', 'D', 'E', 'R'};
    private static final char[] TEXT = {'.', 'T', 'E', 'X', 'T'};
    private Map<String, Object> extensionItems;
    private final FetchItem[] fitems;
    private Item[] items;

    public FetchResponse(Protocol protocol) throws ProtocolException, IOException {
        super(protocol);
        this.fitems = null;
        parse();
    }

    public FetchResponse(IMAPResponse iMAPResponse) throws ProtocolException, IOException {
        this(iMAPResponse, null);
    }

    public FetchResponse(IMAPResponse iMAPResponse, FetchItem[] fetchItemArr) throws ProtocolException, IOException {
        super(iMAPResponse);
        this.fitems = fetchItemArr;
        parse();
    }

    public int getItemCount() {
        return this.items.length;
    }

    public Item getItem(int i) {
        return this.items[i];
    }

    public <T extends Item> T getItem(Class<T> cls) {
        int i = 0;
        while (true) {
            Item[] itemArr = this.items;
            if (i >= itemArr.length) {
                return null;
            }
            if (cls.isInstance(itemArr[i])) {
                return cls.cast(this.items[i]);
            }
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x003a, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T extends com.sun.mail.imap.protocol.Item> T getItem(com.sun.mail.iap.Response[] r7, int r8, java.lang.Class<T> r9) {
        /*
            r0 = 0
            if (r7 != 0) goto L4
            return r0
        L4:
            r1 = 0
            r2 = r1
        L6:
            int r3 = r7.length
            if (r2 >= r3) goto L3d
            r3 = r7[r2]
            if (r3 == 0) goto L3a
            boolean r4 = r3 instanceof com.sun.mail.imap.protocol.FetchResponse
            if (r4 == 0) goto L3a
            com.sun.mail.imap.protocol.FetchResponse r3 = (com.sun.mail.imap.protocol.FetchResponse) r3
            int r3 = r3.getNumber()
            if (r3 == r8) goto L1a
            goto L3a
        L1a:
            r3 = r7[r2]
            com.sun.mail.imap.protocol.FetchResponse r3 = (com.sun.mail.imap.protocol.FetchResponse) r3
            r4 = r1
        L1f:
            com.sun.mail.imap.protocol.Item[] r5 = r3.items
            int r6 = r5.length
            if (r4 >= r6) goto L3a
            r5 = r5[r4]
            boolean r5 = r9.isInstance(r5)
            if (r5 == 0) goto L37
            com.sun.mail.imap.protocol.Item[] r7 = r3.items
            r7 = r7[r4]
            java.lang.Object r7 = r9.cast(r7)
            com.sun.mail.imap.protocol.Item r7 = (com.sun.mail.imap.protocol.Item) r7
            return r7
        L37:
            int r4 = r4 + 1
            goto L1f
        L3a:
            int r2 = r2 + 1
            goto L6
        L3d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.imap.protocol.FetchResponse.getItem(com.sun.mail.iap.Response[], int, java.lang.Class):com.sun.mail.imap.protocol.Item");
    }

    public static <T extends Item> List<T> getItems(Response[] responseArr, int i, Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        if (responseArr == null) {
            return arrayList;
        }
        for (int i2 = 0; i2 < responseArr.length; i2++) {
            Response response = responseArr[i2];
            if (response != null && (response instanceof FetchResponse) && ((FetchResponse) response).getNumber() == i) {
                FetchResponse fetchResponse = (FetchResponse) responseArr[i2];
                int i3 = 0;
                while (true) {
                    Item[] itemArr = fetchResponse.items;
                    if (i3 < itemArr.length) {
                        if (cls.isInstance(itemArr[i3])) {
                            arrayList.add(cls.cast(fetchResponse.items[i3]));
                        }
                        i3++;
                    }
                }
            }
        }
        return arrayList;
    }

    public Map<String, Object> getExtensionItems() {
        return this.extensionItems;
    }

    private void parse() throws ParsingException {
        if (!isNextNonSpace('(')) {
            throw new ParsingException("error in FETCH parsing, missing '(' at index " + this.index);
        }
        ArrayList arrayList = new ArrayList();
        skipSpaces();
        while (this.index < this.size) {
            Item item = parseItem();
            if (item != null) {
                arrayList.add(item);
            } else if (!parseExtensionItem()) {
                throw new ParsingException("error in FETCH parsing, unrecognized item at index " + this.index + ", starts with \"" + next20() + "\"");
            }
            if (isNextNonSpace(')')) {
                this.items = (Item[]) arrayList.toArray(new Item[arrayList.size()]);
                return;
            }
        }
        throw new ParsingException("error in FETCH parsing, ran off end of buffer, size " + this.size);
    }

    private String next20() {
        if (this.index + 20 > this.size) {
            return ASCIIUtility.toString(this.buffer, this.index, this.size);
        }
        return ASCIIUtility.toString(this.buffer, this.index, this.index + 20) + "...";
    }

    private Item parseItem() throws ParsingException {
        boolean z;
        byte b = this.buffer[this.index];
        if (b != 66) {
            if (b != 73) {
                if (b != 77) {
                    if (b != 82) {
                        if (b != 85) {
                            if (b != 98) {
                                if (b != 105) {
                                    if (b != 109) {
                                        if (b != 114) {
                                            if (b != 117) {
                                                if (b != 69) {
                                                    if (b != 70) {
                                                        if (b != 101) {
                                                            if (b != 102) {
                                                                return null;
                                                            }
                                                        }
                                                    }
                                                    if (match(FLAGS.name)) {
                                                        return new FLAGS(this);
                                                    }
                                                    return null;
                                                }
                                                if (match(ENVELOPE.name)) {
                                                    return new ENVELOPE(this);
                                                }
                                                return null;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (match(UID.name)) {
                            return new UID(this);
                        }
                        return null;
                    }
                    if (match(RFC822SIZE.name)) {
                        return new RFC822SIZE(this);
                    }
                    if (!match(RFC822DATA.name)) {
                        return null;
                    }
                    if (match(HEADER)) {
                        z = true;
                    } else {
                        match(TEXT);
                        z = false;
                    }
                    return new RFC822DATA(this, z);
                }
                if (match(MODSEQ.name)) {
                    return new MODSEQ(this);
                }
                return null;
            }
            if (match(INTERNALDATE.name)) {
                return new INTERNALDATE(this);
            }
            return null;
        }
        if (match(BODYSTRUCTURE.name)) {
            return new BODYSTRUCTURE(this);
        }
        if (!match(BODY.name)) {
            return null;
        }
        if (this.buffer[this.index] == 91) {
            return new BODY(this);
        }
        return new BODYSTRUCTURE(this);
    }

    private boolean parseExtensionItem() throws ParsingException {
        if (this.fitems == null) {
            return false;
        }
        int i = 0;
        while (true) {
            FetchItem[] fetchItemArr = this.fitems;
            if (i >= fetchItemArr.length) {
                return false;
            }
            if (match(fetchItemArr[i].getName())) {
                if (this.extensionItems == null) {
                    this.extensionItems = new HashMap();
                }
                this.extensionItems.put(this.fitems[i].getName(), this.fitems[i].parseItem(this));
                return true;
            }
            i++;
        }
    }

    private boolean match(char[] cArr) {
        int length = cArr.length;
        int i = this.index;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (Character.toUpperCase((char) this.buffer[i]) != cArr[i2]) {
                return false;
            }
            i2 = i4;
            i = i3;
        }
        this.index += length;
        return true;
    }

    private boolean match(String str) {
        int length = str.length();
        int i = this.index;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (Character.toUpperCase((char) this.buffer[i]) != str.charAt(i2)) {
                return false;
            }
            i2 = i4;
            i = i3;
        }
        this.index += length;
        return true;
    }
}
