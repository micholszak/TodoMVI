package com.shopper.app.view.common.model

interface AdapterItem {

    fun areItemsTheSame(other: AdapterItem): Boolean

    fun areContentsTheSame(other: AdapterItem): Boolean
}
