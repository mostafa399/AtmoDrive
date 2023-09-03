package com.mostafahelal.AtmoDrive.auth.data.data_source.Utils

sealed class Resource<T> (
    val data : T? = null,
    val message : String? = null
){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data,message)

    fun isSuccessful():Boolean{
        return this is Success
    }
    fun isFailed():Boolean{
        return this is Error
    }

}
