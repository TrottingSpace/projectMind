import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

val trades = listOf("Wine", "Whiskey", "Rum", "Beer")
val matches = (0..4).map{Random.nextInt(4)}

fun main() {
    var count: Int by mutableStateOf(0)

    renderComposable(rootElementId = "root") {
        /*
        Div({ style { padding(25.px) } }) {
            Button(attrs = {
                onClick { count -= 1 }
            }) {
                Text("-")
            }

            Span({ style { padding(15.px) } }) {
                Text("$count")
            }

            Button(attrs = {
                onClick { count += 1 }
            }) {
                Text("+")
            }
        }
         */
        Table({
            style {
                fontSize(50.px)
                //width(100.px)
            }
        }){
            Tr{
                for (i in 0..4){
                    Td{
                        Text(i.toString())
                    }
                }
            }
            Tr{
                for (i in 0..4){
                    Td({style{width(100.px)}}){
                        Select {
                            trades.forEachIndexed { index, it ->
                                Option(index.toString()){
                                    Text(it)
                                }
                            }
                        }
                        //Text(i.toString())
                    }
                }
            }
        }
    }
}