package data.repository

import common.ApplicationDispatcher
import data.network.NetworkClient
import domain.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ItemRepository(private val networkClient: NetworkClient)  {

    fun getTShirtList(): Flow<List<Item>> {
        return flow {
            emit(networkClient.getShirts().shirt)
        }.flowOn(ApplicationDispatcher)
    }
}