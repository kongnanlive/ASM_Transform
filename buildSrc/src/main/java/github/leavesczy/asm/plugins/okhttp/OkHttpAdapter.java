/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.leavesczy.asm.plugins.okhttp;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Wraps OkHttp library APIs to monitor networking activities.
 */
final class OkHttpAdapter extends ClassVisitor implements Opcodes {

    private static final String OKHTTP3_BUILDER_CLASS = "okhttp3/OkHttpClient$Builder";

    OkHttpAdapter(ClassVisitor classVisitor) {
        super(ASM6, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(
            int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return (mv != null) ? new MethodAdapter(mv) : null;
    }

    private static final class MethodAdapter extends MethodVisitor implements Opcodes {

        public MethodAdapter(MethodVisitor mv) {
            super(ASM6, mv);
        }

        @Override
        public void visitMethodInsn(
                int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            if (owner.equals(OKHTTP3_BUILDER_CLASS) && isConstructor(opcode, name, desc) && !itf) {
//                super.visitMethodInsn(opcode, owner, name, desc, itf);
//                super.visitInsn(DUP);
//                super.visitMethodInsn(INVOKESTATIC, OkHttpAdapter.OKHTTP3_WRAPPER,
//                        "addInterceptorToBuilder", "(Ljava/lang/Object;)V", false);

                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "github/leavesczy/asm/okHttp/OkHttp3Wrapper",
                        "addInterceptorToBuilder", "(Ljava/lang/Object;)V", false);
//                mv.visitVarInsn(Opcodes.ALOAD, 0);


//                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/demo/network/plugin/OkHttpHook",
//                        "getInstance", "()Lcom/example/demo/network/plugin/OkHttpHook;", false);
//                mv.visitVarInsn(Opcodes.ALOAD, 0);
                //mv.visitTypeInsn(Opcodes.CHECKCAST, "okhttp3/OkHttpClient");
//                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/example/demo/network/plugin/OkHttpHook", "addInterceptor", "(Lokhttp3/OkHttpClient;)V", false);
            }
        }

        private static boolean isConstructor(int opcode, String name, String desc) {
            return opcode == INVOKESPECIAL && name.equals("<init>") && desc.equals("()V");
        }
    }
}