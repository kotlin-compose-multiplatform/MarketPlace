package com.komekci.marketplace.features.basket.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.komekci.marketplace.core.Resource
import com.komekci.marketplace.core.database.AppDatabase
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity
import com.komekci.marketplace.features.basket.data.local.BasketStore
import com.komekci.marketplace.features.basket.domain.usecase.BasketUseCase
import com.komekci.marketplace.features.basket.presentation.state.BasketPrice
import com.komekci.marketplace.features.basket.presentation.state.SendBasketRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val db: AppDatabase,
    private val userDataStore: UserDataStore,
    private val useCase: BasketUseCase
) : ViewModel() {
    var basketProducts = mutableStateOf<List<BasketLocalEntity>>(emptyList())
        private set

    var basketStores = mutableStateOf<List<BasketStore>>(emptyList())
        private set

    var basketPrice = mutableStateOf(BasketPrice())
        private set

    var sendState = mutableStateOf(SendBasketRequest())
        private set

    var shopId = mutableIntStateOf(0)
        private set

    fun setShopId(id: Int) {
        shopId.intValue = id
    }

    fun getBasketProducts(shpId: Int) {
        viewModelScope.launch {
            val stores = db.basketDao().getBasketStores()
            stores?.let {
                basketStores.value = it
            }
            val list = db.basketDao().getBasket(shpId)
            list?.let {
                basketProducts.value = it
            }
            getPrices()
        }
    }



    /*
    tot_dis = 0.0
    p1 = 40.0 * 2
    dis = (234.0 - 175.5) * 1
    tot_dis+=dis
    p2 = 234.0 * 1 - dis
    p3 = 100.0 * 2 - 50 * 2
     */

    fun sendBasket(
        addressId: String,
        addressName: String,
        addressValue: String,
        addressPhone: String,
        guestId: String,
        isAuth: Boolean,
        paymentType: String,
        bank: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userDataStore.getUserData {
                viewModelScope.launch {
                    val list = db.basketDao().getBasket(shopId.intValue)
                    if (list.isNullOrEmpty().not()) {
                        useCase.sendOrder(
                            token = it.token ?: "",
                            addressId = addressId,
                            paymentMethod = paymentType,
                            bank = bank,
                            products = list ?: emptyList(),
                            isAuth = isAuth,
                            addressName = addressName,
                            addressValue = addressValue,
                            addressPhone = addressPhone,
                            guestId = guestId
                        ).onEach { result ->
                            when (result) {
                                is Resource.Error -> {
                                    sendState.value = sendState.value.copy(
                                        loading = false,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data,
                                    )
                                }

                                is Resource.Loading -> {
                                    sendState.value = sendState.value.copy(
                                        loading = true,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data,
                                    )
                                }
                                is Resource.Success -> {
                                    sendState.value = sendState.value.copy(
                                        loading = false,
                                        error = result.message,
                                        message = result.errorMessage,
                                        code = result.code,
                                        data = result.data,
                                    )
                                    if(paymentType != "card") {
                                        db.basketDao().deleteAll(shopId.intValue)
                                    }
                                    onSuccess()
                                }
                            }
                        }.launchIn(this)
                    }
                }
            }
        }
    }


    fun getPrices() {
        viewModelScope.launch {
            val list = db.basketDao().getBasket(shopId.intValue)
            list?.let {
                var total_discount = 0.0
                var complete = 0.0
                for (i in 0..list.size.minus(1)) {
                    val item = list[i]
                    val dis =
                        if (item.discount > 0) (item.oldPrice - item.discountPrice.toDouble()) * item.count else 0.0
                    total_discount += dis
                    val price = item.oldPrice * item.count - dis
                    Log.e("Discount", dis.toString())
                    complete += price
                }
                Log.e("Complete", total_discount.toString())
                val total = complete + total_discount
                basketPrice.value = basketPrice.value.copy(
                    count = list.size,
                    discountPrice = total_discount,
                    completePrice = complete,
                    total = total,
                    discountPercentage = (total_discount / total) * 100.0
                )
            }
        }
    }

    fun changeCountBasket(count: Int, id: String, onChange: (Int) -> Unit) {
        viewModelScope.launch {
            val old = db.basketDao().getBasketById(id)
            if (old != null) {
                val newCount = old.count + count
                if (newCount <= 0) {
                    db.basketDao().deleteBasketById(id)
//                    onChange(0)
                    getBasketProducts(shopId.intValue)
                } else {
                    db.basketDao().insertBasket(old.copy(count = newCount))
                    onChange(newCount)
                }

            }
            getPrices()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            db.basketDao().deleteAll(shopId.intValue)
            getBasketProducts(shopId.intValue)
        }
    }

    fun deleteFromBasket(id: String) {
        viewModelScope.launch {
            db.basketDao().deleteBasketById(id)
            getBasketProducts(shopId.intValue)
        }
    }

}