package com.zhengdianfang.samplingpad.task.entities

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
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

data class Goods(
    val id: String,
    val name: String,
    val barCode: String,
    val brand: String,
    val packageType: String,
    val specification: String,
    val description: String?
)

data class Enterprise(
    val id: String?,
    val name: String?,
    val licenseNumber: String?,
    val areaName: String?,
    val areaType: String?,
    val linkName: String?,
    val placeName: String?,
    val qsNo: String?,
    val address: String?,
    val annualSales: String?,
    val legalRep: String?,
    val contacts: String?,
    val phone: String?,
    val fax: String?,
    val chainFlag: Int?,
    val mOrP: Int?,
    val url: String?,
    val addressSources: Int?,
    val zipCode: String?
)

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
    var enterpriseFax: String?,
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
    var producerBarcode: String?,
    var sampleBrand: String?,
    var samplePrice: Double?,
    var sampleType: String?,
    var sampleAttribute: String?,
    var sampleSource: String?,
    var samplePackageType: String?,
    var samplePackaging: String?,
    var sampleProductDate: String?,
    var sampleBatchNo: String?,
    var sampleQgp: Int?,
    var sampleQgpUnit: String?,
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
    var sampleDate: String?,
    var entrustLicenseNumber: String?,
    var longitude: Double?,
    var latitude: Double?,
    var abnormalTypeName: String?,
    var chainBrand: String?,
    var entrustActive: Int?,
    var sampleInspectAmountUnit: String?,
    var samplePreparationUnit: String?,
    var attachmentUnitId: String? = id

) : Parcelable {
    companion object {

        const val BUSINESS_CERTIFICATE = 1
        const val PRODUCE_CERTIFICATE = 2

        const val UN_CHAIN_ENTERPRISE = 0
        const val CHAIN_ENTERPRISE = 1
    }

    fun mergeGoods(goods: Goods) {
        sampleName = goods.name
        samplePackageType = goods.packageType
        sampleBrand = goods.brand
        sampleSpecification = goods.specification
        producerBarcode = goods.barCode
    }

    fun mergeEnterprise(enterprise: Enterprise) {
        enterpriseName = enterprise.name
        enterpriseLicenseNumber = enterprise.licenseNumber
        enterpriseAreaName = enterprise.areaName
        enterpriseAreaType = enterprise.areaType
        enterpriseLinkName = enterprise.linkName
        enterprisePlaceName = enterprise.placeName
        enterpriseQsNo = enterprise.qsNo
        enterpriseAddress = enterprise.address
        enterpriseAnnualSales = enterprise.annualSales
        enterpriseLegalRep = enterprise.legalRep
        enterpriseContacts = enterprise.contacts
        enterprisePhone = enterprise.phone
        enterpriseFax = enterprise.fax
        enterpriseChain = enterprise.chainFlag
        enterpriseMOrP = enterprise.mOrP
        enterpriseUrl = enterprise.url
        enterpriseAddressSources = enterprise.addressSources
        enterpriseZipCode = enterprise.zipCode
    }

    fun mergeProduce(enterprise: Enterprise) {
        producerLicenseNumber = enterprise.licenseNumber
        producerName = enterprise.name
        producerAddress = enterprise.address
        producerAreaName = enterprise.areaName
        producerContacts = enterprise.contacts
        producerPhone = enterprise.phone
    }

    fun mergeEntrust(enterprise: Enterprise) {
        entrustLicenseNumber = enterprise.licenseNumber
        entrustName = enterprise.name
        entrustAddress = enterprise.address
        entrustContacts = enterprise.contacts
        entrustPhone = enterprise.phone
    }
}

data class AttachmentIds(val id: String)

class AttachmentItem(val url: String): MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }
}

class UploadItem: MultiItemEntity {
    override fun getItemType(): Int {
        return 1
    }
}

