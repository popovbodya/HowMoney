package ru.popov.bodya.howmoney.presentation.mvp.enrollment

import ru.popov.bodya.core.mvp.AppView
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategoryBalance

/**
 *  @author popovbodya
 */
interface EnrollmentView: AppView {

    fun showDiagram(enrollmentCategoryMap: Map<EnrollmentCategory, Double>, finalBalance: Double)

}