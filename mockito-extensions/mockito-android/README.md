# mockito-android

Mockito mock maker for Android, supporting inline mocking of final classes and methods via JVMTI-based bytecode instrumentation. Requires Android API level 28 (Android P) or higher.

## Origin

The Java sources under `org.mockito.android.internal.creation.inline` (originally `com.android.dx.mockito.inline`), the native code in `src/main/cpp/`, and the `MockMethodDispatcher` class in the sibling `mockito-android-dispatcher` module are forked from [linkedin/dexmaker](https://github.com/linkedin/dexmaker) module `dexmaker-mockito-inline` (version 2.28.4), licensed under the Apache License 2.0.

The `dispatcher.jar` resource is built from source by the `mockito-android-dispatcher` module, which compiles `MockMethodDispatcher.java` to DEX. This class is loaded into the Android bootstrap classloader at runtime so that JVMTI-instrumented method entry hooks can dispatch to `MockMethodAdvice`.

Key modifications from the upstream fork:
- Replaced `AsyncTask.THREAD_POOL_EXECUTOR` with `Executors.newSingleThreadExecutor()` in `InlineDexmakerMockMaker`
- Replaced `ArraySet` with `HashSet` in `InlineDexmakerMockMaker`
- Added `org.mockito.android.internal.creation.inline` to `DexmakerStackTraceCleaner` filter
- Added `InlineAndroidMockMaker` entry-point class that delegates to `InlineDexmakerMockMaker`

The `slicer` library in `src/main/cpp/external/slicer/` originates from the Android Open Source Project (AOSP).
