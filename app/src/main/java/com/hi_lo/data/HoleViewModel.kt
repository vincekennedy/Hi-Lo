package com.hi_lo.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HoleViewModel : ViewModel() {
  var firstOnGreen: MutableLiveData<Int> = MutableLiveData(0)
  var closestToThePin: MutableLiveData<Int> = MutableLiveData(0)
}