package com.labactivity.gakumate

import java.io.Serializable
import java.sql.Time
import java.util.Date

data class Tasks(
    var date: Date,
    var time: Time,
    var tasks: String
) : Serializable