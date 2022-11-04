package com.example.twotwoninezero.common
import android.text.TextUtils
import android.util.Patterns
import android.webkit.URLUtil
import java.util.regex.Pattern

/*
* Handle all validations here
* Created by Velmurugan 29-05-2020
* */
object Validation {

    fun isValidMailId(textToCheck: String?): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]" + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(textToCheck)
        return !(textToCheck?.length == 0 || !matcher.matches())
    }

    fun fullname_multiplespaceValidation(n:String):Boolean{
        return n.matches(".*\\s{2}.*".toRegex())
    }
    fun fullname_spaceValidation_atend(n:String):Boolean{
        return n.matches(".*\\s".toRegex())
    }
    fun fullname_beging_space_validation(n:String):Boolean{
        return n.matches("\\s.*".toRegex())
    }

    fun isValidUrl(url: String): Boolean {
        if (isEmpty(url)) return false
        val p = Patterns.WEB_URL
        val m = p.matcher(url.toLowerCase())
        return m.matches()
    }

    fun  isHttpUrl(url: String):Boolean{
        if(URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)){
            return true
        }
        else
            return false
    }

    fun isValidPwd(txt: String): Boolean {
        return txt.length >= 6
    }

    fun isValidPostCode(txt: String): Boolean {
        return txt.length >= 3
    }

    fun isValidMobile(numberToCheck: String?): Boolean {
        val MOBILE_PATTERN =
            "^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$"
        val pattern = Pattern.compile(MOBILE_PATTERN)
        val matcher = pattern.matcher(numberToCheck)
        return !(numberToCheck?.length == 0 || !matcher.matches())
    }

    fun isValidPhone(phone: String?): Boolean {
        return if (TextUtils.isEmpty(phone)) {
            false
        } else {
            Patterns.PHONE.matcher(phone).matches()
        }
    }

    fun isValidMobileNo(mobileNumber: String): Boolean {
        val regExp = Regex("^[6-9]\\d{9}$")
        var isvalid = false
        if (mobileNumber.length > 0) {
            isvalid = mobileNumber.matches(regExp)
        }
        return isvalid
    }

    fun isValidName(txt: String): Boolean {
        return txt.length >= 3
    }

    fun isEmpty(txt: String?): Boolean {
        return txt == null || txt.trim { it <= ' ' } == "" || txt.isEmpty()
    }

    fun isEmpty(txt: Int?): Boolean {
        return txt == null
    }

    fun isGreaterthanZero(txt: Int?): Boolean {
        return  txt!! <= 0
    }

    fun isGreaterthanValue(val1: Int?, val2: Int?): Boolean {
        return val1!! >= val2!!
    }

    fun isVaildPin(pin: String): Boolean {
        val regExp = Regex("^[1-9]{4}$")
        var isvalid = false
        if (pin.length > 0) {
            isvalid = pin?.matches(regExp)!!
        }
        return isvalid
    }

    fun isMICRNumber(micrNumber: String) : Boolean {
        val regExp = Regex("^[0-9]{9}$")
        var isvalid = false
        if (micrNumber.length > 0) {
            isvalid = micrNumber.matches(regExp)
        }
        return isvalid
    }
    fun isAccountNumber(accNumber: String) : Boolean {
        val regExp = Regex("^[0-9]{9,18}$")
        var isvalid = false
        if (accNumber.length > 0) {
            isvalid = accNumber.matches(regExp)
        }
        return isvalid
    }

    fun isIfscCodeValid(ifsc: String): Boolean {
        val regExp = Regex("^[A-Z]{4}[0][A-Z0-9]{6}$")
        var isvalid = false
        if (ifsc.length > 0) {
            isvalid = ifsc.matches(regExp)
        }
        return isvalid
    }

    fun isPan(pan: String): Boolean {
        val regExp = Regex("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
        var isvalid = false
        if (pan.length > 0) {
            isvalid = pan.matches(regExp)
        }
        return isvalid
    }

    fun isTan(tan: String): Boolean {
        val regExp = Regex("^[A-Z]{4}[0-9]{5}[A-Z]{1}$")
        var isvalid = false
        if (tan.length > 0) {
            isvalid = tan.matches(regExp)
        }
        return isvalid
    }

    fun isGst(gst: String): Boolean {
        val regExp = Regex("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
        var isvalid = false
        if (gst.length > 0) {
            isvalid = gst.matches(regExp)
        }
        return isvalid
    }

    fun isLicense(tan: String): Boolean {
        val regExp = Regex("^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2})|([A-Z]{2}/[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$")
        var isvalid = false
        if (tan.length > 0) {
            isvalid = tan.matches(regExp)
        }
        return isvalid
    }

    fun isValidCreditCard(txt: String): Boolean {
        return txt.length == 16
    }

    fun isValidCvv(cvv: String): Boolean {
        return cvv.length == 3
    }

    fun isAadhaar(aadhaar: String?): Boolean {
        val regExp = Regex("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$")
        var isvalid = false
        if (aadhaar!!.length > 0) {
            isvalid = aadhaar.matches(regExp)
        }
        return isvalid
    }

    fun isValidPincode(pincode: String?): Boolean {
        val regExp = Regex("^[1-9][0-9]{5}$")
        var isvalid = false
        if (pincode!!.length > 0) {
            isvalid = pincode.matches(regExp)
        }
        return isvalid
    }

    fun isValidOtp(otp: String): Boolean {
        return otp.length <= 6
    }

    fun isEmpty(list: List<Any>?): Boolean {
        return list == null || list.isEmpty()
    }

    fun isEmpty(list: Array<String>?): Boolean {
        return list == null || list.isEmpty()
    }
}