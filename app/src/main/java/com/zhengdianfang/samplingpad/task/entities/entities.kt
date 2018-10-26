package com.zhengdianfang.samplingpad.task.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class StatusCount (
    var countSubmit: Int = 0,
    var countRefuse: Int = 0,
    var countEnd: Int = 0,
    var countAbnormal: Int = 0,
    var countAll: Int = 0,
    var retireCount: Int = 0
)

enum class Task_Status(val value: Int) {
    WAIT_VERIFY(1),
    CANCEL(3),
    COMPLETE(7),
    REFUSE(4),
    CAN_NOT_VERIFY(0)
}
enum class Task_Type(val value: Int) {
    NORMAL_TASK(1),
    FOOD_TASK(2),
    NETWORK_TASK(3)
}

@Parcelize
data class TaskItem(
    var id: String = "",
    var code: String? = "",
    var updateDate: String? = "",
    var sampleName: String? = "",
    var level1Name: String? = "",
    var level2Name: String? = "",
    var level3Name: String? = "",
    var level4Name: String? = "",
    var sampleLinkName: String? = "",
    var enterpriseName: String? = "",
    var state: Int? = 0,
    var foodTypeId: Int? = 1,
    var taskSource: String? = "",
    var planName: String? = "",
    var implPlanCode: String? = "",
    var detectionCompanyName: String? = "",
    var createOrgName: String? = "",
    var createOrgAddress: String? = "",
    var createOrgAreaName: String? = "",
    var createOrgContacts: String? = "",
    var creatorEmail: String? = "",
    var creatorPhone: String? = "",
    var inspectionKindName: String? = "",
    var enterpriseLicenseNumber: String?,
    var enterpriseAreaType: String?,
    var enterpriseLinkName: String?,
    var enterpriseAddress: String?,
    var enterpriseMOrP: Int?,
    var enterpriseQsNo: String?,
    var enterpriseLegalRep: String?,
    var enterpriseContacts: String?,
    var enterprisePhone: String?,
    var enterpriseFax :String?,
    var enterpriseZipCode: String?,
    var enterpriseAnnualSales: String?,
    var specialAreaName: String?,
    var enterpriseChain: Int?,
    var enterprisePlaceName: String?,
    var enterpriseAreaName: String?,
    var producerActive: Int?,
    var entrustCsNo: String?,
    var entrustName: String?,
    var entrustAddress: String?,
    var entrustContacts: String?,
    var entrustPhone: String?,
    var agencyName: String?,
    var agencyAddress: String?,
    var agencyContacts: String?,
    var agencyPhone: String?,
    var producerBarcode :String?,
    var sampleBrand: String?,
    var samplePrice: Double?,
    var sampleType: String?,
    var sampleAttribute: String?,
    var sampleSource: String?,
    var samplePackageType: String?,
    var samplePackaging: String?,
    var sampleProductDate: Long?,
    var sampleBatchNo: String?,
    var sampleQgp: String?,
    var sampleSpecification: String?,
    var sampleQualityLevel: String?,
    var sampleMode: String?,
    var sampleForm: String?,
    var sampleAmount: Int?,
    var sampleAmountForTest: Int?,
    var sampleAmountForRetest: Int?,
    var sampleStorageEnvironment: String?,
    var storagePlaceForRetest: String?,
    var lableStandard: String?,
    var thirdLicenseNumber: String?,
    var thirdName: String?,
    var thirdPlatformName: String?,
    var thirdQsNoEditText: String?,
    var thirdQsNo: String?,
    var thirdUrl: String?,
    var producerLicenseNumber: String?,
    var producerCsNo: String?,
    var producerAreaName: String?,
    var enterpriseUrl: String?,
    var producerAddress: String?,
    var producerContacts: String?,
    var producerPhone: String?,
    var enterpriseAddressSources: Int?,
    var producerName: String?,
    var sampleDate: Long?,
    var entrustLicenseNumber: String?,
    var longitude: Double?,
    var latitude: Double?,
    var abnormalTypeName: String?

) : Parcelable {
    companion object {

        const val BUSINESS_CERTIFICATE = 1
        const val PRODUCE_CERTIFICATE = 2

        const val UN_CHAIN_ENTERPRISE = 0
        const val CHAIN_ENTERPRISE = 1
        const val CHAIN_BRAND = 2
    }
}