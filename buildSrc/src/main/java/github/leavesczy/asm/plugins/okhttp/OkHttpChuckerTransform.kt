package github.leavesczy.asm.plugins.okhttp

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.internal.pipeline.TransformManager
import github.leavesczy.asm.base.BaseTransform
import github.leavesczy.asm.utils.Log
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class OkHttpChuckerTransform : BaseTransform() {

    override fun modifyClass(byteArray: ByteArray): ByteArray {
        val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
        var visitor: ClassVisitor = writer
//        visitor = InitializerAdapter(visitor)
//        visitor = HttpURLAdapter(visitor)
        visitor = OkHttpAdapter(visitor)
        val cr = ClassReader(byteArray)
        cr.accept(visitor, 0)
        return writer.toByteArray()

//        val classReader = ClassReader(byteArray)
//        val className = classReader.className
//        val superName = classReader.superName
//        Log.log("className: $className superName: $superName")
//
//        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
//        val classVisitor = OkHttpAdapter(classWriter)
//        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
//        return classWriter.toByteArray()

//        return byteArray
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }
}