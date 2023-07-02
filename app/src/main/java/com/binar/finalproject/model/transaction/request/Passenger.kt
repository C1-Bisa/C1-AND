package com.binar.finalproject.model.transaction.request


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("birthday")
    var birthday: String?,
    @SerializedName("expired")
    var expired: String?,
    @SerializedName("family_name")
    var familyName: String?,
    @SerializedName("issued_country")
    var issuedCountry: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("nationality")
    var nationality: String?,
    @SerializedName("nik")
    var nik: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("seatDeparture")
    var seatDeparture : String?,
    @SerializedName("seatReturn")
    var seatReturn : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(birthday)
        parcel.writeString(expired)
        parcel.writeString(familyName)
        parcel.writeString(issuedCountry)
        parcel.writeString(name)
        parcel.writeString(nationality)
        parcel.writeValue(nik)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(seatDeparture)
        parcel.writeString(seatReturn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Passenger> {
        override fun createFromParcel(parcel: Parcel): Passenger {
            return Passenger(parcel)
        }

        override fun newArray(size: Int): Array<Passenger?> {
            return arrayOfNulls(size)
        }
    }
}