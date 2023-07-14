package view.home

import data.repository.ItemRepository
import domain.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val itemRepository: ItemRepository) {

    private val _itemList: MutableStateFlow<ItemListState> =
        MutableStateFlow(ItemListState.LoadingState)
    val itemList: StateFlow<ItemListState> = _itemList.asStateFlow()


    fun loadItems( scope: CoroutineScope){
        scope.launch {
            _itemList.value = ItemListState.LoadingState
            itemRepository.getTShirtList().collect {
                _itemList.value = ItemListState.Success(it)
            }
        }
    }
}

    sealed class ItemListState {
        object LoadingState : ItemListState()
        class Success(val items: List<Item>) : ItemListState()
        data class Error(val exception: Throwable) : ItemListState()
    }
