package com.example.git_demo

import com.example.git_demo.CompressionUtils.compressString

@JvmInline
value class CommitId(val id: Long) {
    operator fun minus(i: Int): CommitId? {
        return CommitId(id - i)
    }
}

@JvmInline
value class Commit private constructor(private val value: ByteArray) {

    fun get(): String {
        return CompressionUtils.decompressString(value)
    }

    companion object {
        fun build(value: String): Commit {
            compressString(value).let {
                return Commit(it)
            }
        }
    }
}