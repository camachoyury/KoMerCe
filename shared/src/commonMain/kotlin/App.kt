import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import view.home.composables.BottomView
import view.home.composables.ShirtStoreToolBar
import data.network.NetworkClient
import data.repository.ItemRepository
import di.Injector
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import view.home.HomeScreen
import view.home.HomeViewModel

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MaterialTheme {
        val scaffoldState = rememberScaffoldState()
        val topBarState = remember { mutableStateOf(true) }
        val bottomBarState = remember { mutableStateOf(true) }
        val fabState = remember { mutableStateOf(true) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val scope = rememberCoroutineScope()
            val homeViewModel = Injector.homeViewModel
            homeViewModel.loadItems(scope)
            Scaffold(
                scaffoldState = scaffoldState,
                content = { padding ->
//                    Content(
//                        padding,
//                        topBarState,
//                        bottomBarState,
//                        fabState
//                    )
                    HomeScreen(homeViewModel)
                },
                topBar = {
                    ShirtStoreToolBar(
                        scaffoldState,
                        topBarState
                    ) { Text("Google Shirt Store") }
                },
                bottomBar = { BottomBar(bottomBarState) },
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    if (fabState.value) {
                        FloatingActionButton(scaffoldState)
                    }
                },
                drawerContent = { DrawerContent() },
            )
        }
    }
}

expect fun getPlatformName(): String


@Composable
fun BottomBar(state: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar { BottomView() }
        }
    )
}

@Composable
fun FloatingActionButton(scaffoldState: ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material.FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,
        onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("FAB Action Button Clicked")
            }
        }
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "description"
            )
        }
    }
}

@Composable
fun DrawerContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Drawer Content")
    }
}