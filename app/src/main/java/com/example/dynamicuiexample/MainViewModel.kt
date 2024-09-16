package com.example.dynamicuiexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.dynamicuiexample.response.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val selectedRadioMutable = MutableLiveData("")
    val selectedRadio: LiveData<String> = selectedRadioMutable

    private val mandatoryFieldMapMutable = MutableLiveData(HashMap<String, Boolean>())

    private val visibilityFieldMapMutable = MutableLiveData(HashMap<String, Boolean>())
    val visibilityFieldMap: LiveData<HashMap<String, Boolean>> = visibilityFieldMapMutable

    private val isStateValidMediator: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()
    val isStateValid: LiveData<Boolean> = isStateValidMediator


    private val isStateValidObserver =
        Observer<Any?> {
            isStateValidMediator.value = mandatoryFieldMapMutable.value?.values?.all { it }
        }


    init {
        isStateValidMediator.apply { addSource(mandatoryFieldMapMutable, isStateValidObserver) }
    }

    fun onRadioSelected(id: String, selectionId: String?) {
        selectedRadioMutable.value = id
        checkMandatoryFields(selectionId)
        adjustVisibility(id)
    }

    private fun adjustVisibility(id: String) {
        val visibilityMap = visibilityFieldMapMutable.value!!
        if (visibilityMap.contains(id)) {
            visibilityMap[id] = true
            visibilityFieldMapMutable.postValue(visibilityMap)
        } else {
            val tempMap = hashMapOf<String, Boolean>()
            visibilityMap.keys.forEach {
                tempMap[it] = false
            }
            visibilityFieldMapMutable.postValue(tempMap)
        }
    }

    private fun checkMandatoryFields(selectionId: String?) {
        selectionId ?: return
        val mandatoryMapTemp = mandatoryFieldMapMutable.value
        if (mandatoryMapTemp?.contains(selectionId) == true) {
            mandatoryMapTemp[selectionId] = true
            mandatoryFieldMapMutable.value = mandatoryMapTemp
        }
    }

    fun setMandatoryFields(value: ResponseData) {
        if (!value.selectionId.isNullOrEmpty()) {
            val mandatoryMapTemp = mandatoryFieldMapMutable.value!!
            if (!mandatoryMapTemp.contains(value.selectionId)) {
                mandatoryMapTemp[value.selectionId] = false
                mandatoryFieldMapMutable.value = mandatoryMapTemp
            }
        }
    }

    fun setVisibilityDependency(value: ResponseData) {
        val visibilityMap = visibilityFieldMapMutable.value!!
        if (!value.visibilityDependency.isNullOrEmpty() && !visibilityMap.contains(value.visibilityDependency)) {
            visibilityMap[value.visibilityDependency] = false
            visibilityFieldMapMutable.value = visibilityMap
        }
    }


}