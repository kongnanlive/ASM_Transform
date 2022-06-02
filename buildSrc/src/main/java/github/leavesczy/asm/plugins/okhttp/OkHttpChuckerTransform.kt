package github.leavesczy.asm.plugins.okhttp

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.internal.pipeline.TransformManager
import github.leavesczy.asm.base.BaseTransform
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

class OkHttpChuckerTransform : BaseTransform() {

    override fun modifyClass(byteArray: ByteArray): ByteArray {
        val writer = ClassWriter(ClassWriter.COMPUTE_MAXS)
        var visitor: ClassVisitor = writer
        visitor = OkHttpAdapter(visitor)
        val cr = ClassReader(byteArray)
        cr.accept(visitor, 0)
        return writer.toByteArray()
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }
}