import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

val trades = listOf("Wine", "Whiskey", "Rum", "Beer")
val matches = (0..4).map{trades[Random.nextInt(4)]}

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
        val choices = mutableStateListOf<List<String>>()
        var savedChoices = mutableStateListOf("", "", "", "", "")
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
            } //Tr end
            Tr{ //round 1
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
                    } //Td end
                } //for end
            } //Tr end
            if (currentRound > 0) {
                Tr{ //round 1 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[0][i]) {
                                Text("✅")
                            }
                            else if (matches.contains(choices[0][i])) {
                                Text("🟨")
                            }
                            else {
                                Text("❌")
                            }
                        } //Td end
                    } //for end
                } //Tr end
                Tr{ //round 2
                    for (i in 0..4){
                        Td({style{width(100.px)}}){
                            Select({
                                if (currentRound > 1 || matches[i] == choices[0][i]) { disabled() }
                                onChange { savedChoices[i] = it.value!! }
                            }) {
                                if (matches[i] == choices[0][i]) {
                                    Option(choices[0][i]) { Text(choices[0][i]) }
                                    savedChoices[i] = matches[i]
                                }
                                else {
                                    Option("") { Text("Select") }
                                    trades.forEach {
                                        Option(it) {
                                            Text(it)
                                        }
                                    }
                                }
                            }
                        } //Td end
                    } //for end
                } //Tr end
            } //if end
            if (currentRound > 1) {
                Tr{ //round 2 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[1][i]) {
                                Text("✅")
                            }
                            else if (matches.contains(choices[1][i])) {
                                Text("🟨")
                            }
                            else {
                                Text("❌")
                            }
                        } //Td end
                    } //for end
                } //Tr end
                Tr{ //round 3
                    for (i in 0..4){
                        Td({style{width(100.px)}}){
                            Select({
                                if (currentRound > 2 || matches[i] == choices[1][i]) { disabled() }
                                onChange { savedChoices[i] = it.value!! }
                            }) {
                                if (matches[i] == choices[1][i]) {
                                    Option(choices[1][i]) { Text(choices[1][i]) }
                                    savedChoices[i] = matches[i]
                                }
                                else {
                                    Option("") { Text("Select") }
                                    trades.forEach {
                                        Option(it) {
                                            Text(it)
                                        }
                                    }
                                }
                            }
                        } //Td end
                    } //for end
                } //Tr end
            } //if end
            if (currentRound > 2) {
                Tr{ //round 3 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[2][i]) {
                                Text("✅")
                            }
                            else if (matches.contains(choices[2][i])) {
                                Text("🟨")
                            }
                            else {
                                Text("❌")
                            }
                        }
                    } //for end
                } //Tr end
            } //if end
        } //table end

        Button(attrs = {
            if (savedChoices.any { it.isEmpty() }) { disabled() }
            onClick {
                currentRound++
                choices.add(savedChoices)
                savedChoices = mutableStateListOf("", "", "", "", "")
                console.log(JSON.stringify(choices))
            }
        }) {
            Text("Confirm choices")
        }
    }
}