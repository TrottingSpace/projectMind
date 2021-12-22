import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

val trades = listOf("Wine", "Whiskey", "Rum", "Beer")
val matches = (0..4).map{Random.nextInt(4)}

fun main() {
    //var count: Int by mutableStateOf(0)

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
        var currentRound by mutableStateOf(0)
        var choices = mutableStateListOf<List<String>>()
        val savedChoices = mutableStateListOf("", "", "", "", "")
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
                        Select({
                            if (currentRound > 0) { disabled() }
                            onChange { savedChoices[i] = it.value!! }
                        }) {
                            Option(""){Text("Select")}
                            trades.forEach {
                                Option(it){
                                    Text(it)
                                }
                            }
                        }
                        //Text(i.toString())
                    }
                }
            }
        }

        Button(attrs = {
            if (savedChoices.any { it.isEmpty() }) { disabled() }
            onClick {
                currentRound++
                choices.add(savedChoices)
                console.log(JSON.stringify(choices))
            }
        }) {
            Text("Confirm choices")
        }
    }
}