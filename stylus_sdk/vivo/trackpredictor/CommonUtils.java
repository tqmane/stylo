package com.vivo.trackpredictor.utils;

/* loaded from: classes4.dex */
public class CommonUtils {
    /* JADX WARN: Removed duplicated region for block: B:28:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void copyAssetResource2File(android.content.Context r5, java.lang.String r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            android.content.res.AssetManager r5 = r5.getAssets()
            java.io.InputStream r5 = r5.open(r6)
            java.io.File r6 = new java.io.File
            r6.<init>(r7)
            r7 = 1
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L3a
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L3a
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
        L18:
            int r2 = r5.read(r0)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r3 = -1
            if (r2 == r3) goto L24
            r3 = 0
            r1.write(r0, r3, r2)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            goto L18
        L24:
            r1.flush()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r5.close()
        L2a:
            r1.close()
        L2d:
            r6.setReadable(r7)
            goto L58
        L31:
            r0 = move-exception
            goto L59
        L33:
            r0 = move-exception
            goto L3e
        L35:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L59
        L3a:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L3e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31
            r2.<init>()     // Catch: java.lang.Throwable -> L31
            java.lang.String r3 = "IOException! "
            r2.append(r3)     // Catch: java.lang.Throwable -> L31
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L31
            r2.append(r0)     // Catch: java.lang.Throwable -> L31
            r2.toString()     // Catch: java.lang.Throwable -> L31
            r5.close()
            if (r1 == 0) goto L2d
            goto L2a
        L58:
            return
        L59:
            r5.close()
            if (r1 == 0) goto L61
            r1.close()
        L61:
            r6.setReadable(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vivo.trackpredictor.utils.CommonUtils.copyAssetResource2File(android.content.Context, java.lang.String, java.lang.String):void");
    }
}
