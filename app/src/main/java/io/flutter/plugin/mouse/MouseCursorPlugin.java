package io.flutter.plugin.mouse;

import android.view.PointerIcon;
import androidx.core.view.PointerIconCompat;
import io.flutter.embedding.engine.systemchannels.MouseCursorChannel;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class MouseCursorPlugin {
    private static HashMap<String, Integer> systemCursorConstants;
    private final MouseCursorViewDelegate mView;
    private final MouseCursorChannel mouseCursorChannel;

    public interface MouseCursorViewDelegate {
        PointerIcon getSystemPointerIcon(int i);

        void setPointerIcon(PointerIcon pointerIcon);
    }

    public MouseCursorPlugin(MouseCursorViewDelegate mouseCursorViewDelegate, MouseCursorChannel mouseCursorChannel) {
        this.mView = mouseCursorViewDelegate;
        this.mouseCursorChannel = mouseCursorChannel;
        mouseCursorChannel.setMethodHandler(new MouseCursorChannel.MouseCursorMethodHandler() { // from class: io.flutter.plugin.mouse.MouseCursorPlugin.1
            @Override // io.flutter.embedding.engine.systemchannels.MouseCursorChannel.MouseCursorMethodHandler
            public void activateSystemCursor(String str) {
                MouseCursorPlugin.this.mView.setPointerIcon(MouseCursorPlugin.this.resolveSystemCursor(str));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PointerIcon resolveSystemCursor(String str) {
        if (systemCursorConstants == null) {
            systemCursorConstants = new HashMap<String, Integer>() { // from class: io.flutter.plugin.mouse.MouseCursorPlugin.2
                private static final long serialVersionUID = 1;

                {
                    put("alias", 1010);
                    Integer numValueOf = Integer.valueOf(PointerIconCompat.TYPE_ALL_SCROLL);
                    put("allScroll", numValueOf);
                    put("basic", 1000);
                    put("cell", 1006);
                    put("click", 1002);
                    put("contextMenu", 1001);
                    put("copy", Integer.valueOf(PointerIconCompat.TYPE_COPY));
                    Integer numValueOf2 = Integer.valueOf(PointerIconCompat.TYPE_NO_DROP);
                    put("forbidden", numValueOf2);
                    put("grab", Integer.valueOf(PointerIconCompat.TYPE_GRAB));
                    put("grabbing", Integer.valueOf(PointerIconCompat.TYPE_GRABBING));
                    put("help", 1003);
                    put("move", numValueOf);
                    put("none", 0);
                    put("noDrop", numValueOf2);
                    put("precise", 1007);
                    put("text", 1008);
                    Integer numValueOf3 = Integer.valueOf(PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW);
                    put("resizeColumn", numValueOf3);
                    Integer numValueOf4 = Integer.valueOf(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW);
                    put("resizeDown", numValueOf4);
                    Integer numValueOf5 = Integer.valueOf(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
                    put("resizeUpLeft", numValueOf5);
                    Integer numValueOf6 = Integer.valueOf(PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW);
                    put("resizeDownRight", numValueOf6);
                    put("resizeLeft", numValueOf3);
                    put("resizeLeftRight", numValueOf3);
                    put("resizeRight", numValueOf3);
                    put("resizeRow", numValueOf4);
                    put("resizeUp", numValueOf4);
                    put("resizeUpDown", numValueOf4);
                    put("resizeUpLeft", numValueOf6);
                    put("resizeUpRight", numValueOf5);
                    put("resizeUpLeftDownRight", numValueOf6);
                    put("resizeUpRightDownLeft", numValueOf5);
                    put("verticalText", 1009);
                    put("wait", 1004);
                    put("zoomIn", 1018);
                    put("zoomOut", 1019);
                }
            };
        }
        return this.mView.getSystemPointerIcon(systemCursorConstants.getOrDefault(str, 1000).intValue());
    }

    public void destroy() {
        this.mouseCursorChannel.setMethodHandler(null);
    }
}
