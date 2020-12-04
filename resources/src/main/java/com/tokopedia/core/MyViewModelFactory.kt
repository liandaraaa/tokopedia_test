package com.tokopedia.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.security.Provider

class MyViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}