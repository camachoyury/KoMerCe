package data.entity

import domain.Item
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShirtResponse(
    @SerialName("shirts")
    val shirt: List<Item>
)