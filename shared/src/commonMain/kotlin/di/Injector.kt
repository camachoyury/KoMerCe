package di

import data.network.NetworkClient
import data.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import view.home.HomeViewModel

object Injector  {

    private val networkClient: NetworkClient  by lazy { return@lazy NetworkClient() }
    private val itemRepository:ItemRepository by lazy { return@lazy ItemRepository(networkClient) }
    val homeViewModel: HomeViewModel by lazy { return@lazy HomeViewModel(itemRepository) }
}