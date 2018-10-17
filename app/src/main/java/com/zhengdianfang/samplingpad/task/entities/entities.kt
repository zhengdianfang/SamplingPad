package com.zhengdianfang.samplingpad.task.entities

data class StatusCount (
    var countSubmit: Int = 0,
    var countRefuse: Int = 0,
    var countEnd: Int = 0,
    var countAbnormal: Int = 0,
    var countAll: Int = 0
)

enum class Task_Status(val value: Int) {
    WAIT_VERIFY(1),
    CANCEL(3),
    COMPLETE(7),
    REFUSE(4),
    CAN_NOT_VERIFY(0)
}

data class TaskItem(

    val code: String = "",
    val updateDate: String = "",
    val sampleName: String = "",
    val level1Name: String = "",
    val level2Name: String = "",
    val level3Name: String = "",
    val level4Name: String = "",
    val sampleLinkName: String = "",
    val enterpriseName: String = "",
    val state: Int = 0
)