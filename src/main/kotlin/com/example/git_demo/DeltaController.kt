package com.example.git_demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeltaController(
    private val deltaService: DeltaService
) {

    @PostMapping("/delta")
    fun commit(@RequestBody data : CommitRequest){
        deltaService.diffAndSave(data.id, data.data)
    }

    @GetMapping("/delta/{id}/{commitId}")
    fun get(@PathVariable id : Long, @PathVariable commitId: Long) = deltaService.findLatestData(id, CommitId(commitId))

    @GetMapping("/delta/diff/{id}/{commitId}")
    fun getHistory(@PathVariable id : Long, @PathVariable commitId: Long) = deltaService.getDiff(id, CommitId(commitId))

}