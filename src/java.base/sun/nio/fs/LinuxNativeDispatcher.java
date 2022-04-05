/*
 * Copyright (c) 2008, 2019, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package sun.nio.fs;

/**
 * Linux specific system calls.
 */

class LinuxNativeDispatcher extends UnixNativeDispatcher {
    private LinuxNativeDispatcher() { }

   /**
    * FILE *setmntent(const char *filename, const char *type);
    */
    static long setmntent(byte[] filename, byte[] type) throws UnixException {
        NativeBuffer pathBuffer = NativeBuffers.asNativeBuffer(filename);
        NativeBuffer typeBuffer = NativeBuffers.asNativeBuffer(type);
        try {
            return setmntent0(pathBuffer.address(), typeBuffer.address());
        } finally {
            typeBuffer.release();
            pathBuffer.release();
        }
    }
    private static native long setmntent0(long pathAddress, long typeAddress)
        throws UnixException;

    /**
     * int getmntent(FILE *fp, struct mnttab *mp, int len);
     */

    static int getmntent(long fp, UnixMountEntry entry, int buflen) throws UnixException {
        NativeBuffer buffer = NativeBuffers.getNativeBuffer(buflen);
        try {
            return getmntent0(fp, entry, buffer.address(), buflen);
        } finally {
            buffer.release();
        }
    }

    static native int getmntent0(long fp, UnixMountEntry entry, long buffer, int bufLen)
        throws UnixException;

    /**
     * int endmntent(FILE* filep);
     */
    static native void endmntent(long stream) throws UnixException;

    // initialize
    private static native void init();

    static {
        jdk.internal.loader.BootLoader.loadLibrary("nio");
        init();
    }
}
