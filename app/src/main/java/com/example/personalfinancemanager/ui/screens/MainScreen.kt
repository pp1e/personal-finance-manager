import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.personalfinancemanager.components.main.MainComponent
import com.example.personalfinancemanager.ui.constants.UiConstants
import com.example.personalfinancemanager.ui.elements.AccountStatus
import com.example.personalfinancemanager.ui.elements.BottomMenu
import com.example.personalfinancemanager.ui.elements.CustomOperationButton

@Composable
fun MainScreen(component: MainComponent) {
    val model by component.model.subscribeAsState()
    val stateVertical = rememberScrollState(0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            HorizontalDivider(
                modifier = Modifier.height(UiConstants.AccountButtonDividerHeight),
                thickness = 0.dp
            )
            HorizontalDivider()

            Row(Modifier.fillMaxWidth()) {
                val modifier = Modifier
                    .weight(1f)
                    .padding(UiConstants.DefaultPadding)

                AccountStatus(
                    modifier = modifier,
                    moneyAmount = model.balanceRubles,
                    currency = "₽"
                )
                AccountStatus(
                    modifier = modifier,
                    moneyAmount = model.balanceDollars,
                    currency = "$"
                )
            }

            HorizontalDivider(
                modifier = Modifier.height(UiConstants.AccountButtonDividerHeight)
            )

            LazyColumn {
                items(model.frequentCategories) { category ->
                    CustomOperationButton(
                        text = category.name,
                        onClick = { },
                        modifier = Modifier.padding(
                            top = UiConstants.DefaultPadding,
                            start = UiConstants.DefaultPadding,
                            end = UiConstants.DefaultPadding
                        )
                    )
                }
            }
        }

        BottomMenu(
            onChartClicked = component::onChartClicked,
            onHistoryClicked = component::onHistoryClicked,
            onNewOperationClicked = component::onNewOperationClicked
        )
    }
}
