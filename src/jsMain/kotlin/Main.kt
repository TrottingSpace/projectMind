import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

val trades = listOf("Wine", "Whiskey", "Rum", "Beer")
val matches = (0..4).map{trades[Random.nextInt(4)]}

fun main() {
    var currentRound by mutableStateOf(0)
    val choices = mutableStateListOf<List<String>>()
    var savedChoices = mutableStateListOf("", "", "", "", "")
    var isWin by mutableStateOf(0)

    renderComposable(rootElementId = "root") {

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
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[0][index]){ null }
                    else { it }
                }
                Tr{ //round 1 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[0][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[0][i])) {
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
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[1][index]){ null }
                    else { it }
                }
                Tr{ //round 2 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[1][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[1][i])) {
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
                val unmatched = matches.mapIndexedNotNull{index, it ->
                    if (it == choices[1][index]){ null }
                    else { it }
                }
                Tr{ //round 3 check
                    for (i in 0..4) {
                        Td({style{width(100.px)}}){
                            if (matches[i] == choices[2][i]) {
                                Text("✅")
                            }
                            else if (unmatched.contains(choices[2][i])) {
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

        if (isWin == 5) {
            Text("You Won")
            console.log("if", "round:", currentRound, "points:", isWin)
        }
        else if (currentRound <= 2 && isWin != 5) {
            Button(attrs = {
                if (savedChoices.any { it.isEmpty() } || currentRound > 2) {
                    disabled()
                }
                onClick {
                    for (i in 0..4){
                        if (matches[i] == savedChoices[i]){
                            isWin++
                        }
                    }
                    console.log("onClick 1", "round:", currentRound, "points:", isWin)
                    if (isWin < 5){ isWin = 0 }
                    console.log("onClick", "round:", currentRound, "points:", isWin)
                    currentRound++
                    choices.add(savedChoices)
                    savedChoices = mutableStateListOf("", "", "", "", "")
                    console.log(JSON.stringify(choices))
                }
            }) {
                Text("Confirm choices")
            } //button end
        }
        else {
            Text("You Lose")
            console.log("else", "round:", currentRound, "points:", isWin)
        }
    }
}