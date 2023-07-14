package view.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import view.home.composables.LikedIndicator
import view.home.composables.ProgressBar
import domain.Item
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalFoundationApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val items = homeViewModel.itemList.collectAsState()
    when (items.value) {
        is ItemListState.Success -> {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    val i = (items.value as ItemListState.Success).items
                    items(i.size) { index ->
                        Item(i[index], selectItem = {})
                    }
                }
            )
        }
        is ItemListState.Error -> {
            Text(text = "EROR")
        }
        is ItemListState.LoadingState -> {
            ProgressBar()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Item(item: Item, selectItem: () -> Unit) {

    var imageUri = "${item.image}.jpg"

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .background(Color.White)
            .padding(4.dp)
            .clickable { selectItem() },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,
            ) {
                LikedIndicator()
            }

            Image(
                painterResource(imageUri),
                contentDescription = "image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(start = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    item.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                )

                Text(
                    "${item.price} $",
                    style = TextStyle(color = Color.Red, fontFamily = FontFamily.Monospace),
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )

            }
        }
    }
}
