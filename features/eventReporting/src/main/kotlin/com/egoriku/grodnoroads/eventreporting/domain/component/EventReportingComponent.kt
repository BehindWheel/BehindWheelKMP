package com.egoriku.grodnoroads.eventreporting.domain.component

import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult

interface EventReportingComponent {

    fun report(reportingResult: ReportingResult)
}