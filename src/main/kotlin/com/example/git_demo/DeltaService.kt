package com.example.git_demo

import com.github.difflib.DiffUtils
import com.github.difflib.UnifiedDiffUtils
import com.github.difflib.patch.Patch
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import org.springframework.stereotype.Service
import java.util.*


@Service
class DeltaService {
    private val versionControlRepository: HashMap<Long, MutableList<Commit>> = HashMap()
    private val dmp = DiffMatchPatch()

    fun diffAndSave(id: Long, newData: String): Long {
        val origin = findLatestData(id)
        val diffs = dmp.diffMain(origin, newData)
        val delta = dmp.diffToDelta(diffs)
        return save(id, Commit.build(delta))
    }

    fun findLatestData(id: Long, commitId: CommitId? = null): String {
        val deltas = versionControlRepository[id] ?: return ""
        return applyDeltas(deltas, commitId ?: CommitId(deltas.size.toLong()))
    }

    fun getDiff(id: Long, commitId: CommitId): String {
        val before = findLatestData(id, commitId - 1).split("\n")
        val after = findLatestData(id, commitId).split("\n")

        val patch: Patch<String> = DiffUtils.diff<String>(before, after)
        val unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("Original", "Revised", before, patch, 3)

        return unifiedDiff.joinToString("\n")
}

private fun applyDeltas(commits: List<Commit>, commitId: CommitId): String {
    var data = ""
    for (i in 0 until commitId.id.toInt()) {
        val delta = commits[i]
        val diffs = dmp.diffFromDelta(data, delta.get())
        val patches = dmp.patchMake(diffs)
        data = dmp.patchApply(patches, data)[0].toString()
    }
    return data
}


private fun save(id: Long, commit: Commit): Long {
    val deltas = versionControlRepository.computeIfAbsent(id) { mutableListOf() }
    deltas.add(commit)
    return deltas.size.toLong()
}
}
