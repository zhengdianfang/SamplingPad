package com.zhengdianfang.samplingpad.task.entities

import android.os.Parcelable
import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zhengdianfang.samplingpad.App
import com.zhengdianfang.samplingpad.common.entities.Category
import com.zhengdianfang.samplingpad.common.entities.OptionItem
import com.zhengdianfang.samplingpad.common.entities.Region
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
    COMPLETE(2),
    REFUSE(4),
    CAN_NOT_VERIFY(0)
}
enum class Task_Type(val value: Int) {
    NORMAL_TASK(1),
    FOOD_TASK(2),
    NETWORK_TASK(3)
}

enum class Purpose_Type(val value: Int) {
    SUPERVISION(1),
    RISK(2),
    EVALUATE(3)
}

data class Goods(
    val id: String,
    val name: String,
    val barCode: String,
    val brand: String,
    val packageType: String,
    val specification: String,
    val description: String?,
    var level1Id: Int?,
    var level2Id: Int?,
    var level3Id: Int?,
    var level4Id: Int?,
    var level1Name: String?,
    var level2Name: String?,
    var level3Name: String?,
    var level4Name: String?
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
    val zipCode: String?,
    val chainBrand: String?
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
    var level1Id: Int?,
    var level2Id: Int?,
    var level3Id: Int?,
    var level4Id: Int?,
    var sampleLinkName: String? = "",
    var enterpriseName: String? = "",
    var state: Int? = 0,
    var foodTypeId: Int? = 1,
    var taskSource: String? = "",
    var planName: String? = "",
    var implPlanCode: String? = "",
    var detectionCompanyId: Int?,
    var detectionCompanyName: String? = "",
    var createOrgId: Int?,
    var createOrgName: String? = "",
    var createOrgAddress: String? = "",
    var createOrgAreaId: Int?,
    var createOrgAreaName: String? = "",
    var createOrgContacts: String?,
    var creatorEmail: String?,
    var creatorPhone: String?,
    var inspectionKindName: String?,
    var inspectionKindId: Int?,
    var enterpriseLicenseNumber: String?,
    var enterpriseAreaType: String?,
    var enterpriseLinkName: String?,
    var enterpriseLinkId: Int?,
    var creatorId: Int?,
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
    var specialAreaId: Int?,
    var enterpriseChain: Int?,
    var enterprisePlaceName: String?,
    var enterpriseAreaName: String?,
    var enterpriseAreaId: Int?,
    var sampleActive: Int?,
    var entrustCsNo: String?,
    var entrustId: Int?,
    var entrustName: String?,
    var entrustAddress: String?,
    var entrustContacts: String?,
    var entrustPhone: String?,
    var agencyId: Int?,
    var agencyName: String?,
    var agencyAddress: String?,
    var agencyContacts: String?,
    var agencyPhone: String?,
    var agentCountyName: String?,
    var agencyOriginAreaName: String?,
    var agencyOriginAreaId: Int?,
    var sampleSourceArea: String?,
    var producerBarcode: String?,
    var sampleBrand: String?,
    var samplePrice: Double?,
    var priceUnit: String?,
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
    var sampleAmount: String?,
    var sampleAmountForTest: Double?,
    var sampleAmountForRetest: Double?,
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
    var producerAreaId: Int?,
    var enterpriseUrl: String?,
    var producerAddress: String?,
    var producerProvincialId: Int?,
    var producerProvincialName: String?,
    var producerTownId: Int?,
    var producerTownName: String?,
    var producerCountyId: Int?,
    var producerCountyName: String?,
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
    var attachmentUnitId: String?,
    var entrustProvincialId: Int?,
    var entrustProvincialName: String?,
    var entrustTownId: Int?,
    var entrustTownName: String?,
    var entrustCountyId: Int?,
    var entrustCountyName: String?,
    var sampleReportAttachmentId: Int?,
    var beautyFoodTypeId: Int?,
    var beautyFoodType: String?,
    var wellBrandName: String?,
    var workerOneId: Int?,
    var workerTwoId: Int?,
    var enterpriseId: Int?,
    var enterprisePlaceId: Int?,
    var producerId: Int?,
    var sampleBillId: Int?,
    var sampleDateKind: String?,
    var nominalDate: String?,
    var comment: String?,
    var inspectionPackageNumber: String?,
    var samplePackingNumber: String?,
    var enforcePeople: String?,
    var sampleNcode: String?,
    var inspectionPurposeId: Int?
) : Parcelable {
    companion object {

        const val BUSINESS_CERTIFICATE = 1
        const val PRODUCE_CERTIFICATE = 2

        const val UN_CHAIN_ENTERPRISE = 0
        const val CHAIN_ENTERPRISE = 1
    }

    fun mergeGoods(goods: Goods) {
        if (!TextUtils.isEmpty(goods.name)) {
            sampleName = goods.name
        }
        if (!TextUtils.isEmpty(goods.packageType)) {
            samplePackageType = goods.packageType
        }
        if (!TextUtils.isEmpty(goods.brand)) {
            sampleBrand = goods.brand
        }
        if (!TextUtils.isEmpty(goods.specification)) {
            sampleSpecification = goods.specification
        }
        if (!TextUtils.isEmpty(goods.barCode)) {
            producerBarcode = goods.barCode
        }
        if (!TextUtils.isEmpty(goods.brand)) {
            chainBrand = goods.brand
        }
        if (goods.level1Id != null) {
            level1Id = goods.level1Id
        }
        if (goods.level2Id  != null) {
            level2Id = goods.level2Id
        }
        if (goods.level3Id  != null) {
            level3Id = goods.level3Id
        }
        if (goods.level4Id  != null) {
            level4Id = goods.level4Id
        }
        if (!TextUtils.isEmpty(level1Name)) {
            level1Name = goods.level1Name
        }
        if (!TextUtils.isEmpty(level2Name)) {
            level2Name = goods.level2Name
        }
        if (!TextUtils.isEmpty(level3Name)) {
            level3Name = goods.level3Name
        }
        if (!TextUtils.isEmpty(level4Name)) {
            level4Name = goods.level4Name
        }
    }

    fun mergeEnterprise(enterprise: Enterprise) {
        if (!TextUtils.isEmpty(enterprise.name)) {
            enterpriseName = enterprise.name
        }
        if (!TextUtils.isEmpty(enterprise.licenseNumber)) {
            enterpriseLicenseNumber = enterprise.licenseNumber
        }
        if (!TextUtils.isEmpty(enterprise.areaName)) {
            enterpriseAreaName = enterprise.areaName
        }

        if (!TextUtils.isEmpty(enterprise.areaType)) {
            enterpriseAreaType = enterprise.areaType
        }

        if (!TextUtils.isEmpty(enterprise.linkName)) {
            enterpriseLinkName = enterprise.linkName
        }

        if (!TextUtils.isEmpty(enterprise.placeName)) {
            enterprisePlaceName = enterprise.placeName
        }

        if (!TextUtils.isEmpty(enterprise.qsNo)) {
            enterpriseQsNo = enterprise.qsNo
        }

        if (!TextUtils.isEmpty(enterprise.address)) {
            enterpriseAddress = enterprise.address
        }

        if (!TextUtils.isEmpty(enterprise.annualSales)) {
            enterpriseAnnualSales = enterprise.annualSales
        }

        if (!TextUtils.isEmpty(enterprise.legalRep)) {
            enterpriseLegalRep = enterprise.legalRep
        }
        if (!TextUtils.isEmpty(enterprise.contacts)) {
            enterpriseContacts = enterprise.contacts
        }
        if (!TextUtils.isEmpty(enterprise.phone)) {
            enterprisePhone = enterprise.phone
        }
        if (!TextUtils.isEmpty(enterprise.fax)) {
            enterpriseFax = enterprise.fax
        }
        if (enterprise.chainFlag != null) {
            enterpriseChain = enterprise.chainFlag
        }
        if (enterprise.mOrP != null) {
            enterpriseMOrP = enterprise.mOrP
        }
        if (!TextUtils.isEmpty(enterprise.url)) {
            enterpriseUrl = enterprise.url
        }
        if (enterprise.addressSources != null){
            enterpriseAddressSources = enterprise.addressSources
        }
        if (!TextUtils.isEmpty(enterprise.zipCode)) {
            enterpriseZipCode = enterprise.zipCode
        }
        if (!TextUtils.isEmpty(enterprise.chainBrand)) {
            chainBrand = enterprise.chainBrand
        }
    }

    fun setInspectionKindOption(optionItem: OptionItem?) {
        inspectionKindId = optionItem?.id
        inspectionKindName = optionItem?.name
    }

    fun setEnterpriseLink(optionItem: OptionItem?) {
        enterpriseLinkName = optionItem?.name
        enterpriseLinkId = optionItem?.id
    }

    fun setSpecialAreaName(optionItem: OptionItem?) {
        specialAreaName = optionItem?.name
        specialAreaId = optionItem?.id
    }

    fun setSampleTypeOption(optionItem: OptionItem?) {
        sampleType = optionItem?.name
    }

    fun setSampleAttributeOption(optionItem: OptionItem?) {
        sampleAttribute = optionItem?.name
    }

    fun setSampleSourceOption(optionItem: OptionItem?) {
        sampleSource = optionItem?.name
    }


    fun setSamplePackageType(optionItem: OptionItem?) {
        samplePackageType = optionItem?.name
    }

    fun setSamplePackaging(checkedOption: OptionItem?) {
        samplePackaging = checkedOption?.name
    }

    fun setSampleMode(checkedOption: OptionItem?) {
        sampleMode = checkedOption?.name
    }

    fun setSampleForm(checkedOption: OptionItem?) {
        sampleForm = checkedOption?.name
    }

    fun setSampleStorageEnvironment(checkedOption: OptionItem?) {
        sampleStorageEnvironment = checkedOption?.name
    }

    fun setStoragePlaceForRetest(checkedOption: OptionItem?) {
        storagePlaceForRetest = checkedOption?.name
    }


    fun setProducerAddressInfo(province: Region?, town: Region?, county: Region?) {
        producerProvincialId = province?.id
        producerProvincialName = province?.name

        producerTownId = town?.id
        producerTownName = town?.name

        producerCountyId = county?.id
        producerCountyName = county?.name
    }

    fun setEnstrustAddressInfo(province: Region?, town: Region?, county: Region?) {
        entrustProvincialId = province?.id
        entrustProvincialName = province?.name

        entrustTownId = town?.id
        entrustTownName = town?.name

        entrustCountyId = county?.id
        entrustCountyName = county?.name
    }

    fun setEnterpriseAreaInfo(street: Region?) {
        enterpriseAreaId = street?.id
        enterpriseAreaName = street?.name
    }

    fun setAgencyOriginArea(optionItem: OptionItem?) {
        sampleSourceArea = optionItem?.name
    }

    fun mergeSelectedValuesToTaskItem(selectedLevelCategory: Array<Category?>) {
        level1Name = selectedLevelCategory[0]?.name
        level2Name = selectedLevelCategory[1]?.name
        level3Name = selectedLevelCategory[2]?.name
        level4Name = selectedLevelCategory[3]?.name

        level1Id = selectedLevelCategory[0]?.id
        level2Id = selectedLevelCategory[1]?.id
        level3Id = selectedLevelCategory[2]?.id
        level4Id = selectedLevelCategory[3]?.id
    }

    fun isMyTask() = workerOneId == App.INSTANCE.user?.id
}
data class TaskException(
    val id: Int?,
    val task_no: String?,
    val enterprise_area_id: Int?,
    val abnormal_type_id: Int?,
    val enterprise_address: String?,
    val abnormal_type_name: String?,
    val create_org_name: String?,
    val want_sample: String?,
    val enterprise_area_name: String?,
    val creator_id: String?,
    val creator_name: String?,
    val enterprise_name: String?,
    val create_date: String?,
    val callback: Int?
)

data class AttachmentIds(val id: String)

class AttachmentItem(
    var id: Int,
    var documentType: String,
    var url: String?
    ): MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }
}

class UploadItem: MultiItemEntity {
    override fun getItemType(): Int {
        return 1
    }
}

