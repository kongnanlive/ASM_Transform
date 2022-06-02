
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

package com.android.tools.profiler.support.network.okhttp;

@SuppressWarnings("unused") // This class and all methods referenced via instrumentation.
public final class OkHttp3Wrapper {

    public static void addInterceptorToBuilder(Object builder) {
        try {
//            setOkHttpClassLoader(builder);
//            Class<?> interceptorClass =
//                    myOkHttp3ClassLoader.get().loadClass(OKHTTP3_INTERCEPTOR_CLASS_NAME);
//            interceptorClass.getMethod("addToBuilder", Object.class).invoke(null, builder);
            OkHttp3Interceptor.addToBuilder(builder);
        } catch (Exception ex) {
            StudioLog.e("Could not add an OkHttp3 profiler interceptor during OkHttpClient construction", ex);
        }
    }

}